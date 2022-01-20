import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, Observer} from "rxjs";
import {shareReplay, tap} from "rxjs/operators";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user: Observable<User>;

  constructor(private http: HttpClient) {
    this.recheckStatus()
  }

  getStatus(): Observable<User> {
    return this.http.get<User>(environment.apiUrl + '/authentication/status', {
      withCredentials: true
    }).pipe(shareReplay(1));
  }


  recheckStatus() {
    this.user = this.getStatus();
    this.user.subscribe()
  }


  // createCheckIfSignedInRequest(): Observable<User> {
  //   return this.http.get<User>(environment.apiUrl + '/authentication/status', {
  //     withCredentials: true
  //   }).pipe(shareReplay(1));
  // }

  logout() {
    this.http.post(environment.apiUrl + '/authentication/logout', null, {
      withCredentials: true
    }).subscribe((response) => {
      window.location.href = environment.appUrl;
      this.user = undefined;
    });
  }


  public login(loginMail: string, loginPassword: string): Observable<User> {
    return this.http.post<User>(environment.apiUrl + '/authentication/login', {
      'mail': loginMail,
      'password': loginPassword
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).pipe(tap((loggedUser) => this.user = new Observable<User>((observer: Observer<User>) => {
      observer.next(loggedUser)
    }).pipe(shareReplay(1))))
  }

  public generateVerificationCode(mail: string): Observable<any> {
    return this.http.post(environment.apiUrl + '/authentication/code', {
      'mail': mail
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  public checkAgainstVerificationCode(mail: string, codeInput: string): Observable<any> {
    let params = new HttpParams();
    params = params.append('mail', mail).append('codeInput', codeInput);

    return this.http.get(environment.apiUrl + '/authentication/code', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }

  public register(registerMail: string, registerPassword: string, username: string, verificationCode: string): Observable<any> {
    return this.http.post<User>(environment.apiUrl + '/authentication/register', {
      'mail': registerMail,
      'password': registerPassword,
      'username': username,
      'verificationCode': verificationCode
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).pipe(tap((loggedUser) => this.user = new Observable<User>((observer: Observer<User>) => {
      observer.next(loggedUser)
    }).pipe(shareReplay(1))))
  }

  completePasswordChange(loginMail: string, newPassword: string, verificationCode: string) {
    return this.http.post<User>(environment.apiUrl + '/authentication/password-new', {
      'mail': loginMail,
      'password': newPassword,
      'verificationCode': verificationCode
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).pipe(tap((loggedUser) => this.user = new Observable<User>((observer: Observer<User>) => {
      observer.next(loggedUser)
    }).pipe(shareReplay(1))))
  }
}

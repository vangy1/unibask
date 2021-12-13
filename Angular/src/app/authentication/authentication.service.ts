import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {map, shareReplay, tap} from "rxjs/operators";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user?: User;
  checkIfSignedInRequest: Observable<User>;

  constructor(private http: HttpClient) {
    this.checkIfSignedInRequest = this.createCheckIfSignedInRequest();
    this.checkIfSignedInRequest.pipe(tap(user => this.user = user)).subscribe();
  }

  createCheckIfSignedInRequest(): Observable<User> {
    return this.http.get<User>(environment.apiUrl + '/authentication/status', {
      withCredentials: true
    }).pipe(shareReplay(1));
  }

  logout() {
    this.http.post(environment.apiUrl + '/authentication/logout', null, {
      withCredentials: true
    }).subscribe((response) => {
      this.user = undefined;
      window.location.href = environment.appUrl;
    });
  }


  public login(loginMail: string, loginPassword: string): Observable<User> {
    return this.http.post<User>(environment.apiUrl + '/authentication/login', {
      'mail': loginMail,
      'password': loginPassword
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).pipe(map((user: User) => this.user = user))
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
    console.log(mail)
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
    }).pipe(map((user: User) => this.user = user))
  }

  completePasswordChange(loginMail: string, newPassword: string, verificationCode: string) {
    return this.http.post<User>(environment.apiUrl + '/authentication/password-new', {
      'mail': loginMail,
      'password': newPassword,
      'verificationCode': verificationCode
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).pipe(map((user: User) => this.user = user))
  }
}

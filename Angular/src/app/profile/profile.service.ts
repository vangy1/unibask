import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Question} from "../question/question";
import {environment} from "../../environments/environment";
import {Answer} from "../question/answer/answer";
import {Comment} from "../question/comment/comment";
import {map} from "rxjs/operators";
import {User} from "../authentication/user";
import {EntryProfile} from "./entry-preview/entry-profile";
import {UserInfo} from "./user-info";

@Injectable()
export class ProfileService {
  constructor(private http: HttpClient) {
  }

  getUser(userId: string) {
    return this.http.get<User>(environment.apiUrl + '/profile', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: new HttpParams().append('userId', userId)
    })
  }

  getUserInfo() {
    return this.http.get<UserInfo>(environment.apiUrl + '/profile/info', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  saveUserInfo(userInfo: UserInfo) {
    return this.http.post(environment.apiUrl + '/profile/info', {
      'studyProgramId': userInfo.studyProgramId,
      'mailNotificationsEnabled': userInfo.mailNotificationsEnabled,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  getUploadRequest(avatarFile: any) {
    const formData = new FormData();
    formData.append('avatar', avatarFile);

    return this.http.post(environment.apiUrl + '/avatar/upload', formData, {
      responseType: 'text',
      withCredentials: true,
      reportProgress: true,
      observe: "events",
      headers: new HttpHeaders({
        'ngsw-bypass': 'true',
      })
    });
  }

  getProfileEntries(userId: string) {
    let params = new HttpParams();
    params = params.append('userId', userId);

    return this.http.get(environment.apiUrl + '/entries', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    }).pipe(map((respone: any) => {
      let questions: Question[] = respone['questions']
      let answers: Answer[] = respone['answers']
      let comments: Comment[] = respone['comments']

      return [
        ...questions.map(value => <EntryProfile>{entryName: "Otázka", title: value.title, entry: value}),
        ...answers.map(value => <EntryProfile>{entryName: "Odpoveď", title: null, entry: value}),
        ...comments.map(value => <EntryProfile>{
          entryName: "Komentár",
          title: null,
          entry: value
        })].sort((a, b) => new Date(b.entry.creationDate).getTime() - new Date(a.entry.creationDate).getTime())
    }))
  }

  saveImageRequest(avatarUrl: string) {
    return this.http.post(environment.apiUrl + '/avatar', {
      'avatarUrl': avatarUrl,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }


  changePassword(oldPassword: string, newPassword: string) {
    return this.http.post(environment.apiUrl + '/authentication/password-change', {
      'oldPassword': oldPassword,
      'newPassword': newPassword,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

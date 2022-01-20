import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Question} from "../../entry/question/question";
import {environment} from "../../../environments/environment";
import {ProfileEntry} from "./profile-entry";
import {Answer} from "../../entry/answer/answer";
import {Comment} from "../../entry/comment/comment";
import {map} from "rxjs/operators";
import {User} from "../../authentication/user";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) {
  }

  getUser(userId: string) {
    let params = new HttpParams();
    params = params.append('userId', userId);

    return this.http.get<User>(environment.apiUrl + '/user', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
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
        ...questions.map(value => <ProfileEntry>{entryName: "Otázka", title: value.title, entry: value}),
        ...answers.map(value => <ProfileEntry>{entryName: "Odpoveď", title: null, entry: value}),
        ...comments.map(value => <ProfileEntry>{
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


  setStudyProgram(studyProgramId: string) {
    return this.http.post(environment.apiUrl + '/user/study-program', {
      'studyProgramId': studyProgramId,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  setMailNotifications(selectedMailNotifications: string) {

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

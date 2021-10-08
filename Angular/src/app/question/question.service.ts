import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Question} from "./question";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) {
  }

  getQuestion(id: string) {
    let params = new HttpParams();
    params = params.append('id', id);

    return this.http.get<Question>(environment.apiUrl + '/api/question?answers=false', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }

  getQuestionWithAnswers(id: string) {
    let params = new HttpParams();
    params = params.append('id', id);

    return this.http.get<Question>(environment.apiUrl + '/api/question?answers=true', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }

  createNewQuestion(title: string, text: string, categoryId: string, isAnonymous: string) {
    return this.http.post(environment.apiUrl + '/api/question', {
      'title': title,
      'text': text,
      'categoryId': categoryId,
      'isAnonymous': isAnonymous
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

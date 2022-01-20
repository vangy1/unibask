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


  getQuestions(phrase: string, page: number, limit: number) {
    return this.getQuestionsRequest(new HttpParams(), phrase, page, limit);
  }

  getFollowedQuestions(phrase: string, page: number, limit: number) {
    return this.getQuestionsRequest(new HttpParams().append('followed', true), phrase, page, limit);
  }

  getQuestionsByCategory(phrase: string, category: number, page: number, limit: number) {
    return this.getQuestionsRequest(new HttpParams().append('category', category), phrase, page, limit);
  }

  getQuestionsRequest(params: HttpParams, phrase: string, page: number, limit: number) {
    if (phrase) params = params.append('phrase', phrase);
    params = params.append('page', page);
    params = params.append('limit', limit);

    return this.http.get<Question[]>(environment.apiUrl + '/questions', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }


  getQuestionWithAnswers(id: string) {
    let params = new HttpParams();
    params = params.append('id', id);

    return this.http.get<Question>(environment.apiUrl + '/question', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }

  createNewQuestion(title: string, text: string, unformattedText: string, categoryId: number, isAnonymous: boolean) {
    return this.http.post<string>(environment.apiUrl + '/question', {
      'title': title,
      'text': text,
      'unformattedText': unformattedText,
      'categoryId': categoryId,
      'isAnonymous': isAnonymous
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  changeFollowStatus(id: number, status: boolean) {
    return this.http.post(environment.apiUrl + '/question/follow', {
      'id': id,
      'followed': status,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true
    })
  }
}

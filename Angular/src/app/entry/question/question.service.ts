import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Question} from "./question";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) {
  }


  getQuestions(page: number, limit: number) {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('limit', limit);

    return this.getQuestionsRequest(params);
  }

  getFollowedQuestions(page: number, limit: number) {
    let params = new HttpParams();
    params = params.append('followed', true);
    params = params.append('page', page);
    params = params.append('limit', limit);

    return this.getQuestionsRequest(params);
  }

  getQuestionsByCategory(category: number, page: number, limit: number) {
    let params = new HttpParams();
    params = params.append('category', category);
    params = params.append('page', page);
    params = params.append('limit', limit);

    return this.getQuestionsRequest(params);
  }

  getQuestionsRequest(params: HttpParams) {
    return this.http.get<Question[]>(environment.apiUrl + '/api/questions', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
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

  createNewQuestion(title: string, text: string, categoryId: number, isAnonymous: boolean) {
    return this.http.post<string>(environment.apiUrl + '/api/question', {
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

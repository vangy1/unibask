import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Question} from "../question";
import {Answer} from "./answer";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) {
  }

  markSolved(answerId: number, isSolved: boolean) {
    return this.http.post(environment.apiUrl + '/answer/mark-solved', {
      answerId: answerId,
      isSolved: isSolved
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  createNewAnswer(text: string, unformattedText: string, isAnonymous: boolean, question: Question) {
    return this.http.post<Answer>(environment.apiUrl + '/answer', {
      'text': text,
      'unformattedText': unformattedText,
      'isAnonymous': isAnonymous,
      'questionId': question.id
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

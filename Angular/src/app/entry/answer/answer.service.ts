import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Answer} from "./answer";
import {Question} from "../question/question";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) {
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

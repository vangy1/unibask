import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  constructor(private http: HttpClient) {
  }

  sendFeedback(feedbackText: string) {
    return this.http.post(environment.apiUrl + '/feedback', {
      feedbackText: feedbackText
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

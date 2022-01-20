import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Comment} from "./comment";
import {Entry} from "../../entry/entry";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  createNewComment(text: string, entry: Entry) {
    return this.http.post<Comment>(environment.apiUrl + '/comment', {
      'text': text,
      'entryId': entry.id
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

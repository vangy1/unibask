import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Entry} from "../entry";

@Injectable()
export class EditEntryService {

  constructor(private http: HttpClient) {
  }

  getEntry(id: number) {
    return this.http.get<Entry>(environment.apiUrl + '/entry', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: new HttpParams().append('id', id)
    })
  }

  editEntry(entryId: number, text: string, unformattedText: string) {
    return this.http.post(environment.apiUrl + '/entry/edit', {
      id: entryId,
      text: text,
      unformattedText: unformattedText
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

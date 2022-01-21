import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class ReportService {

  constructor(private http: HttpClient) {
  }

  reportEntry(entryId: number, reportReason: string) {
    return this.http.post(environment.apiUrl + '/report', {
      entryId: entryId,
      reportReason: reportReason
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}

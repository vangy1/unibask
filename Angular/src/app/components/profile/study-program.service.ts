import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StudyProgram} from "./study-program";

@Injectable({
  providedIn: 'root'
})
export class StudyProgramService {
  studyPrograms: StudyProgram[];

  constructor(private http: HttpClient) {
    this.getStudyPrograms();

  }

  getStudyPrograms() {
    return this.http.get<StudyProgram[]>(environment.apiUrl + '/api/study-programs', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true
    }).subscribe((programs) => this.studyPrograms = programs);
  }
}

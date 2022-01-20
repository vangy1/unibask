import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LeaderboardItem} from "./leaderboard-item";

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  leaderboard: LeaderboardItem[]

  constructor(private http: HttpClient) {
  }

  getLeaderboard() {
    this.http.get<LeaderboardItem[]>(environment.apiUrl + '/leaderboard', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).subscribe(response => this.leaderboard = response)
  }
}

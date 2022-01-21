import {Component, OnInit} from '@angular/core';
import {LeaderboardService} from "./leaderboard.service";
import {Router} from "@angular/router";

@Component({
  selector: 'leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.scss']
})
export class LeaderboardComponent implements OnInit {
  constructor(private leaderboardService: LeaderboardService, private router: Router) {
    leaderboardService.getLeaderboard()
  }

  ngOnInit(): void {
  }

  goToProfile(userId: number) {
    this.router.navigate(['/profile'], {queryParams: {id: userId}})
  }

  getLeaderboardItems() {
    return this.leaderboardService.leaderboard
  }

  getColor(i: number) {
    switch (i) {
      case 0:
        return '#FFD700'
      case 1:
        return '#C0C0C0'
      case 2:
        return '#CD7F32'
    }
    return ''
  }
}

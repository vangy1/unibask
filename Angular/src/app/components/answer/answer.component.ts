import {Component, Input, OnInit} from '@angular/core';
import {Answer} from "../../entry/answer/answer";
import {VoteService} from "../../vote/vote.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.scss']
})
export class AnswerComponent implements OnInit {
  @Input() answer: Answer;

  constructor(private voteService: VoteService, private snackBar: MatSnackBar, private router: Router) {
  }

  ngOnInit(): void {
  }

  upvote() {
    this.voteService.upvoteEntry(this.answer)
  }

  downvote() {
    this.voteService.downvoteEntry(this.answer)
  }

  goToProfile(userId: number) {
    this.router.navigate(['/profile'], {queryParams: {id: userId}})
  }
}

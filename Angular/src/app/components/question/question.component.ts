import {Component, Input, OnInit} from '@angular/core';
import {Question} from "../../entry/question/question";
import {VoteService} from "../../vote/vote.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  @Input() question: Question;
  @Input() showVoting: boolean;

  constructor(private voteService: VoteService, private snackBar: MatSnackBar, private router: Router) {
  }

  ngOnInit(): void {
  }

  upvote() {
    this.voteService.upvoteEntry(this.question)
  }

  downvote() {
    this.voteService.downvoteEntry(this.question)
  }

  goToProfile(userId: number) {
    this.router.navigate(['/profile'], {queryParams: {id: userId}})
  }
}

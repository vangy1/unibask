import {Component, Input} from '@angular/core';
import {Answer} from "./answer";
import {VoteService} from "../vote.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../authentication/authentication.service";
import {Question} from "../question";
import {AnswerService} from "./answer.service";
import {ReportDialogComponent} from "../report-dialog/report-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.scss']
})
export class AnswerComponent {
  @Input() question: Question;
  @Input() answer: Answer;

  constructor(private answerService: AnswerService,
              private voteService: VoteService,
              private snackBar: MatSnackBar,
              private router: Router,
              private authenticationService: AuthenticationService,
              private dialog: MatDialog) {
  }

  upvote() {
    this.voteService.upvoteEntry(this.answer)
  }

  downvote() {
    this.voteService.downvoteEntry(this.answer)
  }

  markSolved() {
    this.answerService.markSolved(this.answer.id, !this.answer.solvesQuestion).subscribe(() => {
      this.question.answers.forEach(answer => {
        if (this.answer != answer) answer.solvesQuestion = false
      });
      this.answer.solvesQuestion = !this.answer.solvesQuestion
    })
  }

  goToProfile(userId: number) {
    this.router.navigate(['/profile'], {queryParams: {id: userId}})
  }

  edit() {
    this.router.navigate(['/question/edit'], {queryParams: {id: this.answer.id}})
  }

  reportEntry() {
    const dialogRef = this.dialog.open(ReportDialogComponent, {
      maxWidth: "400px",
      data: {entryId: this.answer.id}
    });
  }

  getAuthenticatedUser() {
    return this.authenticationService?.user
  }
}

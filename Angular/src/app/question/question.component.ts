import {Component, Input} from '@angular/core';
import {Question} from "./question";
import {VoteService} from "./vote.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication/authentication.service";
import {MatDialog} from "@angular/material/dialog";
import {ReportDialogComponent} from "./report-dialog/report-dialog.component";
import {QuestionService} from "./question.service";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent {

  @Input() question: Question;
  @Input() showVoting: boolean;

  constructor(private questionService: QuestionService,
              private voteService: VoteService,
              private snackBar: MatSnackBar,
              private router: Router,
              private authenticationService: AuthenticationService,
              private dialog: MatDialog) {
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

  reportEntry() {
    const dialogRef = this.dialog.open(ReportDialogComponent, {
      maxWidth: "400px",
      data: {entryId: this.question.id}
    });
  }

  changeFollowStatus() {
    this.question.followed = !this.question.followed
    this.questionService.changeFollowStatus(this.question.id, this.question.followed).subscribe()
  }

  edit() {
    this.router.navigate(['/question/edit'], {queryParams: {id: this.question.id}})
  }

  getAuthenticatedUser() {
    return this.authenticationService?.user
  }
}

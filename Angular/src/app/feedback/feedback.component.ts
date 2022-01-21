import {Component, OnInit} from '@angular/core';
import {FeedbackService} from "./feedback.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit {
  feedbackText: string;

  constructor(private feedbackService: FeedbackService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  sendFeedback(feedback: string) {
    this.feedbackService.sendFeedback(feedback).subscribe(() => {
        this.feedbackText = '';
        this.snackBar.open("Feedback bol úspešne zaslaný. Ďakujem.", undefined, {duration: 3000});
      }
    )
  }
}

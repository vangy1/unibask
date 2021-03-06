import {Component, Input, OnInit} from '@angular/core';
import {Question} from "../../question/question";
import {Router} from "@angular/router";

@Component({
  selector: 'app-question-preview',
  templateUrl: './question-preview.component.html',
  styleUrls: ['./question-preview.component.scss']
})
export class QuestionPreviewComponent implements OnInit {
  @Input() question: Question

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  goToQuestion() {
    this.router.navigate(['/question'], {queryParams: {id: this.question.id}})
  }
}

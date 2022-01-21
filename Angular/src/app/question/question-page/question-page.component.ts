import {Component, Input, OnInit} from '@angular/core';
import {QuestionService} from "../question.service";
import {Question} from "../question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'question-view',
  templateUrl: './question-page.component.html',
  styleUrls: ['./question-page.component.scss']
})
export class QuestionPageComponent implements OnInit {
  @Input() questionId: string;
  question: Question;

  constructor(private questionService: QuestionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.questionService.getQuestionWithAnswers(params['id']).subscribe(question => {
        this.question = question;
      })
    });
  }
}

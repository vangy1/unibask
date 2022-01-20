import {Component, Input, OnInit} from '@angular/core';
import {QuestionService} from "../../entry/question/question.service";
import {Question} from "../../entry/question/question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-question-view',
  templateUrl: './view-question.component.html',
  styleUrls: ['./view-question.component.scss']
})
export class ViewQuestionComponent implements OnInit {
  @Input() questionId: string;
  question: Question;

  constructor(private questionService: QuestionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.questionService.getQuestionWithAnswers(params['id']).subscribe(question => {
        console.log(question)
        this.question = question;
      })
    });
  }
}

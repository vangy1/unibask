import {Component, Input, OnInit} from '@angular/core';
import {QuestionService} from "../../question/question.service";
import {Question} from "../../question/question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-question-view',
  templateUrl: './question-view.component.html',
  styleUrls: ['./question-view.component.scss']
})
export class QuestionViewComponent implements OnInit {
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

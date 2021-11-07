import {Component, OnInit} from '@angular/core';
import {QuestionService} from "../../entry/question/question.service";
import {Question} from "../../entry/question/question";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-list-questions',
  templateUrl: './list-questions.component.html',
  styleUrls: ['./list-questions.component.scss']
})
export class ListQuestionsComponent implements OnInit {
  questions: Question[] = []

  constructor(private questionService: QuestionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      if (!params) {
        this.questionService.getQuestions(undefined).subscribe(next => this.questions = next)
      } else {
        this.questionService.getQuestions(params['categoryId']).subscribe(questions => {
          this.questions = questions;
        })
      }

    });

  }

}

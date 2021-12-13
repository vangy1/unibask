import {Component, OnInit} from '@angular/core';
import {QuestionService} from "../../entry/question/question.service";
import {ActivatedRoute, Params} from "@angular/router";
import {ListQuestionsService} from "./list-questions.service";

@Component({
  selector: 'app-list-questions',
  templateUrl: './list-questions.component.html',
  styleUrls: ['./list-questions.component.scss']
})
export class ListQuestionsComponent implements OnInit {
  params: Params;
  title: string

  constructor(private questionService: QuestionService, private route: ActivatedRoute, public listQuestionsService: ListQuestionsService) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.params = params;
      this.setTitle(params)
      this.listQuestionsService.clearQuestions();
      this.listQuestionsService.loadMore(params['followed'], params['category']);
    });
  }

  setTitle(params: Params) {
    if (params['followed']) {
      this.title = 'Sledované kategórie'
    } else if (params['category']) {
      this.title = '<meno-kategorie> todo'
    } else {
      this.title = 'Všetky otázky'
    }
  }

  onScroll(): void {
    this.listQuestionsService.loadMore(this.params["followed"], this.params["category"])
  }
}

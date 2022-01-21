import {Component, OnInit} from '@angular/core';
import {QuestionService} from "../question/question.service";
import {ActivatedRoute, Params} from "@angular/router";
import {QuestionListService} from "./question-list.service";
import {Subject} from "rxjs";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {CategoryService} from "../category/category.service";

@Component({
  selector: 'question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent implements OnInit {
  params: Params;
  title: string

  searchPhrase: string;
  questionSearchChanged: Subject<string> = new Subject<string>();

  constructor(private questionService: QuestionService,
              private route: ActivatedRoute,
              private listQuestionsService: QuestionListService,
              private categoryService: CategoryService) {
    this.questionSearchChanged
      .pipe(
        debounceTime(700),
        distinctUntilChanged())
      .subscribe(phrase => {
        this.searchPhrase = phrase;
        this.listQuestionsService.loadInitial(this.params['followed'], this.params['category'], this.searchPhrase);
      });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.params = params;
      this.setTitle(params);
      this.listQuestionsService.loadInitial(params['followed'], params['category'], this.searchPhrase);
    });
  }

  getQuestions() {
    return this.listQuestionsService?.questions
  }

  setTitle(params: Params) {
    if (params['followed']) {
      this.title = 'Sledované kategórie'
    } else if (params['category']) {
      this.categoryService.getCategory(params['category']).subscribe(category => this.title = category.title)
    } else {
      this.title = 'Všetky otázky'
    }
  }

  onScroll(): void {
    this.listQuestionsService.loadMore(this.params["followed"], this.params["category"], this.searchPhrase)
  }

  searchPhraseChanged(text: string) {
    this.questionSearchChanged.next(text);
  }
}

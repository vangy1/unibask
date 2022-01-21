import {Injectable} from '@angular/core';
import {Question} from "../question/question";
import {QuestionService} from "../question/question.service";

@Injectable()
export class QuestionListService {
  questions: Question[];
  page = 0;
  limit = 10;

  constructor(private questionService: QuestionService) {
  }

  loadInitial(followed: any, category: any, searchPhrase: string) {
    this.questions = undefined;
    this.page = 0;
    this.getRequest(followed, category, searchPhrase).subscribe(questions => {
      this.questions = []
      this.questions.push(...questions)
    })
    this.page += 1;
  }

  loadMore(followed: any, category: any, searchPhrase: string): void {
    this.getRequest(followed, category, searchPhrase).subscribe(questions => {
      this.questions.push(...questions)
    })

    this.page += 1;
  }

  getRequest(followed: any, category: any, searchPhrase: string) {
    if (followed) {
      return this.questionService.getFollowedQuestions(searchPhrase, this.page, this.limit)
    } else if (category) {
      return this.questionService.getQuestionsByCategory(searchPhrase, category, this.page, this.limit)
    } else {
      return this.questionService.getQuestions(searchPhrase, this.page, this.limit)
    }
  }
}

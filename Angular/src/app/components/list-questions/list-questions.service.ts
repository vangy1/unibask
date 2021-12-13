import {Injectable} from '@angular/core';
import {Question} from "../../entry/question/question";
import {QuestionService} from "../../entry/question/question.service";

@Injectable({
  providedIn: 'root'
})
export class ListQuestionsService {
  questions: Question[] = []
  page = 0;
  limit = 10;

  constructor(private questionService: QuestionService) {
  }

  loadMore(followed: any, category: any): void {
    let request;
    if (followed) {
      request = this.questionService.getFollowedQuestions(this.page, this.limit)
    } else if (category) {
      request = this.questionService.getQuestionsByCategory(category, this.page, this.limit)
    } else {
      request = this.questionService.getQuestions(this.page, this.limit)
    }

    request.subscribe(questions => {
      this.questions.push(...questions)
    })

    this.page += 1;
  }


  clearQuestions() {
    this.questions = [];
    this.page = 0
  }
}

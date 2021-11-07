import {Component, Input, OnInit} from '@angular/core';
import {AnswerService} from "../../entry/answer/answer.service";
import {Question} from "../../entry/question/question";

@Component({
  selector: 'app-answer-to-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.scss']
})
export class AnswerQuestionComponent implements OnInit {
  @Input() question: Question
  text: string;
  isAnonymous: boolean;

  constructor(private answerService: AnswerService) {
  }

  ngOnInit(): void {
  }

  createNewAnswer() {
    this.answerService.createNewAnswer(this.text, this.isAnonymous, this.question).subscribe(answer => {
      this.question.answers.push(answer);
    });
  }
}

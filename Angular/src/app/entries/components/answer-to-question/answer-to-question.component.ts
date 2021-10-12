import {Component, Input, OnInit} from '@angular/core';
import {AnswerService} from "../../answer/answer.service";
import {Question} from "../../question/question";

@Component({
  selector: 'app-answer-to-question',
  templateUrl: './answer-to-question.component.html',
  styleUrls: ['./answer-to-question.component.scss']
})
export class AnswerToQuestionComponent implements OnInit {
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

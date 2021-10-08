import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-answer-to-question',
  templateUrl: './answer-to-question.component.html',
  styleUrls: ['./answer-to-question.component.scss']
})
export class AnswerToQuestionComponent implements OnInit {
  text: any;
  isAnonymous: any;

  constructor() {
  }

  ngOnInit(): void {
  }

  createNewQuestion() {


  }
}

import {Component, Input, OnInit} from '@angular/core';
import {Question} from "../../entry/question/question";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  @Input() question: Question;
  @Input() allowVoting: boolean;

  constructor() {
  }

  ngOnInit(): void {
  }

}

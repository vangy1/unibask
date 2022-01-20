import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {AnswerService} from "../../entry/answer/answer.service";
import {Question} from "../../entry/question/question";
import {QuillEditorBase} from "ngx-quill";
import {QuillModulesService} from "../../quill-modules.service";

@Component({
  selector: 'app-answer-to-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.scss']
})
export class AnswerQuestionComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;

  @Input() question: Question
  text: string;
  isAnonymous: boolean;

  constructor(private answerService: AnswerService, public quillService: QuillModulesService) {
  }

  ngOnInit(): void {
  }

  createNewAnswer() {
    this.answerService.createNewAnswer(this.text, this.quillEditorBase.editorElem.innerText, this.isAnonymous, this.question).subscribe(answer => {
      this.text = '';
      this.question.answers.push(answer);
    });
  }
}

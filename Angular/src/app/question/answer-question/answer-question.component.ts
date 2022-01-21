import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Question} from "../question";
import {QuillEditorBase} from "ngx-quill";
import {EditorService} from "../../editor/editor.service";
import {AnswerService} from "../answer/answer.service";

@Component({
  selector: 'answer-to-question',
  templateUrl: './answer-question.component.html',
  styleUrls: ['./answer-question.component.scss']
})
export class AnswerQuestionComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;

  @Input() question: Question
  text: string;
  isAnonymous: boolean;

  constructor(private answerService: AnswerService, public quillService: EditorService) {
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

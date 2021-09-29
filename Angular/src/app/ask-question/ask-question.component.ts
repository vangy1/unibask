import {Component, OnInit, ViewChild} from '@angular/core';
import {QuillEditorBase} from "ngx-quill";

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.scss']
})
export class AskQuestionComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;

  constructor() {
  }

  ngOnInit(): void {
  }

}

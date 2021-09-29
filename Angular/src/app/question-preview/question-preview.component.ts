import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-question-preview',
  templateUrl: './question-preview.component.html',
  styleUrls: ['./question-preview.component.scss']
})
export class QuestionPreviewComponent implements OnInit {

  allowPreview = false

  // input: Question

  constructor() {
  }

  ngOnInit(): void {
  }

}

import {Component, OnInit, ViewChild} from '@angular/core';
import {QuillEditorBase} from "ngx-quill";
import {QuestionService} from "../../question/question.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-question-ask',
  templateUrl: './question-ask.component.html',
  styleUrls: ['./question-ask.component.scss']
})
export class QuestionAskComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;
  title: string;
  text: string;
  isAnonymous: boolean;

  constructor(private router: Router, private questionService: QuestionService) {
  }

  ngOnInit(): void {
  }

  createNewQuestion() {
    this.questionService.createNewQuestion(this.title, this.text, "1", this.isAnonymous).subscribe(id => {
      this.router.navigate(['/question'], {queryParams: {id: id}})
    });
  }

}

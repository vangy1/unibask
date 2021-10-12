import {Component, Input, OnInit} from '@angular/core';
import {CommentService} from "../../comment/comment.service";
import {Question} from "../../question/question";
import {Answer} from "../../answer/answer";

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.scss']
})
export class CommentSectionComponent implements OnInit {

  @Input() entry: Question | Answer
  text: string

  constructor(private commentService: CommentService) {
  }

  ngOnInit(): void {
  }

  createNewComment() {
    this.commentService.createNewComment(this.text, this.entry).subscribe(comment => {
      this.entry.comments.push(comment);
    });
  }

}

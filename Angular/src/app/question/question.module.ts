import {NgModule} from '@angular/core';
import {SharedModule} from "../shared.module";
import {AnswerQuestionComponent} from "./answer-question/answer-question.component";
import {AnswerComponent} from "./answer/answer.component";
import {CommentSectionComponent} from "./comment/comment-section/comment-section.component";
import {EditEntryComponent} from "./edit-entry/edit-entry.component";
import {QuestionPageComponent} from "./question-page/question-page.component";
import {QuestionComponent} from "./question.component";
import {ReportDialogComponent} from "./report-dialog/report-dialog.component";
import {EditorModule} from "../editor/editor.module";
import {MatDialogModule} from "@angular/material/dialog";
import {RouterModule, Routes} from "@angular/router";
import {AnswerService} from "./answer/answer.service";
import {CommentService} from "./comment/comment.service";
import {EditEntryService} from "./edit-entry/edit-entry.service";
import {QuestionService} from "./question.service";
import {ReportService} from "./report-dialog/report.service";
import {VoteService} from "./vote.service";


const routes: Routes = [
  {path: '', component: QuestionPageComponent},
  {path: 'edit', component: EditEntryComponent}
];

@NgModule({
  declarations: [AnswerComponent,
    AnswerQuestionComponent,
    CommentSectionComponent,
    EditEntryComponent,
    QuestionComponent,
    ReportDialogComponent,
    QuestionPageComponent],
  imports: [
    SharedModule,
    EditorModule,
    MatDialogModule,
    RouterModule.forChild(routes)
  ], providers: [
    AnswerService, CommentService, EditEntryService, QuestionService, ReportService, VoteService
  ]
})
export class QuestionModule {
}

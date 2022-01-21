import {NgModule} from '@angular/core';
import {SharedModule} from "../shared.module";
import {QuestionListComponent} from "./question-list.component";
import {QuestionPreviewComponent} from "./question-preview/question-preview.component";
import {CategoryService} from "../category/category.service";
import {QuestionService} from "../question/question.service";
import {QuestionListService} from "./question-list.service";
import {RouterModule, Routes} from "@angular/router";
import {EditorModule} from "../editor/editor.module";

const routes: Routes = [
  {path: '', component: QuestionListComponent},
];

@NgModule({
  declarations: [QuestionListComponent, QuestionPreviewComponent],
  imports: [
    SharedModule,
    EditorModule,
    RouterModule.forChild(routes)
  ], providers: [
    QuestionService,
    QuestionListService,
    CategoryService,
  ]
})
export class QuestionListModule {
}

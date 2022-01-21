import {NgModule} from '@angular/core';
import {SharedModule} from "../shared.module";
import {QuestionAskComponent} from "./question-ask.component";
import {EditorModule} from "../editor/editor.module";
import {ReactiveFormsModule} from "@angular/forms";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {QuestionService} from "../question/question.service";
import {CategoryService} from "../category/category.service";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: QuestionAskComponent},
];

@NgModule({
  declarations: [QuestionAskComponent],
  imports: [
    SharedModule,
    EditorModule,
    ReactiveFormsModule,
    NgxMatSelectSearchModule,
    RouterModule.forChild(routes)
  ], providers: [
    QuestionService,
    CategoryService
  ]
})
export class QuestionAskModule {
}

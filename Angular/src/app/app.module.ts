import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {QuillModule} from "ngx-quill";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {AuthenticationPageComponent} from "./authentication/page/authentication-page.component";
import {HomeComponent} from './components/home/home.component';
import {QuestionPreviewComponent} from './components/question-preview/question-preview.component';
import {MatIconModule} from "@angular/material/icon";
import {AppComponent} from './app.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {NavigationComponent} from './components/navigation/navigation.component';
import {MatListModule} from "@angular/material/list";
import {MatToolbarModule} from "@angular/material/toolbar";
import {AskQuestionComponent} from './components/ask-question/ask-question.component';
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {ViewQuestionComponent} from './components/view-question/view-question.component';
import {QuestionComponent} from './components/question/question.component';
import {MatCardModule} from "@angular/material/card";
import localeSk from '@angular/common/locales/sk';
import {registerLocaleData} from "@angular/common";
import {CommentSectionComponent} from './components/comment-section/comment-section.component';
import {AnswerComponent} from './components/answer/answer.component';
import {AnswerQuestionComponent} from './components/answer-question/answer-question.component';
import {ListQuestionsComponent} from './components/list-questions/list-questions.component';
import {CategoryComponent} from './components/category/category.component';
import {MatTreeModule} from "@angular/material/tree";
import {AuthenticationService} from "./authentication/authentication.service";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";
import {ProfileComponent} from './components/profile/profile.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {EntryPreviewComponent} from './components/profile/entry-preview/entry-preview.component';

registerLocaleData(localeSk);

@NgModule({
  declarations: [
    AuthenticationPageComponent,
    HomeComponent,
    QuestionPreviewComponent,
    AppComponent,
    NavigationComponent,
    AskQuestionComponent,
    ViewQuestionComponent,
    QuestionComponent,
    CommentSectionComponent,
    AnswerComponent,
    AnswerQuestionComponent,
    ListQuestionsComponent,
    CategoryComponent,
    ProfileComponent,
    EntryPreviewComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    QuillModule.forRoot({
      modules: {
        formula: true,
        syntax: true,
        toolbar: [['formula', 'code-block'],
          ['bold', 'italic', 'underline', 'strike'],
          [{'header': 1}, {'header': 2}],
          ['link', 'image', 'video'],
          [{'list': 'ordered'}, {'list': 'bullet'}]
        ],
      }
    }),
    MatFormFieldModule,
    MatToolbarModule,
    MatInputModule,
    MatListModule,
    MatIconModule,
    MatSidenavModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    MatSnackBarModule,
    FormsModule,
    MatCardModule,
    MatTreeModule,
    NgxMatSelectSearchModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    InfiniteScrollModule
  ],
  providers: [AuthenticationService, {provide: LOCALE_ID, useValue: 'sk-SK'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

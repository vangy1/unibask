import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {QuillModule} from "ngx-quill";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {AuthenticationPageComponent} from "./authentication/page/authentication-page.component";
import {HomeComponent} from './home/home.component';
import {QuestionPreviewComponent} from './entries/components/preview/question-preview.component';
import {MatIconModule} from "@angular/material/icon";
import {AppComponent} from './app.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {NavigationComponent} from './navigation/navigation.component';
import {MatListModule} from "@angular/material/list";
import {MatToolbarModule} from "@angular/material/toolbar";
import {QuestionAskComponent} from './entries/components/ask/question-ask.component';
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {QuestionViewComponent} from './entries/components/view/question-view.component';
import {QuestionComponent} from './entries/components/question/question.component';
import {MatCardModule} from "@angular/material/card";
import localeSk from '@angular/common/locales/sk';
import {registerLocaleData} from "@angular/common";
import {CommentSectionComponent} from './entries/components/comment-section/comment-section.component';
import {AnswerComponent} from './entries/components/answer/answer.component';
import {AnswerToQuestionComponent} from './entries/components/answer-to-question/answer-to-question.component';

registerLocaleData(localeSk);

@NgModule({
  declarations: [
    AuthenticationPageComponent,
    HomeComponent,
    QuestionPreviewComponent,
    AppComponent,
    NavigationComponent,
    QuestionAskComponent,
    QuestionViewComponent,
    QuestionComponent,
    CommentSectionComponent,
    AnswerComponent,
    AnswerToQuestionComponent
  ],
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
    MatCardModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'sk-SK'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

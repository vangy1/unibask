import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {QuillModule} from "ngx-quill";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {AuthenticationComponent} from "./authentication/authentication.component";
import {HomeComponent} from './home/home.component';
import {QuestionPreviewComponent} from './question-preview/question-preview.component';
import {MatIconModule} from "@angular/material/icon";
import {AppComponent} from './app.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {NavigationComponent} from './navigation/navigation.component';
import {MatListModule} from "@angular/material/list";
import {MatToolbarModule} from "@angular/material/toolbar";
import {AskQuestionComponent} from './ask-question/ask-question.component';
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";

@NgModule({
  declarations: [
    AuthenticationComponent,
    HomeComponent,
    QuestionPreviewComponent,
    AppComponent,
    NavigationComponent,
    AskQuestionComponent
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
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

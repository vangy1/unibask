import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AuthenticationPageComponent} from "./authentication/page/authentication-page.component";
import {AskQuestionComponent} from "./components/ask-question/ask-question.component";
import {UserGuard} from "./authentication/guards/user-guard.service";
import {GuestGuard} from "./authentication/guards/guest.guard";
import {ViewQuestionComponent} from "./components/view-question/view-question.component";
import {ListQuestionsComponent} from "./components/list-questions/list-questions.component";
import {CategoryComponent} from "./components/category/category.component";
import {ProfileComponent} from "./components/profile/profile.component";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [UserGuard]},
  {path: 'authentication', component: AuthenticationPageComponent, canActivate: [GuestGuard]},
  {path: 'ask', component: AskQuestionComponent, canActivate: [UserGuard]},
  {path: 'question', component: ViewQuestionComponent, canActivate: [UserGuard]},
  {path: 'categories', component: CategoryComponent, canActivate: [UserGuard]},
  {path: 'list', component: ListQuestionsComponent, canActivate: [UserGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [UserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

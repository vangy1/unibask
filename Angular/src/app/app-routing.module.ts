import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationPageComponent} from "./authentication/page/authentication-page.component";
import {QuestionAskComponent} from "./question-ask/question-ask.component";
import {UserGuard} from "./authentication/guards/user-guard.service";
import {GuestGuard} from "./authentication/guards/guest.guard";
import {ViewQuestionComponent} from "./question/view-question/view-question.component";
import {QuestionListComponent} from "./question-list/question-list.component";
import {CategoryComponent} from "./category/category.component";
import {ProfileComponent} from "./profile/profile.component";
import {LeaderboardComponent} from "./leaderboard/leaderboard.component";
import {FeedbackComponent} from "./feedback/feedback.component";
import {EditEntryComponent} from "./question/edit-entry/edit-entry.component";

const routes: Routes = [
  {path: '', component: QuestionListComponent, canActivate: [UserGuard]},
  {path: 'authentication', component: AuthenticationPageComponent, canActivate: [GuestGuard]},
  {path: 'ask', component: QuestionAskComponent, canActivate: [UserGuard]},
  {path: 'question', component: ViewQuestionComponent, canActivate: [UserGuard]},
  {path: 'categories', component: CategoryComponent, canActivate: [UserGuard]},
  {path: 'list', component: QuestionListComponent, canActivate: [UserGuard]},
  {path: 'leaderboard', component: LeaderboardComponent, canActivate: [UserGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [UserGuard]},
  {path: 'feedback', component: FeedbackComponent, canActivate: [UserGuard]},
  {path: 'edit', component: EditEntryComponent, canActivate: [UserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

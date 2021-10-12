import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AuthenticationPageComponent} from "./authentication/page/authentication-page.component";
import {QuestionAskComponent} from "./entries/components/ask/question-ask.component";
import {UserGuard} from "./authentication/guards/user-guard.service";
import {GuestGuard} from "./authentication/guards/guest.guard";
import {QuestionViewComponent} from "./entries/components/view/question-view.component";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [UserGuard]},
  {path: 'authentication', component: AuthenticationPageComponent, canActivate: [GuestGuard]},
  {path: 'ask', component: QuestionAskComponent, canActivate: [UserGuard]},
  {path: 'question', component: QuestionViewComponent, canActivate: [UserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

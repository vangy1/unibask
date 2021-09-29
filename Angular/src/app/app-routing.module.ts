import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AuthenticationComponent} from "./authentication/authentication.component";
import {AskQuestionComponent} from "./ask-question/ask-question.component";
import {UserGuard} from "./authentication/user-guard.service";
import {GuestGuard} from "./authentication/guest.guard";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [UserGuard]},
  {path: 'authentication', component: AuthenticationComponent, canActivate: [GuestGuard]},
  {path: 'ask', component: AskQuestionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

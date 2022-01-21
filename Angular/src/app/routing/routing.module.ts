import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserGuard} from "./guards/user-guard.service";
import {GuestGuard} from "./guards/guest.guard";

const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {
    path: 'list',
    loadChildren: () => import('../question-list/question-list.module').then(m => m.QuestionListModule),
    canActivate: [UserGuard]
  },
  {
    path: 'authentication',
    loadChildren: () => import('../authentication/authentication.module').then(m => m.AuthenticationModule),
    canActivate: [GuestGuard]
  },
  {
    path: 'leaderboard',
    loadChildren: () => import('../leaderboard/leaderboard.module').then(m => m.LeaderboardModule),
    canActivate: [UserGuard]
  },
  {
    path: 'ask',
    loadChildren: () => import('../question-ask/question-ask.module').then(m => m.QuestionAskModule),
    canActivate: [UserGuard]
  },
  {
    path: 'question',
    loadChildren: () => import('../question/question.module').then(m => m.QuestionModule),
    canActivate: [UserGuard]
  },
  {
    path: 'categories',
    loadChildren: () => import('../category/category.module').then(m => m.CategoryModule),
    canActivate: [UserGuard]
  },
  {
    path: 'profile',
    loadChildren: () => import('../profile/profile.module').then(m => m.ProfileModule),
    canActivate: [UserGuard]
  },
  {
    path: 'feedback',
    loadChildren: () => import('../feedback/feedback.module').then(m => m.FeedbackModule),
    canActivate: [UserGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [GuestGuard, UserGuard]
})
export class RoutingModule {
}

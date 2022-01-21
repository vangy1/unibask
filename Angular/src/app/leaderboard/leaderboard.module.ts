import {NgModule} from '@angular/core';
import {LeaderboardComponent} from "./leaderboard.component";
import {SharedModule} from "../shared.module";
import {RouterModule, Routes} from "@angular/router";
import {LeaderboardService} from "./leaderboard.service";


const routes: Routes = [
  {path: '', component: LeaderboardComponent},
];

@NgModule({
  declarations: [LeaderboardComponent],
  imports: [SharedModule, RouterModule.forChild(routes)],
  providers: [LeaderboardService]
})
export class LeaderboardModule {

}

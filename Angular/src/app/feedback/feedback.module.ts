import {NgModule} from '@angular/core';
import {SharedModule} from "../shared.module";
import {FeedbackComponent} from "./feedback.component";
import {FeedbackService} from "./feedback.service";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: FeedbackComponent},
];

@NgModule({
  declarations: [FeedbackComponent],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ], providers: [
    FeedbackService
  ]
})
export class FeedbackModule {
}

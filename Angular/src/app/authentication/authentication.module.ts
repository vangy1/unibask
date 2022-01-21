import {NgModule} from '@angular/core';
import {SharedModule} from "../shared.module";
import {AuthenticationPageComponent} from "./page/authentication-page.component";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', outlet: 'auth', component: AuthenticationPageComponent},
];

@NgModule({
  declarations: [AuthenticationPageComponent],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  exports: [AuthenticationPageComponent]
})
export class AuthenticationModule {
}

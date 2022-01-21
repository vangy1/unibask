import {NgModule} from '@angular/core';
import {NotificationComponent} from "./notification.component";
import {SharedModule} from "../shared.module";
import {NotificationService} from "./notification.service";


@NgModule({
  declarations: [NotificationComponent],
  imports: [
    SharedModule
  ], providers: [
    NotificationService
  ]
})
export class NotificationModule {
}

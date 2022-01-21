import {Component} from '@angular/core';
import {NotificationService} from "./notification.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent {

  constructor(private notificationService: NotificationService, private router: Router) {
  }


  refreshNotifications() {
    this.notificationService.getNotification().subscribe()
  }


}

import {Component, OnInit} from '@angular/core';
import {NotificationService} from "./notification.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  constructor(private notificationService: NotificationService, private router: Router) {
  }

  ngOnInit(): void {
    console.log("aaa")
  }

  refreshNotifications() {
    this.notificationService.getNotification().subscribe()
  }


}

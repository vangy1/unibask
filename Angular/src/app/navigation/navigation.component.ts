import {Component, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";
import {NotificationService} from "../notification/notification.service";
import {Notification} from "../notification/notification";
import {map} from "rxjs/operators";

@Component({
  selector: 'navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
})
export class NavigationComponent {
  @ViewChild(MatSidenav, {static: false}) drawer: MatSidenav;
  @ViewChild('toolbar', {static: false}) toolbar: any;
  mail: string

  constructor(private router: Router, private authenticationService: AuthenticationService, private notificationService: NotificationService) {
    this.notificationService.getNotification().subscribe()
  }

  goToRoute(route: string, params: any) {
    this.router.navigate([route], {queryParams: params})
    this.drawer.close();
  }

  goToProfile() {
    this.authenticationService.user.subscribe(user => {
      this.router.navigate(['/profile'], {queryParams: {id: user.id}})
      this.drawer.close();
    })
  }

  fetchNotifications() {
    this.notificationService.getNotification().subscribe()
  }

  hasNewNotifications() {
    return this.notificationService?.notifications?.find(notification => !notification.viewed)
  }

  getNotifications() {
    return this.notificationService.notifications
  }

  goToNotificationUrl(notification: Notification) {
    this.router.navigateByUrl(notification.url)
  }

  markNotificationsAsViewed() {
    this.notificationService.markNotificationsAsViewed().subscribe()
  }

  currentUrlStartsWith(value: string) {
    return this.router.url.startsWith(value)
  }

  currentUrlEndsWith(value: string) {
    return this.router.url.endsWith(value)
  }


  logout() {
    this.authenticationService.logout()
  }

  isOwnProfile() {
    return this.authenticationService.user.pipe(map(user => this.router.url.startsWith('/profile?id=' + user.id)))

  }
}

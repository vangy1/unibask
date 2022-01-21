import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map, tap} from "rxjs/operators";
import {Notification} from "./notification";

@Injectable()
export class NotificationService {
  notifications: Notification[]

  constructor(private http: HttpClient) {
  }

  getNotification() {
    return this.http.get<Notification[]>(environment.apiUrl + '/notification', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: new HttpParams()
    }).pipe(map(response => this.notifications = response))
  }

  markNotificationsAsViewed() {
    return this.http.post(environment.apiUrl + '/notification', {
      notificationIds: this.notifications.filter(notification => !notification.viewed).flatMap(value => value.id)
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: new HttpParams()
    }).pipe(tap(() => this.notifications.forEach(notification => notification.viewed = true)))
  }
}

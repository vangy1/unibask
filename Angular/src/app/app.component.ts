import {Component} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter, map} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  route: string;
  showNavigation: Observable<boolean>

  constructor(private router: Router) {
    this.showNavigation = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd),
        map(() => {
          return this.router.url !== '/authentication'
        }))
  }
}

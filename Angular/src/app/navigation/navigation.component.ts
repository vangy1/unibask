import {Component, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {MatSidenav} from "@angular/material/sidenav";
import {AuthenticationService} from "../authentication/authentication.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {
  @ViewChild(MatSidenav, {static: false}) drawer: MatSidenav;


  constructor(public router: Router, public authenticationService: AuthenticationService) {
  }


  goToRoute(route: string) {
    this.router.navigate([route])
    this.drawer.close();
  }
}

import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public a: AuthenticationService, private router: Router) {
  }

  ngOnInit(): void {

  }

  signout() {
    this.a.logout();
  }

  askQuestion() {
    this.router.navigate(['/ask'])

  }
}

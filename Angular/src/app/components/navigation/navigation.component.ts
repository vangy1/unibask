import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Renderer2, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {AuthenticationService} from "../../authentication/authentication.service";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
  changeDetection: ChangeDetectionStrategy.Default

})
export class NavigationComponent {
  @ViewChild(MatSidenav, {static: false}) drawer: MatSidenav;
  @ViewChild('toolbar', {static: false}) toolbar: any;
  mail: string

  constructor(public router: Router, public authenticationService: AuthenticationService, private renderer: Renderer2, private cd: ChangeDetectorRef) {
    console.log(this.router.url)
    // this.router.events
    //   .pipe(filter(event => event instanceof NavigationEnd))
    //   .subscribe((event) => {
    //     if (this.router.url === '/authentication') {
    //       this.renderer.addClass(this.toolbar._elementRef.nativeElement, 'hidden-toolbar');
    //     } else {
    //       this.renderer.removeClass(this.toolbar._elementRef.nativeElement, 'hidden-toolbar');
    //       this.recheckUser()
    //     }
    //   });
  }

  recheckUser() {
    this.authenticationService.checkIfSignedInRequest.pipe(tap(user => {
      this.mail = user.mail.replace("uniba.sk", "")
      this.cd.detectChanges()
    })).subscribe();
  }

  goToRoute(route: string, params: any) {
    this.router.navigate([route], {queryParams: params})
    this.drawer.close();
  }

  logout() {
    this.authenticationService.logout()
  }
}

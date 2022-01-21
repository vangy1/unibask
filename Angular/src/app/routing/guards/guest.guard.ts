import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from "../../authentication/authentication.service";
import {map, tap} from "rxjs/operators";

@Injectable()
export class GuestGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authenticationService.user.pipe(map(value => value == null), tap(value => {
      if (!value) this.router.navigate(['/list'])
    }));
  }
}

import {Component} from '@angular/core';
import {AuthenticationService} from "../authentication.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-authentication-page',
  templateUrl: './authentication-page.component.html',
  styleUrls: ['./authentication-page.component.scss']
})
export class AuthenticationPageComponent {
  public state: AuthenticationState = AuthenticationState.START;
  AuthenticationState = AuthenticationState;

  loginMail: string = "";
  loginPassword: string = "";

  newPassword: string = "";

  registerMail: string = "";
  username: string = "";
  registerPassword: string = "";

  verificationCode: string = "";


  constructor(private authenticationService: AuthenticationService, private snackBar: MatSnackBar, private router: Router) {
  }

  login() {
    this.authenticationService.login(this.getLoginMail(), this.loginPassword).subscribe(() => {
      this.router.navigate(['/']);
    }, error => {
      this.snackBar.open("Zadané prihlasovacie údaje neprísluchajú žiadnému existujúcemu účtu.", undefined, {duration: 3000});
    })
  }

  continueToPasswordChange() {
    this.state = AuthenticationState.PASSWORD_CHANGE
  }

  continueToPasswordChangeVerification() {
    this.state = AuthenticationState.VERIFY_PASSWORD_CHANGE
    this.authenticationService.generateVerificationCode(this.getLoginMail()).subscribe()
  }

  continueToPasswordChangeFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.getLoginMail(), this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_PASSWORD_CHANGE
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.getLoginMail(), undefined, {duration: 3000});
      }
    })
  }

  continueToRegisterVerification() {
    this.state = AuthenticationState.VERIFY_REGISTRATION
    this.authenticationService.generateVerificationCode(this.getRegisterMail()).subscribe()
  }

  continueToRegistrationFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.getRegisterMail(), this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_REGISTRATION
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.getRegisterMail(), undefined, {duration: 3000});
      }
    })
  }

  register() {
    this.authenticationService.register(this.getRegisterMail(), this.registerPassword, this.username, this.verificationCode).subscribe(() => {
      this.router.navigate(['/']);
    })
  }

  completePasswordChange() {
    this.authenticationService.completePasswordChange(this.getLoginMail(), this.newPassword, this.verificationCode).subscribe();
  }

  getLoginMail() {
    if (this.loginMail.endsWith("@uniba.sk")) return this.registerMail
    return this.loginMail + "@uniba.sk";
  }

  getRegisterMail() {
    if (this.registerMail.endsWith("@uniba.sk")) return this.registerMail
    return this.registerMail + "@uniba.sk";
  }


}

enum AuthenticationState {
  START, PASSWORD_CHANGE, VERIFY_PASSWORD_CHANGE, FINISH_PASSWORD_CHANGE, VERIFY_REGISTRATION, FINISH_REGISTRATION
}

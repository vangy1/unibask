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


  isRegisterButtonDisabled = false


  constructor(private authenticationService: AuthenticationService, private snackBar: MatSnackBar, private router: Router) {
  }

  login() {
    this.authenticationService.login(this.getFormattedMail(this.loginMail), this.loginPassword).subscribe(() => {
      this.authenticationService.checkIfSignedInRequest = this.authenticationService.createCheckIfSignedInRequest()
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
    this.authenticationService.generateVerificationCode(this.getFormattedMail(this.loginMail)).subscribe()
  }

  continueToPasswordChangeFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.getFormattedMail(this.loginMail), this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_PASSWORD_CHANGE
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.getFormattedMail(this.loginMail), undefined, {duration: 3000});
      }
    })
  }

  continueToRegisterVerification() {
    this.state = AuthenticationState.VERIFY_REGISTRATION
    this.authenticationService.generateVerificationCode(this.getFormattedMail(this.registerMail)).subscribe()
  }

  continueToRegistrationFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.getFormattedMail(this.registerMail), this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_REGISTRATION
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.getFormattedMail(this.registerMail), undefined, {duration: 3000});
      }
    })
  }

  register() {
    this.isRegisterButtonDisabled = true;
    this.authenticationService.register(this.getFormattedMail(this.registerMail), this.registerPassword, this.username, this.verificationCode).subscribe(() => {
      this.router.navigate(['/']);
    })
  }

  completePasswordChange() {
    this.authenticationService.completePasswordChange(this.getFormattedMail(this.loginMail), this.newPassword, this.verificationCode).subscribe(() => {
      this.router.navigate(['/']);
    })
  }

  getFormattedMail(mail: string) {
    if (mail.indexOf('@') < 0) mail += '@';
    if (mail.endsWith("uniba.sk")) {
      return mail
    }
    return mail + "uniba.sk";
  }

}

enum AuthenticationState {
  START, PASSWORD_CHANGE, VERIFY_PASSWORD_CHANGE, FINISH_PASSWORD_CHANGE, VERIFY_REGISTRATION, FINISH_REGISTRATION
}

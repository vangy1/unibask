import {Component} from '@angular/core';
import {AuthenticationService} from "../authentication.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'authentication-page',
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
    if (this.isUnibaMail(this.loginMail)) {
      this.authenticationService.login(this.loginMail, this.loginPassword).subscribe(() => {
        this.router.navigate(['/list']);
      }, error => {
        this.snackBar.open("Zadané prihlasovacie údaje neprísluchajú žiadnému existujúcemu účtu.", undefined, {duration: 3000});
      })
    } else {
      this.snackBar.open("Mail nie je z domény uniba.sk", undefined, {duration: 3000});
    }
  }

  continueToPasswordChange() {
    this.state = AuthenticationState.PASSWORD_CHANGE
  }

  continueToPasswordChangeVerification() {
    if (this.isUnibaMail(this.loginMail)) {
      this.state = AuthenticationState.VERIFY_PASSWORD_CHANGE
      this.authenticationService.generateVerificationCode(this.loginMail).subscribe()
    } else {
      this.snackBar.open("Mail nie je z domény uniba.sk", undefined, {duration: 3000});
    }
  }

  continueToPasswordChangeFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.loginMail, this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_PASSWORD_CHANGE
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.loginMail, undefined, {duration: 3000});
      }
    })
  }

  continueToRegisterVerification() {
    if (this.isUnibaMail(this.loginMail)) {
      this.state = AuthenticationState.VERIFY_REGISTRATION
      this.authenticationService.generateVerificationCode(this.registerMail).subscribe()
    } else {
      this.snackBar.open("Mail nie je z domény uniba.sk", undefined, {duration: 3000});
    }
  }

  continueToRegistrationFinish() {
    this.authenticationService.checkAgainstVerificationCode(this.registerMail, this.verificationCode).subscribe((response) => {
      if (response) {
        this.state = AuthenticationState.FINISH_REGISTRATION
      } else {
        this.snackBar.open("Zadaní overovací kód nie je totožný kódu poslanému na mail " + this.registerMail, undefined, {duration: 3000});
      }
    })
  }

  register() {
    this.isRegisterButtonDisabled = true;
    this.authenticationService.register(this.registerMail, this.registerPassword, this.username, this.verificationCode).subscribe({
      next: () => this.router.navigate(['/list']),
      error: (error) => this.snackBar.open(error.error.message, undefined, {duration: 3000})
    })
  }

  completePasswordChange() {
    this.authenticationService.completePasswordChange(this.loginMail, this.newPassword, this.verificationCode).subscribe(() => {
      this.router.navigate(['/list']);
    })
  }

  private isUnibaMail(mail: string) {
    return mail.endsWith("@uniba.sk") || mail.endsWith("@fmph.uniba.sk");
  }
}

enum AuthenticationState {
  START, PASSWORD_CHANGE, VERIFY_PASSWORD_CHANGE, FINISH_PASSWORD_CHANGE, VERIFY_REGISTRATION, FINISH_REGISTRATION
}

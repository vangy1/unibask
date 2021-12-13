import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {Router} from "@angular/router";
import {QuestionService} from "../../entry/question/question.service";
import {Question} from "../../entry/question/question";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  recentQuestions: Question[]
  readonly RECENT_QUESTIONS_TO_DISPLAY = 5

  constructor(public authenticationService: AuthenticationService, private questionService: QuestionService, private router: Router) {
  }

  ngOnInit(): void {
    this.questionService.getQuestions(0, this.RECENT_QUESTIONS_TO_DISPLAY).subscribe(next => this.recentQuestions = next.slice(0, this.RECENT_QUESTIONS_TO_DISPLAY))
  }

  signout() {
    this.authenticationService.logout();
  }

  askQuestion() {
    this.router.navigate(['/ask'])
  }

  goToAllQuestion() {
    this.router.navigate(['/list'])
  }

  goToCategories() {
    this.router.navigate(['/categories'])
  }
}

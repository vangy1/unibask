import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../authentication/authentication.service";
import {HttpClient, HttpEventType} from "@angular/common/http";
import {ProfileService} from "./profile.service";
import {ProfileEntry} from "./profile-entry";
import {StudyProgramService} from "./study-program.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../authentication/user";
import {Observable, Observer} from "rxjs";
import {shareReplay} from "rxjs/operators";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  @ViewChild('imageInput', {static: false}) imageInput: any;
  newAvatar: string;
  uploadProgress = -1;
  uploadPictureButtonText = "Nahraj avatar"
  profileEntries: ProfileEntry[] = []
  user: User

  selectedStudyProgram = 'not-set';
  selectedMailNotifications = 'not-set';

  oldPassword: string;
  newPassword: string;

  constructor(public authenticationService: AuthenticationService, private router: Router, private http: HttpClient, private profileService: ProfileService, private studyProgramService: StudyProgramService, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.profileService.getUser(params['id']).subscribe((user) => this.user = user);
      this.profileService.getProfileEntries(params['id']).subscribe((profileEntries: ProfileEntry[]) => this.profileEntries = profileEntries)
    });
  }

  ngOnInit(): void {
    this.authenticationService.user.subscribe(user => {
      this.newAvatar = user.avatar;
    })
  }

  saveImage() {
    this.profileService.saveImageRequest(this.newAvatar).subscribe(() => {
      this.authenticationService.user.subscribe(user => {
        user.avatar = this.newAvatar
        this.authenticationService.user = new Observable<User>((observer: Observer<User>) => {
          observer.next(user)
        }).pipe(shareReplay(1))
      })
      this.user.avatar = this.newAvatar
    })
  }

  uploadImagePick() {
    this.uploadImage(this.imageInput);
    this.imageInput.nativeElement.value = '';
  }

  uploadImage(fileInput: any) {
    this.uploadProgress = 0;
    this.profileService.getUploadRequest(fileInput.nativeElement.files[0]).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.uploadProgress = Math.round(100 * event.loaded / event.total);
        this.uploadPictureButtonText = "Uploading (" + this.uploadProgress + ")";
      } else if (event.type === HttpEventType.Response) {
        this.newAvatar = event.body.toString()
        this.uploadPictureButtonText = "Nahraj avatar"
        this.uploadProgress = -1
      }
    });
  }

  setNewPictureSeed() {
    let seed = Math.random() * 99999999;
    this.newAvatar = "https://avatars.dicebear.com/api/adventurer/:" + seed + ".svg"
  }

  getStudyPrograms() {
    return this.studyProgramService.studyPrograms;
  }

  saveProfileDetails() {
    let studyProgramId = this.selectedStudyProgram;
    if (studyProgramId != 'not-set') {
      this.profileService.setStudyProgram(studyProgramId).subscribe(() => {
        let newStudyProgram = this.studyProgramService.studyPrograms.find((program) => String(program.id) == studyProgramId)

        this.authenticationService.user.subscribe(user => {
          user.studyProgram = newStudyProgram;
          this.authenticationService.user = new Observable<User>((observer: Observer<User>) => {
            observer.next(user)
          }).pipe(shareReplay(1))
        })

        this.user.studyProgram = newStudyProgram;
        this.selectedStudyProgram = 'not-set'
      })
    }
    if (this.selectedMailNotifications != 'not-set') {
      this.profileService.setMailNotifications(this.selectedMailNotifications);
    }
  }

  changePassword() {
    this.profileService.changePassword(this.oldPassword, this.newPassword).subscribe()
  }

  openEntry(profileEntry: ProfileEntry) {
    this.router.navigate(['/question'], {queryParams: {id: profileEntry.entry.questionId}})
  }
}

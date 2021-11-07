import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {HttpClient, HttpEventType, HttpHeaders, HttpRequest} from "@angular/common/http";
import {environment} from "../../../environments/environment";

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

  constructor(public authenticationService: AuthenticationService, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.newAvatar = this.authenticationService.user.avatar;
  }

  saveImage() {
    return this.http.post(environment.apiUrl + '/avatar', {
      'avatarUrl': this.newAvatar,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).subscribe((response) => this.authenticationService.user.avatar = this.newAvatar)
  }

  uploadImagePick() {
    this.uploadImage(this.imageInput);
    this.imageInput.nativeElement.value = '';
  }

  uploadImage(fileInput: any) {
    this.uploadProgress = 0;
    const formData = new FormData();
    formData.append('avatar', fileInput.nativeElement.files[0]);

    this.getUploadRequest(formData).subscribe(event => {
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

  getUploadRequest(formData: any) {
    return this.http.request(new HttpRequest('POST', environment.apiUrl + '/avatar/upload', formData, {
      responseType: 'text',
      withCredentials: true,
      reportProgress: true,
      headers: new HttpHeaders({
        'ngsw-bypass': 'true',
      })
    }));
  }

  setNewPictureSeed() {
    let seed = Math.random() * 99999999;
    this.newAvatar = "https://avatars.dicebear.com/api/adventurer/:" + seed + ".svg"
  }
}

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ImageHandler, Options} from "ngx-quill-upload";
import Quill from "quill";

Quill.register('modules/imageHandler', ImageHandler);

@Injectable()
export class EditorService {
  modules = {
    formula: true,
    syntax: true,
    toolbar: [['formula', 'code-block'],
      ['bold', 'italic', 'underline', 'strike'],
      [{'header': 1}, {'header': 2}],
      ['link', 'image', 'video'],
      [{'list': 'ordered'}, {'list': 'bullet'}]
    ],
    imageHandler: {
      upload: (picture) => {
        return new Promise(async (resolve, reject) => {
          const uploadData = new FormData();
          uploadData.append('picture', picture, picture.name);
          try {
            let result = await this.http.post(environment.apiUrl + '/picture/upload', uploadData, {
              responseType: 'text',
              headers: new HttpHeaders({
                'ngsw-bypass': 'true',
              })
            }).toPromise();
            resolve(result);
          } catch (error) {
            reject('Upload failed');
          }
        });
      },
    } as Options,
  }

  constructor(private http: HttpClient) {


  }
}

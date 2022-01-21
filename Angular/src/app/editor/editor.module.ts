import {NgModule} from '@angular/core';
import {QuillModule} from "ngx-quill";
import {EditorService} from "./editor.service";


@NgModule({
  declarations: [],
  imports: [
    QuillModule
  ], exports: [
    QuillModule,
  ], providers: [
    EditorService
  ]
})
export class EditorModule {
}

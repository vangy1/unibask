import {NgModule} from '@angular/core';
import {EntryPreviewComponent} from "./entry-preview/entry-preview.component";
import {SharedModule} from "../shared.module";
import {ProfileComponent} from "./profile.component";
import {EditorModule} from "../editor/editor.module";
import {ProfileService} from "./profile.service";
import {StudyProgramService} from "./study-program/study-program.service";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: ProfileComponent},
];

@NgModule({
  declarations: [ProfileComponent, EntryPreviewComponent],
  imports: [
    SharedModule,
    EditorModule,
    RouterModule.forChild(routes)
  ], providers: [
    ProfileService, StudyProgramService
  ]
})
export class ProfileModule {
}

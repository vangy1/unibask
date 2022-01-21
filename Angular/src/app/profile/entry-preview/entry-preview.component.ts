import {Component, Input} from '@angular/core';
import {EntryProfile} from "./entry-profile";

@Component({
  selector: 'entry-preview',
  templateUrl: './entry-preview.component.html',
  styleUrls: ['./entry-preview.component.scss']
})
export class EntryPreviewComponent {
  @Input('profileEntry') profileEntry: EntryProfile

  constructor() {
  }
}

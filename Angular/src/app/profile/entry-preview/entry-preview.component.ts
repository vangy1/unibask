import {Component, Input, OnInit} from '@angular/core';
import {EntryProfile} from "./entry-profile";

@Component({
  selector: 'entry-preview',
  templateUrl: './entry-preview.component.html',
  styleUrls: ['./entry-preview.component.scss']
})
export class EntryPreviewComponent implements OnInit {

  @Input('profileEntry') profileEntry: EntryProfile

  constructor() {
  }

  ngOnInit(): void {
  }
}

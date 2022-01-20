import {Component, Input, OnInit} from '@angular/core';
import {ProfileEntry} from "../profile-entry";

@Component({
  selector: 'app-entry-preview',
  templateUrl: './entry-preview.component.html',
  styleUrls: ['./entry-preview.component.scss']
})
export class EntryPreviewComponent implements OnInit {

  @Input('profileEntry') profileEntry: ProfileEntry

  constructor() {
  }

  ngOnInit(): void {
  }
}

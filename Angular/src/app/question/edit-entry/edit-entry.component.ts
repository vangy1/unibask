import {Component, OnInit, ViewChild} from '@angular/core';
import {QuillEditorBase} from "ngx-quill";
import {ActivatedRoute, Router} from "@angular/router";
import {EditEntryService} from "./edit-entry.service";
import {Entry} from "../entry";
import {EditorService} from "../../editor/editor.service";

@Component({
  selector: 'edit-entry',
  templateUrl: './edit-entry.component.html',
  styleUrls: ['./edit-entry.component.scss']
})
export class EditEntryComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;
  title: string;
  text: string;
  entry: Entry

  constructor(private editEntryService: EditEntryService, private route: ActivatedRoute, private router: Router, private quillService: EditorService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.editEntryService.getEntry(params['id']).subscribe({
        next: (response) => {
          if (response) {
            this.entry = response
          } else {
            this.router.navigate(['/list'])
          }
        },
        error: () => this.router.navigate(['/list'])
      })
    });
  }

  editEntry() {
    this.editEntryService.editEntry(this.entry.id, this.entry.text, this.quillEditorBase.editorElem.innerText).subscribe(() => this.router.navigate(['/question'], {queryParams: {id: this.entry.questionId}}));
  }

  getQuillModules() {
    return this.quillService.modules
  }
}

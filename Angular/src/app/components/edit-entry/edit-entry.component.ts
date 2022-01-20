import {Component, OnInit, ViewChild} from '@angular/core';
import {QuillEditorBase} from "ngx-quill";
import {ActivatedRoute, Router} from "@angular/router";
import {EditEntryService} from "./edit-entry.service";
import {Entry} from "../../entry/entry";
import {QuillModulesService} from "../../quill-modules.service";

@Component({
  selector: 'app-edit-entry',
  templateUrl: './edit-entry.component.html',
  styleUrls: ['./edit-entry.component.scss']
})
export class EditEntryComponent implements OnInit {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;
  title: string;
  text: string;
  entry: Entry


  constructor(private editEntryService: EditEntryService, private route: ActivatedRoute, private router: Router, public quillService: QuillModulesService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.editEntryService.getEntry(params['id']).subscribe({
        next: (response) => {
          if (response) {
            this.entry = response
          } else {
            this.router.navigate([''])
          }
        },
        error: () => this.router.navigate([''])
      })
    });
  }

  editEntry() {
    this.editEntryService.editEntry(this.entry.id, this.entry.text, this.quillEditorBase.editorElem.innerText).subscribe(() => this.router.navigate(['/question'], {queryParams: {id: this.entry.questionId}}));
  }
}

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.scss']
})
export class ReportDialogComponent implements OnInit {
  reportReason: string;

  constructor(private http: HttpClient, public dialogRef: MatDialogRef<ReportDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
  }

  onConfirm(): void {
    this.http.post(environment.apiUrl + '/report', {
      entryId: this.data.entryId,
      reportReason: this.reportReason
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    }).subscribe()
    this.dialogRef.close();
  }

  onDismiss(): void {
    this.dialogRef.close();
  }


}

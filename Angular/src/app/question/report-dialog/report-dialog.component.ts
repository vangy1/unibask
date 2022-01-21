import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {HttpClient} from "@angular/common/http";
import {ReportService} from "./report.service";

@Component({
  selector: 'report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.scss']
})
export class ReportDialogComponent implements OnInit {
  reportReason: string;

  constructor(private http: HttpClient, private reportService: ReportService, public dialogRef: MatDialogRef<ReportDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
  }

  onConfirm(): void {
    this.reportService.reportEntry(this.data.entryId, this.reportReason).subscribe()
    this.dialogRef.close();
  }

  onDismiss(): void {
    this.dialogRef.close();
  }


}

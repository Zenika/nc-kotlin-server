import { Component, Inject } from '@angular/core'
import { CodeService } from '../services/code.service'
import { Observable } from 'rxjs'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material'

@Component({
  selector: 'devheroes-tests-modal',
  templateUrl: './testsmodal.component.html',
  styleUrls: ['./testsmodal.component.scss']
})
export class TestsModalComponent {
  tests = []

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<TestsModalComponent>) {}

  ngOnInit() {
    this.tests = this.data.tests
  }

  closeDialog() {
    this.dialogRef.close()
  }
}

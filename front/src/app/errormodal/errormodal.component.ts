import { Component, Inject } from '@angular/core'
import { CodeService } from '../services/code.service'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material'

@Component({
  selector: 'devheroes-error-modal',
  templateUrl: './errormodal.component.html',
  styleUrls: ['./errormodal.component.scss']
})
export class ErrorModalComponent {
  error = ''

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<ErrorModalComponent>) {}

  ngOnInit() {
    this.error = this.data.error.error
  }

  closeDialog() {
    this.dialogRef.close()
  }
}

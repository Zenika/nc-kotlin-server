import { Component, Inject } from '@angular/core'
import { CodeService } from '../services/code.service'
import { Observable } from 'rxjs'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material'

@Component({
  selector: 'devheroes-step-modal',
  templateUrl: './stepmodal.component.html',
  styleUrls: ['./stepmodal.component.scss']
})
export class StepModalComponent {
  rate = ''

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<StepModalComponent>) {}

  ngOnInit() {
    this.rate = `${Math.ceil(this.data.result.rate * 100)}%`
  }

  closeDialog() {
    this.dialogRef.close()
  }
}

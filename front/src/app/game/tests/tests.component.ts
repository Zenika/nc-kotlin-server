import { Component, Input } from '@angular/core'
import { TestsModalComponent } from '../../testsmodal/testsmodal.component'
import { MatDialog } from '@angular/material'

@Component({
  selector: 'devheroes-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.scss']
})
export class TestsComponent {
  title = 'devheroes-tests'
  @Input() tests = []
  @Input() results = []

  constructor(private modal: MatDialog) {}

  openTests() {
    this.modal
      .open(TestsModalComponent, {
        width: '600px',
        height: '400px',
        data: {
          tests: this.tests,
        }
      })
  }
}

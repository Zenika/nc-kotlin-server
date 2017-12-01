import { Component } from '@angular/core'
import { StateService } from '../services/state.service'
import { CodeService } from '../services/code.service'
import { Observable } from 'rxjs'

@Component({
  selector: 'devheroes-end',
  templateUrl: './end.component.html',
  styleUrls: ['./end.component.scss']
})
export class EndComponent {
  title = 'end'
  state: Observable<any>
  text: string

  constructor(private stateService: StateService, private codeService: CodeService) {}

  async ngOnInit() {
    this.state = await this.stateService.getState()
    this.text = await this.codeService.endingText
  }
}

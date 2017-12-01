import { Injectable } from '@angular/core'
import { Http } from '@angular/http'
import { PlayerService } from './player.service'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'
import { StateService } from './state.service'
import 'rxjs/add/operator/do'
import { StepModalComponent } from '../stepmodal/stepmodal.component'
import { MatDialog } from '@angular/material'

interface ValidateResult {
  rate: number
  score: number
  text: string
}

@Injectable()
export class CodeService {

  constructor(private http: Http, private playerService: PlayerService, private stateService: StateService, private modal: MatDialog) { }

  private tests = new BehaviorSubject([])

  private result = new BehaviorSubject({})

  private isLoading = false

  endingText: string

  getTests() {
    return this.tests.asObservable()
  }

  getResult() {
    return this.result.asObservable()
  }

  launchTest(code) {
    if (!this.playerService.player) {return}
    const { playerId } = this.playerService.player

    this.isLoading = true
    this.http
      .post(`/api/player/${playerId}/test`, { code: code.replace(/\n/g, '\n') })
      .map(res => res.json())
      .subscribe(tests => {
        this.isLoading = false
        return this.tests.next(tests)
      })
  }

  validate(code) {
    if (!this.playerService.player) {
      return
    }
    const { playerId } = this.playerService.player

    this.isLoading = true
    document.getElementById('launchTest').focus()
    this.http
      .post(`/api/player/${playerId}/validate`, { code: code.replace(/\n/g, '\n') })
      .map(res => res.json())
      .subscribe((result: ValidateResult) => {
        this.result.next(result)
        this.isLoading = false
        this.endingText = result.text
        this.modal
          .open(StepModalComponent, {
            width: '500px',
            height: '200px',
            data: {
              result,
            }
          })
          .afterClosed().subscribe(() => {
            this.stateService.refreshState()
          })
      })
  }
}

import { Component } from '@angular/core'
import { StateService } from '../services/state.service'
import { PlayerService } from '../services/player.service'
import { CodeService } from '../services/code.service'
import { Observable, BehaviorSubject } from 'rxjs'
import 'rxjs/add/operator/do'

const exerciseTimer = 600

@Component({
  selector: 'devheroes-main',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent {
  title = 'devheroes'
  state: Observable<any>
  result: Observable<any>
  formattedOutput = ''
  tests = []
  results = []
  player = {}
  timer = exerciseTimer
  interval = undefined

  constructor(
    private stateService: StateService,
    private codeService: CodeService,
    private playerService: PlayerService,
  ) {}

  async ngOnInit() {
    this.initInterval()

    this.codeService.getResult().subscribe(() => {
      this.interval = clearInterval(this.interval)
    })
    this.player = this.playerService.player
    this.state = this.stateService.getState()
      .do((state: any) => {
        this.timer = exerciseTimer
        if (!this.interval) {
          this.initInterval()
        }
        this.tests = state.tests
        this.formattedOutput = ''
        this.results = []
      })
    this.codeService
      .getTests()
      .subscribe(tests => {
        this.results = tests

        this.formattedOutput = tests.map((output, index) => {
          let errors = ''
          if (output.err !== '') {
            errors = `\n${output.err
            .split('\n')
            .filter(err => err)
            .map(err => `<span class="error">${err}</span>`)
            .join('\n')}`
          }

          let logs = ''
          if (output.out !== '') {
            logs = `\n${output.out
            .split('\n')
            .filter(log => log)
            .map(log => `<span class="log">${log}</span>`)
            .join('\n')}`
          }

          return `<span class="separator">[${this.tests[index].title}]</span>${errors}${logs}\n`
        })
        .join('')
        .split('\n')
        .filter(chunk => chunk)
        .map((chunk, index) => chunk.includes('separator') ? `${index !== 0 ? '\n' : ''}${chunk}` : `> ${chunk}`)
        .join('\n')
      })
  }

  counter() {
    const minutes = Math.floor(this.timer/60) < 10 ? `0${Math.floor(this.timer/60)}` : Math.floor(this.timer/60)
    const secondes = this.timer > 60 ? (this.timer%60 < 10 ? `0${this.timer%60}`: this.timer%60) : (this.timer < 10 ? `0${this.timer}` : this.timer)
    return `${minutes}:${secondes}`
  }

  initInterval() {
    this.interval = setInterval(() => {
      if (this.timer !== 0) {
        this.timer--
      } else {
        this.interval = clearInterval(this.interval)
        var clickEvent = new MouseEvent("click", {
            "view": window,
            "bubbles": true,
            "cancelable": false
        })

        document.getElementById('validate').dispatchEvent(clickEvent)
      }
    }, 1000)
  }

  ngOnDestroy() {
    clearInterval(this.interval)
  }
}

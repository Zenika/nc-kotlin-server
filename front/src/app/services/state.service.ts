import { Injectable } from '@angular/core'
import { Http } from '@angular/http'
import { PlayerService } from './player.service'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'
import { Router } from '@angular/router'
import { apiUrl } from './api.conf'

@Injectable()
export class StateService {

  private state = new BehaviorSubject({})

  constructor(private http: Http, private playerService: PlayerService, private router: Router) { }

  getState() {
    this.refreshState()

    return this.state.asObservable()
  }

  refreshState() {
    if (!this.playerService.player) {
      this.router.navigate(['/landing'])
      return
    }
    const { playerId } = this.playerService.player
    this.http
      .get(`${apiUrl}/player/${playerId}/state`)
      .map(res => res.json())
      .subscribe(state => {
        if (state.finished) {
          this.router.navigate(['/end'])
        }
        return this.state.next(state)
      })
  }
}

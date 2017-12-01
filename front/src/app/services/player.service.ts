import { Injectable } from '@angular/core'
import { Http } from '@angular/http'
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/toPromise'

export interface Player {
  playerId?: string
  name: string
  mail: string
  language: string
}

@Injectable()
export class PlayerService {

  player: Player

  constructor(private http: Http) {}

  async save(player: Player) {
    if (!player) {
      this.player = null
      return
    }

    this.player = await this.http
      .post('/api/player', player)
      .map(res => res.json())
      .toPromise()

    return this.player
  }

}

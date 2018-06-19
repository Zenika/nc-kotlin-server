import { Component } from '@angular/core'
import { PlayerService } from '../services/player.service'
import { ActivatedRoute, Router } from '@angular/router'
import { MatDialog } from '@angular/material'
import { ErrorModalComponent } from '../errormodal/errormodal.component'

@Component({
  selector: 'devheroes-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {
  title = 'landing'
  model = {
    name: '',
    mail: '',
    language: 'kt'
  }

  constructor(private playerService: PlayerService, private router: Router, private modal: MatDialog) {}

  async onSubmit(playerForm) {
    try {
      await this.playerService.save(this.model)
      this.router.navigate(['/game'])
    } catch (res) {
      const error = res.json()
      this.modal
      .open(ErrorModalComponent, {
        width: '500px',
        height: '200px',
        data: {
          error,
        }
      })
    }
  }

  selectLanguage(lang) {
    this.model.language = lang
  }
}

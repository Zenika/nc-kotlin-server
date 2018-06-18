import { Component, OnInit, Input } from '@angular/core'
import { CodeService } from '../../services/code.service'
import { PlayerService } from '../../services/player.service'

@Component({
  selector: 'devheroes-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss']
})
export class EditorComponent {
  @Input() text = ''
  mode = 'javascript'

  constructor(private codeService: CodeService, private playerService: PlayerService) {}

  ngOnInit() {
    const language = this.playerService.player ? this.playerService.player.language : 'js'
    this.mode = language === 'js' ? 'javascript' : this.playerService.player.language
    this.mode = language === 'kt' ? 'kotlin' : this.playerService.player.language
  }

  test() {
    this.codeService.launchTest(this.text)
  }

  validate() {
    this.codeService.validate(this.text)
  }
}

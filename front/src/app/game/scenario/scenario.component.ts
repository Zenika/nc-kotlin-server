import { Component, OnInit, Input } from '@angular/core'

@Component({
  selector: 'devheroes-scenario',
  templateUrl: './scenario.component.html',
  styleUrls: ['./scenario.component.scss']
})
export class ScenarioComponent implements OnInit {

  @Input() scenario = ''

  constructor() { }

  ngOnInit() {
  }

}

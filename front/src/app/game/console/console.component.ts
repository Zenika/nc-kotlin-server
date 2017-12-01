import { Component, Input, ViewEncapsulation } from '@angular/core'

@Component({
  selector: 'devheroes-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ConsoleComponent {
  title = 'devheroes-console'
  @Input() output
}

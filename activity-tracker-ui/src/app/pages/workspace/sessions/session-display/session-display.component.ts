import {Component, input} from '@angular/core';
import {Session} from "../../../../services/models/session";

@Component({
  selector: 'app-session-display',
  standalone: true,
  imports: [],
  templateUrl: './session-display.component.html',
  styleUrl: './session-display.component.scss'
})
export class SessionDisplayComponent {

  sessions = input.required<Session[] | undefined>();


}

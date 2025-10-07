import {Component, input} from '@angular/core';
import {SessionDisplayComponent} from "./session-display/session-display.component";
import {NewSessionComponent} from "./new-session/new-session.component";
import {Session} from "../../../services/models/session";

@Component({
  selector: 'app-sessions',
  standalone: true,
  imports: [
    SessionDisplayComponent,
    NewSessionComponent
  ],
  templateUrl: './sessions.component.html',
  styleUrl: './sessions.component.scss'
})
export class SessionsComponent {

  toggleNewSessions = false;
  sessions = input.required<Session[] | undefined>();

  onToggleNewSession(){
    this.toggleNewSessions = true;
  }

}

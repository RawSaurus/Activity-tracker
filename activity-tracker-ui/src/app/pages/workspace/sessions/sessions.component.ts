import {Component, inject, input, output} from '@angular/core';
import {SessionDisplayComponent} from "./session-display/session-display.component";
import {NewSessionComponent} from "./new-session/new-session.component";
import {Session} from "../../../services/models/session";
import {SessionService} from "./session.service";

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
  newSessionOutput = output<Session>();
  sessionService = inject(SessionService);

  ngOnInit(){
    this.sessionService.getSessions({
      "activity-id": 1
    });
    console.log('sessions: ' + this.sessionService.sessions);
    this.sessionService.addSession({
      name: 'FE test',
      info: 'FE info'
    });
  }

  onToggleNewSession(val: boolean){
    this.toggleNewSessions = val;
  }

  addNewSession(newSession: Session){
    this.newSessionOutput.emit(newSession);
    this.toggleNewSessions = false;
  }
}

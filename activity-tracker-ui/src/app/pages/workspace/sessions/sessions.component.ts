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
  selectedActivity = input.required<number | undefined>()
  sessionService = inject(SessionService);

  ngOnInit(){
    this.sessionService.setSelectedActivity(this.selectedActivity() ?? 0)
    this.sessionService.loadSessions();
    console.log("sessions");
    console.log(this.sessionService.selectedActivity);
  }

  ngOnChanges(){
    this.sessionService.loadSessions();
  }

  onToggleNewSession(val: boolean){
    this.toggleNewSessions = val;
  }
  //
  // addNewSession(newSession: Session){
  //   this.toggleNewSessions = false;
  // }
}

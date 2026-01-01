import {Component, inject, input, OnInit} from '@angular/core';
import {Session} from "../../../../services/models/session";
import {SessionService} from "../session.service";
import {SessionResponse} from "../../../../services/models/session-response";

@Component({
  selector: 'app-session-display',
  standalone: true,
  imports: [],
  templateUrl: './session-display.component.html',
  styleUrl: './session-display.component.scss'
})
export class SessionDisplayComponent implements OnInit{

  // sessions = input.required<Session[] | undefined>();
  sessionService = inject(SessionService);
  displaySessions: {session: SessionResponse, showNotes: boolean}[] = [];
  showNotes = false;
  session = input.required<SessionResponse>()

  ngOnInit(){
    // const array: Session[] = [...this.sessions() as Session[]]
    // for (const s of array) {
    //   this.displaySessions.push({
    //     session: s,
    //     showNotes: false,
    //   })
    // }
  }

  toggleNotes(){
    this.showNotes = !this.showNotes;
  }


}

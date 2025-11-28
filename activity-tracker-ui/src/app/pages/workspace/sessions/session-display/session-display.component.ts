import {Component, input, OnInit} from '@angular/core';
import {Session} from "../../../../services/models/session";

@Component({
  selector: 'app-session-display',
  standalone: true,
  imports: [],
  templateUrl: './session-display.component.html',
  styleUrl: './session-display.component.scss'
})
export class SessionDisplayComponent implements OnInit{

  sessions = input.required<Session[] | undefined>();
  displaySessions: {session: Session, showNotes: boolean}[] = [];

  ngOnInit(){
    const array: Session[] = [...this.sessions() as Session[]]
    for (const s of array) {
      this.displaySessions.push({
        session: s,
        showNotes: false,
      })
    }
  }

  toggleNotes(name: string){
    this.displaySessions.filter(s => {
      if(s.session.name === name){
        s.showNotes = !s.showNotes;
      }
    });
  }


}

import {inject, Injectable} from '@angular/core';
import {Session} from "../../../services/models/session";
import {SessionControllerService} from "../../../services/services/session-controller.service";
import {FindAllSessions$Params} from "../../../services/fn/session-controller/find-all-sessions";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  sessionController = inject(SessionControllerService);
  sessions: Session[] = [];

  constructor() { }

  getSessions(params: FindAllSessions$Params){
    this.sessionController.findAllSessions(params)
      .subscribe({
        next: val => {console.log(val);
          this.sessions = val.content ?? [];}
      });
  }

  addSession(session: Session){
    this.sessions.push(session);
    this.sessionController.createSession({
      "activity-id": 1,
      body: session
    }).subscribe();
  }

  deleteSession(sessionId: string){

  }

}

import {inject, Injectable} from '@angular/core';
import {Session} from "../../../services/models/session";
import {SessionControllerService} from "../../../services/services/session-controller.service";
import {FindAllSessions$Params} from "../../../services/fn/session-controller/find-all-sessions";
import {lastValueFrom} from "rxjs";
import {SessionRequest} from "../../../services/models/session-request";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  sessionController = inject(SessionControllerService);
  sessions: Session[] = [];
  selectedActivity: number = 0;

  constructor() { }

  setSelectedActivity(activityId: number){
    this.selectedActivity = activityId;
  }

  async loadSessions(){
    const res = await lastValueFrom(this.sessionController.findAllSessions({
      "activity-id": this.selectedActivity
    }));

    this.sessions = res.content ?? [];
  }

  getSessions(){
    this.sessionController.findAllSessions({
      "activity-id": this.selectedActivity
    })
      .subscribe({
        complete: () => this.loadSessions()
      });
  }

  addSession(req: SessionRequest){
    this.sessionController.createSession({
      "activity-id": this.selectedActivity,
      body: req
    }).subscribe({
      complete: () => this.loadSessions()
    });
  }

  deleteSession(sessionId: number){
    this.sessionController.deleteSession({
      "activity-id": this.selectedActivity,
      "session-id": sessionId
    }).subscribe({
      complete: () => this.loadSessions()
    });
  }

}

import {Component, inject, output} from '@angular/core';
import {TimerComponent} from "./timer/timer.component";
import {TimeInputComponent} from "./time-input/time-input.component";
import {Router} from "@angular/router";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {Session} from "../../../../services/models/session";

export type TimeData = {
  start: string,
  end: string,
  notes: string[],
}

@Component({
  selector: 'app-new-session',
  standalone: true,
  imports: [
    TimerComponent,
    TimeInputComponent,
    ReactiveFormsModule,
    DatePipe
  ],
  templateUrl: './new-session.component.html',
  styleUrl: './new-session.component.scss'
})
export class NewSessionComponent {

  timerOrInput: 'Timer' | 'Input' = 'Input';
  timeData: TimeData = {start:'', end:'', notes:[]};
  return = output<boolean>();
  showNewForm = false;
  sessionToOutput = output<Session>();

  newSessionForm = new FormGroup({
    name: new FormControl(''),
    info: new FormControl(''),
    start: new FormControl(''),
    end: new FormControl(''),
    duration: new FormControl(0),
  });

  toggleTimerOrInput(){
    this.timerOrInput = this.timerOrInput === 'Timer' ? 'Input' : 'Timer';
    console.log(this.timerOrInput);
  }

  openNewSessionForm(timeData: TimeData){
    this.timeData = timeData;
    this.showNewForm = true;
    this.newSessionForm.controls.start.setValue(timeData.start);
    this.newSessionForm.controls.end.setValue(timeData.end);


    this.newSessionForm.controls.duration.setValue(this.calculateDuration(timeData.start, timeData.end));
  }

  private calculateDuration(start: string, end: string){
    let startDate = new Date(start);
    let endDate = new Date(end);
    const offset = (startDate.getTimezoneOffset() / 60) * -1;
    startDate.setTime(startDate.getTime() + (offset*60) * 60 * 1000);
    endDate.setTime(endDate.getTime() + (offset*60) * 60 * 1000);

    const duration = endDate.getTime() - startDate.getTime();
    console.log(duration);

    // return duration.getUTCHours() + ':' + duration.getUTCMinutes() + ':' + duration.getUTCSeconds();
    return duration;
  }

  back(){
    this.return.emit(!window.confirm('Do you want to exit ? Data may be lost'));
  }
  backNoConfirm(){
    const session: Session = {
      name: this.newSessionForm.controls.name.value!,
      info: this.newSessionForm.controls.info.value!,
      start: this.newSessionForm.controls.start.value!,
      finish: this.newSessionForm.controls.end.value!,
      notes: this.timeData.notes,
      duration: this.newSessionForm.controls.duration.value?.toString()
    }
    this.sessionToOutput.emit(session);
    console.log(session);
  }
}

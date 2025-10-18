import {Component, inject, output} from '@angular/core';
import {TimerComponent} from "./timer/timer.component";
import {TimeInputComponent} from "./time-input/time-input.component";
import {Router} from "@angular/router";

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
    TimeInputComponent
  ],
  templateUrl: './new-session.component.html',
  styleUrl: './new-session.component.scss'
})
export class NewSessionComponent {

  timerOrInput: 'Timer' | 'Input' = 'Input';
  timeData: TimeData = {start:'', end:'', notes:[]};
  return = output<boolean>();

  toggleTimerOrInput(){
    this.timerOrInput = this.timerOrInput === 'Timer' ? 'Input' : 'Timer';
    console.log(this.timerOrInput);
  }

  test(timeData: TimeData){
    this.timeData = timeData;
  }

  test1(){
    console.log(this.timeData);
  }

  back(){
    this.return.emit(!window.confirm('Do you want to exit ? Data may be lost'));
  }
}

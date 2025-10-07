import {Component, DestroyRef, inject, OnDestroy} from '@angular/core';
import {DatePipe} from "@angular/common";
import {TimerService} from "./timer.service";
import {Subscription} from "rxjs";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-timer',
  standalone: true,
  imports: [
    DatePipe,
    FormsModule
  ],
  templateUrl: './timer.component.html',
  styleUrl: './timer.component.scss'
})
export class TimerComponent implements OnDestroy{

  time = new Date(0,0,0);
  startDate: Date | undefined = undefined;
  finishDate = new Date(0, 0, 0);
  comment = '';

  subscription = new Subscription();

  timerService = inject(TimerService);
  destroyRef = inject(DestroyRef);

  constructor() {
    this.subscription.add(
      this.timerService.stopWatch$.subscribe({
        next:(val) => {
          this.time = new Date(0,0,0);
          this.time.setSeconds(val);
          console.log(this.time);
        },
        complete: () => this.finishDate.setDate(Date.now())
      })
    );
  }

  ngOnDestroy() {
    this.destroyRef.onDestroy(() => this.subscription.unsubscribe());
  }

  startTimer(){
    if(this.startDate == undefined){
      this.startDate = new Date(Date.now());
    }
    this.timerService.startCount();
  }

  stopTimer(){
   this.timerService.stopCount();
   console.log(this.comment);
  }

  resetTimer(){
    this.timerService.resetCount();
  }

}

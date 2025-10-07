import { Injectable } from '@angular/core';
import {BehaviorSubject, Subscription, timer} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class TimerService {
  private readonly initialTime = 0;

  private timer$: BehaviorSubject<number> = new BehaviorSubject(this.initialTime);
  private lastStoppedTime: number = this.initialTime;
  private timerSubscription: Subscription = new Subscription();
  isRunning = false;

  constructor() { }

  public get stopWatch$(){
    return this.timer$.pipe(
      map((val: number) => val)
    );
  }

  startCount(){
    if(this.isRunning){
      return;
    }

    this.timerSubscription = timer(0, 1000)
      .pipe(map((value) => value + this.lastStoppedTime))
      .subscribe(this.timer$);

    this.isRunning = true;
  }

  stopCount(){
    this.lastStoppedTime = this.timer$.value;
    this.timerSubscription.unsubscribe();
    this.isRunning = false;
  }

  resetCount(){
    this.timerSubscription.unsubscribe();
    this.lastStoppedTime = this.initialTime;
    this.timer$.next(this.initialTime);
    this.isRunning = false;
  }
}

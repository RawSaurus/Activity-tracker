import {Component, DestroyRef, inject, OnDestroy, output} from '@angular/core';
import {DatePipe} from "@angular/common";
import {TimerService} from "./timer.service";
import {Subscription} from "rxjs";
import {FormControl, FormGroup, FormsModule} from "@angular/forms";

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
export class TimerComponent {

  time = new Date(0,0,0);
  startDate: Date | null = null;
  finishDate = new Date(0, 0, 0);
  isActive = false;

  notes: string[] = [];
  currentNote = '';

  sessionData = output<{
    start: string,
    end: string,
    notes: string[],
  }>();

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

  outputSessionData(){
    if(this.isActive){
      this.stopTimer()
    }
    console.log('start date' + this.startDate);
    if(this.startDate) {
      const newSession = {
        start: this.startDate.toString(),
        end: this.finishDate.toString(),
        notes: this.notes,
      }
      console.log(newSession);
      console.log('timer');
      this.sessionData.emit(newSession);
    }

    this.notes = [];
  }

  addNote(){
    this.notes.push(this.currentNote);
    this.currentNote = '';
  }

  deleteNote(index: number){
    this.notes.splice(index, 1);
  }

  ngOnDestroy() {
    // this.destroyRef.onDestroy(() => this.subscription.unsubscribe());
    this.subscription.unsubscribe();
  }

  startTimer(){
    if(!this.startDate){
      this.startDate = new Date(Date.now());
    }
    this.isActive = true;
    this.timerService.startCount();
  }

  stopTimer(){
   this.timerService.stopCount();
   this.finishDate = new Date(Date.now());
   this.isActive = false;
  }

  resetTimer(){
    this.timerService.resetCount();
    this.isActive = false;
    this.startDate = null;
  }

}

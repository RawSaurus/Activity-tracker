import {Component, output} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Session} from "../../../../../services/models/session";

@Component({
  selector: 'app-time-input',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './time-input.component.html',
  styleUrl: './time-input.component.scss'
})
export class TimeInputComponent {

  inputForm = new FormGroup({
    start: new FormControl(''),
    end: new FormControl(''),
  });

  sessionData = output<{
    start: string,
    end: string,
    notes: string[],
  }>();

  notes: string[] = [];
  currentNote = '';

  outputSessionData(){
    const newSession = {
      start: this.inputForm.controls.start.value!,
      end: this.inputForm.controls.end.value!,
      notes: this.notes,
    }
    console.log('input');

    this.sessionData.emit(newSession);

    this.inputForm.reset();
    this.notes = [];
  }

  addNote(){
    this.notes.push(this.currentNote);
    this.currentNote = '';
  }

  deleteNote(index: number){
    this.notes.splice(index, 1);
  }

}

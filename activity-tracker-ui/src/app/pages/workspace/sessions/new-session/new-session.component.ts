import { Component } from '@angular/core';
import {TimerComponent} from "./timer/timer.component";

@Component({
  selector: 'app-new-session',
  standalone: true,
  imports: [
    TimerComponent
  ],
  templateUrl: './new-session.component.html',
  styleUrl: './new-session.component.scss'
})
export class NewSessionComponent {

}

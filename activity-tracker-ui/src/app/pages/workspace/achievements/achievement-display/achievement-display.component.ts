import {Component, input} from '@angular/core';
import {Achievement} from "../../../../services/models/achievement";

@Component({
  selector: 'app-achievement-display',
  standalone: true,
  imports: [],
  templateUrl: './achievement-display.component.html',
  styleUrl: './achievement-display.component.scss'
})
export class AchievementDisplayComponent {

  achievement = input.required<Achievement>();
}

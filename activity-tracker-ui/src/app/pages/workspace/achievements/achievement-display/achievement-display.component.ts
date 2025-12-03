import {Component, input} from '@angular/core';
import {Achievement} from "../../../../services/models/achievement";
import {AchievementResponseV2} from "../../../../services/models/achievement-response-v-2";

@Component({
  selector: 'app-achievement-display',
  standalone: true,
  imports: [],
  templateUrl: './achievement-display.component.html',
  styleUrl: './achievement-display.component.scss'
})
export class AchievementDisplayComponent {

  achievement = input.required<AchievementResponseV2>();
}

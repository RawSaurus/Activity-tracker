import {Component, input} from '@angular/core';
import {AchievementDisplayComponent} from "./achievement-display/achievement-display.component";
import {Achievement} from "../../../services/models/achievement";

@Component({
  selector: 'app-achievements',
  standalone: true,
  imports: [
    AchievementDisplayComponent
  ],
  templateUrl: './achievements.component.html',
  styleUrl: './achievements.component.scss'
} )
export class AchievementsComponent {

  achievements = input.required<Achievement[] | undefined>();

  onAddNewAchievement(){
    // pop up with achievement creation window
  }
}

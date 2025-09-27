import {Component, input} from '@angular/core';
import {AchievementDisplayComponent} from "./achievement-display/achievement-display.component";
import {Achievement} from "../../../services/models/achievement";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-achievements',
  standalone: true,
  imports: [
    AchievementDisplayComponent,
    FormsModule
  ],
  templateUrl: './achievements.component.html',
  styleUrl: './achievements.component.scss'
} )
export class AchievementsComponent {

  achievements = input.required<Achievement[] | undefined>();
  toggleCreateNewAchievement = false;
  newAchievement: Achievement = {};
  typeOptions = ['GOAL', 'DAILY', 'AMOUNT'];

  onAddNewAchievement(){
    this.toggleCreateNewAchievement = true;
  }


}

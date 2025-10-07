import {Component, input} from '@angular/core';
import {AchievementDisplayComponent} from "./achievement-display/achievement-display.component";
import {Achievement} from "../../../services/models/achievement";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Activity} from "../../../services/models/activity";

type achievementOptions = 'GOAL' | 'DAILY' | 'AMOUNT';

@Component({
  selector: 'app-achievements',
  standalone: true,
  imports: [
    AchievementDisplayComponent,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './achievements.component.html',
  styleUrl: './achievements.component.scss'
} )
export class AchievementsComponent {

  typeOptions = ['GOAL', 'DAILY', 'AMOUNT'];

  achievements = input.required<Achievement[] | undefined>();
  toggleCreateNewAchievement = false;
  newAchievement: Achievement = {};

  newAchievementForm = new FormGroup({
    name: new FormControl(''),
    info: new FormControl(''),
    type: new FormControl<achievementOptions>('GOAL')
  });

  onAddNewAchievement(){
    this.toggleCreateNewAchievement = true;
  }

  addNewAchievement(){

    const achievementToAdd: Achievement = {
      name: this.newAchievementForm.controls.name.value!,
      info: this.newAchievementForm.controls.info.value!,
      type: this.newAchievementForm.controls.type.value!
    }
    this.achievements()?.push(achievementToAdd);

    this.toggleCreateNewAchievement = false;
    this.newAchievementForm.reset();
  }


}

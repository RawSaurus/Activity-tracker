import {ChangeDetectorRef, Component, inject, input, output} from '@angular/core';
import {Achievement} from "../../../../services/models/achievement";
import {AchievementResponseV2} from "../../../../services/models/achievement-response-v-2";
import {AchievementControllerService} from "../../../../services/services/achievement-controller.service";
import {AchievementService} from "../achievement.service";

@Component({
  selector: 'app-achievement-display',
  standalone: true,
  imports: [],
  templateUrl: './achievement-display.component.html',
  styleUrl: './achievement-display.component.scss'
})
export class AchievementDisplayComponent {

  achievement = input.required<AchievementResponseV2>();
  achievementService = inject(AchievementService);
  achToUpdate = output<AchievementResponseV2>();
  finishBg = ''

  updateAchievement(){
    this.achToUpdate.emit(this.achievement());
  }

  deleteAchievement(){
    this.achievementService.deleteAchievement(this.achievement().achievementId);
  }

  markFinished(){
    this.achievementService.markFinished(this.achievement().achievementId);
  }
}

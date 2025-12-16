import {ChangeDetectorRef, Component, inject, input} from '@angular/core';
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
  // achievementService = inject(AchievementControllerService);
  // cdRef = inject(ChangeDetectorRef);
  achievementService = inject(AchievementService);

  deleteAchievement(){
  //   this.achievementService.deleteAchievement({
  //     "achievement-id": this.achievement().achievementId
  // }).subscribe({
  //     complete: () => this.cdRef.detectChanges()
  //   });
    this.achievementService.deleteAchievement(this.achievement().achievementId);
  }
}

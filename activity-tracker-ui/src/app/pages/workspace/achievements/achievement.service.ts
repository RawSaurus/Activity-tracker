import {inject, Injectable} from '@angular/core';
import {AchievementResponseV2} from "../../../services/models/achievement-response-v-2";
import {AchievementControllerService} from "../../../services/services/achievement-controller.service";
import {async, asyncScheduler, lastValueFrom, observeOn} from "rxjs";
import {AchievementRequest} from "../../../services/models/achievement-request";

@Injectable({
  providedIn: 'root'
})
export class AchievementService {

  achievements: AchievementResponseV2[] = [];
  achievementService = inject(AchievementControllerService);
  selectedActivity: number = 0;

  constructor() {
  }

  setSelectedActivity(activityId: number){
    this.selectedActivity = activityId;
  }

  async updateAchievements(){
    // this.achievementService.findAll2({
    //   "activity-id": this.selectedActivity() ?? 0
    // }).subscribe({
    //   next: (val) => console.log(val.content)
    // });

   const res = await lastValueFrom(this.achievementService.findAll2({
      "activity-id": this.selectedActivity
    }));

   this.achievements = res.content ?? [];
  }

  addNewAchievement(achievementToAdd: AchievementRequest){

    if(achievementToAdd.type === 'GOAL'){
      this.achievementService.createGoalAchievement({
        "activity-id": this.selectedActivity ?? 0,
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type,
          setXPGain: achievementToAdd.setXPGain,
          totalXP: achievementToAdd.totalXP,
          deadline: achievementToAdd.deadline,
          unit: achievementToAdd.unit,
        }
      })
        .subscribe({
          complete: () => this.updateAchievements()
        });
    }else if(achievementToAdd.type === 'DAILY'){
      this.achievementService.createDailyAchievement({
        "activity-id": this.selectedActivity ?? 0,
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type,
          setXPGain: achievementToAdd.setXPGain,
          totalXP: achievementToAdd.totalXP,
          deadline: achievementToAdd.deadline,
          unit: achievementToAdd.unit,
        }
      })
        .subscribe({
          complete: () => this.updateAchievements()
        });
    }else{
      this.achievementService.createAmountAchievement({
        "activity-id": this.selectedActivity ?? 0,
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type,
          setXPGain: achievementToAdd.setXPGain,
          totalXP: achievementToAdd.totalXP,
          deadline: achievementToAdd.deadline,
          unit: achievementToAdd.unit,
        }
      })
        .subscribe({
          complete: () => this.updateAchievements()
        });
    }
  }

  updateAchievement(activityId: number, achievementId: number, achRequest: AchievementRequest){
    this.achievementService.updateAchievement({
      "activity-id": activityId,
      "achievement-id": achievementId,
      body: achRequest
    }).subscribe({
      complete: () => this.updateAchievements()
    });
  }

  markFinished(achievementId: number){
    return this.achievementService.markFinished({
      "achievement-id": achievementId
    }).subscribe({
      complete: () => this.updateAchievements()
    });
  }

  deleteAchievement(achievementId: number){
    this.achievementService.deleteAchievement({
      "achievement-id": achievementId
    }).subscribe({
      complete: () => this.updateAchievements()
    });
  }
}

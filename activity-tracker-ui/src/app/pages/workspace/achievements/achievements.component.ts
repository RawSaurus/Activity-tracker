import {ChangeDetectorRef, Component, inject, input, OnInit} from '@angular/core';
import {AchievementDisplayComponent} from "./achievement-display/achievement-display.component";
import {Achievement} from "../../../services/models/achievement";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AchievementControllerService} from "../../../services/services/achievement-controller.service";
import {lastValueFrom} from "rxjs";
import {AchievementResponseV2} from "../../../services/models/achievement-response-v-2";
import {AchievementRequest} from "../../../services/models/achievement-request";
import {AchievementService} from "./achievement.service";

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
export class AchievementsComponent implements OnInit{

  private cdRef =  inject(ChangeDetectorRef);

  typeOptions = ['GOAL', 'DAILY', 'AMOUNT'];
  // achievementService = inject(AchievementControllerService);
  achievementService = inject(AchievementService);

  selectedActivity = input.required<number | undefined>();
  achievements: AchievementResponseV2[] = [];
  toggleCreateNewAchievement = false;
  newAchievement: Achievement = {};

  newAchievementForm = new FormGroup({
    base: new FormGroup({
      name: new FormControl(''),
      info: new FormControl(''),
      type: new FormControl<achievementOptions>('GOAL'),
      setXPGain: new FormControl(0),
      totalXP: new FormControl(0)
    }, {validators: [Validators.required]}),
    extra: new FormGroup({
      unit: new FormControl(''),
      deadline: new FormControl(''),
    }),
  });

  ngOnInit() {
    this.achievementService.setSelectedActivity(this.selectedActivity() ?? 0);
    this.achievementService.updateAchievements();
  }

  ngOnChanges(){
    this.achievementService.updateAchievements();
    // this.updateAchievements().then(result => this.achievements = result.content ?? []);
  }

  // async updateAchievements(){
    // this.achievementService.findAll2({
    //   "activity-id": this.selectedActivity() ?? 0
    // }).subscribe({
    //   next: (val) => console.log(val.content)
    // });

  //   const achRes = await lastValueFrom(this.achievementService.findAll2({
  //     "activity-id": this.selectedActivity() ?? 0
  //   }));
  //
  //   return achRes;
  // }

  onAddNewAchievement(){
    this.toggleCreateNewAchievement = true;
  }

  addNewAchievement(){

    const achievementToAdd: AchievementRequest = {
      name: this.newAchievementForm.controls.base.value.name!,
      info: this.newAchievementForm.controls.base.value.info!,
      type: this.newAchievementForm.controls.base.value.type!,
      setXPGain: this.newAchievementForm.controls.base.value.setXPGain ?? 0,
      totalXP: this.newAchievementForm.controls.base.value.totalXP ?? 0,
      deadline: this.newAchievementForm.controls.extra.value.deadline ?? '',
      unit: this.newAchievementForm.controls.extra.value.unit ?? '',
    }

    // if(achievementToAdd.type === 'GOAL'){
    //   this.achievementService.createGoalAchievement({
    //     "activity-id": this.selectedActivity() ?? 0,
    //     body: {
    //       name: achievementToAdd.name!,
    //       info: achievementToAdd.info!,
    //       type: achievementToAdd.type,
    //       setXPGain: achievementToAdd.setXPGain,
    //       totalXP: achievementToAdd.totalXP,
    //       deadline: achievementToAdd.deadline,
    //       unit: achievementToAdd.unit,
    //     }
    //   }).subscribe();
    // }else if(achievementToAdd.type === 'DAILY'){
    //   this.achievementService.createDailyAchievement({
    //     "activity-id": this.selectedActivity() ?? 0,
    //     body: {
    //       name: achievementToAdd.name!,
    //       info: achievementToAdd.info!,
    //       type: achievementToAdd.type,
    //       setXPGain: achievementToAdd.setXPGain,
    //       totalXP: achievementToAdd.totalXP,
    //       deadline: achievementToAdd.deadline,
    //       unit: achievementToAdd.unit,
    //     }
    //   }).subscribe();
    // }else{
    //   this.achievementService.createAmountAchievement({
    //     "activity-id": this.selectedActivity() ?? 0,
    //     body: {
    //       name: achievementToAdd.name!,
    //       info: achievementToAdd.info!,
    //       type: achievementToAdd.type,
    //       setXPGain: achievementToAdd.setXPGain,
    //       totalXP: achievementToAdd.totalXP,
    //       deadline: achievementToAdd.deadline,
    //       unit: achievementToAdd.unit,
    //     }
    //   }).subscribe();
    // }
    // // this.achievements.push(achievementToAdd);
    // this.updateAchievements().then(result => this.achievements = result.content ?? []);

    this.achievementService.addNewAchievement(achievementToAdd);

    this.toggleCreateNewAchievement = false;
    this.newAchievementForm.reset();
  }


}

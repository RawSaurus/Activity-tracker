import {ChangeDetectorRef, Component, inject, input, OnInit} from '@angular/core';
import {AchievementDisplayComponent} from "./achievement-display/achievement-display.component";
import {Achievement} from "../../../services/models/achievement";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AchievementControllerService} from "../../../services/services/achievement-controller.service";
import {lastValueFrom} from "rxjs";
import {AchievementResponseV2} from "../../../services/models/achievement-response-v-2";

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
  achievementService = inject(AchievementControllerService);

  selectedActivity = input.required<number | undefined>();
  achievements: AchievementResponseV2[] = [];
  toggleCreateNewAchievement = false;
  newAchievement: Achievement = {};

  newAchievementForm = new FormGroup({
    base: new FormGroup({
      name: new FormControl(''),
      info: new FormControl(''),
      type: new FormControl<achievementOptions>('GOAL'),
      setXpGain: new FormControl(0),
    }, {validators: [Validators.required]}),
    extra: new FormGroup({
      unit: new FormControl(''),
      deadline: new FormControl(''),
    }),
  });

  ngOnInit() {
  }

  ngOnChanges(){
    this.updateAchievements().then(result => this.achievements = result.content ?? []);
  }

  async updateAchievements(){
    // this.achievementService.findAll2({
    //   "activity-id": this.selectedActivity() ?? 0
    // }).subscribe({
    //   next: (val) => console.log(val.content)
    // });

    const achRes = await lastValueFrom(this.achievementService.findAll2({
      "activity-id": this.selectedActivity() ?? 0
    }));

    return achRes;
  }

  onAddNewAchievement(){
    this.toggleCreateNewAchievement = true;
  }

  addNewAchievement(){

    const achievementToAdd: AchievementResponseV2 = {
      name: this.newAchievementForm.controls.base.value.name!,
      info: this.newAchievementForm.controls.base.value.info!,
      type: this.newAchievementForm.controls.base.value.type!,
      deadline: this.newAchievementForm.controls.extra.value.deadline ?? '',
      unit: this.newAchievementForm.controls.extra.value.unit ?? '',
      // setXpGain: this.newAchievementForm.controls.extra.value.setXpGain ?? '',
    }

    if(achievementToAdd.type === 'GOAL'){
      this.achievementService.createGoalAchievement({
        "activity-id": this.selectedActivity() ?? 0,
        deadline: achievementToAdd.deadline?.toString() ?? '',
        setXpGain: 20,
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type
        }
      }).subscribe();
    }else if(achievementToAdd.type === 'DAILY'){
      this.achievementService.createDailyAchievement({
        "activity-id": this.selectedActivity() ?? 0,
        setXpGain: 20,
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type
        }
      }).subscribe();
    }else{
      this.achievementService.createAmountAchievement({
        "activity-id": this.selectedActivity() ?? 0,
        setXpGain: 20,
        unit: achievementToAdd.unit ?? '',
        body: {
          name: achievementToAdd.name!,
          info: achievementToAdd.info!,
          type: achievementToAdd.type
        }
      }).subscribe();
    }
    // this.achievements.push(achievementToAdd);
    this.updateAchievements().then(result => this.achievements = result.content ?? []);

    this.toggleCreateNewAchievement = false;
    this.newAchievementForm.reset();
  }


}

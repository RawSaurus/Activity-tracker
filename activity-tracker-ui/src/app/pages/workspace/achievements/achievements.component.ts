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
  toggleUpdateAchievement = false;
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

  achievementToUpdate = 0;

  ngOnInit() {
    this.achievementService.setSelectedActivity(this.selectedActivity() ?? 0);
    this.achievementService.updateAchievements();
  }

  ngOnChanges(){
    this.achievementService.updateAchievements();
  }


  onAddNewAchievement(){
    this.toggleCreateNewAchievement = true;
  }

  addOrUpdateAchievement(){

    const achievementToAdd: AchievementRequest = {
      name: this.newAchievementForm.controls.base.value.name!,
      info: this.newAchievementForm.controls.base.value.info!,
      type: this.newAchievementForm.controls.base.value.type!,
      setXPGain: this.newAchievementForm.controls.base.value.setXPGain ?? 0,
      totalXP: this.newAchievementForm.controls.base.value.totalXP ?? 0,
      deadline: this.newAchievementForm.controls.extra.value.deadline ?? '',
      unit: this.newAchievementForm.controls.extra.value.unit ?? '',
    }

    if(this.toggleUpdateAchievement){
      this.achievementService.updateAchievement(this.selectedActivity() ?? 0, this.achievementToUpdate, achievementToAdd);
    }else{
      this.achievementService.addNewAchievement(achievementToAdd);
    }

    this.toggleCreateNewAchievement = false;
    this.toggleUpdateAchievement = false;
    this.newAchievementForm.reset();
  }

  updateAchievement(achToUpdate: AchievementResponseV2){
    this.toggleCreateNewAchievement = true;
    this.toggleUpdateAchievement = true;

    this.newAchievementForm.setValue({
      base:{
        name: achToUpdate.name ?? '',
        info: achToUpdate.info ?? '',
        type: achToUpdate.type ?? null,
        setXPGain: 0,
        totalXP: 0
      },
      extra: {
        deadline: achToUpdate.deadline ?? '',
        unit: achToUpdate.unit ?? '',
      }
    })

    this.achievementToUpdate = achToUpdate.achievementId;
  }


}

import {Component, signal} from '@angular/core';
import {Activity} from "../../services/models/activity";
import {AchievementsComponent} from "./achievements/achievements.component";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    AchievementsComponent
  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.scss'
})
export class WorkspaceComponent {

  userActivities: Activity[] = [
    {
      activityId: 1,
      name: 'test1',
      achievements: [
        {
          name: 'achievement test11',
          type: 'GOAL',
          info: 'info about test11',
        },
        {
          name: 'achievement test12',
          type: 'AMOUNT',
          info: 'info about test12',
        },
        {
          name: 'achievement test13',
          type: 'DAILY',
          info: 'info about test13',
        },
      ]
    },
    {
      activityId: 2,
      name: 'test2',
      achievements: [
        {
          name: 'achievement test21',
          type: 'GOAL',
          info: 'info about test21',
        },
        {
          name: 'achievement test22',
          type: 'AMOUNT',
          info: 'info about test22',
        },
        {
          name: 'achievement test23',
          type: 'DAILY',
          info: 'info about test23',
        },
      ]
    }
  ];
  chosenActivity= signal<Activity>({});
  activityOption = signal<'ACHIEVEMENTS' | 'SESSIONS' | 'SETTINGS'>('ACHIEVEMENTS');

  ngOnInit(){
    console.log(this.chosenActivity().name);
  }

  onChooseActivity(chosenActivity: Activity){
    this.chosenActivity.update((a) => a = chosenActivity);
  }

  onChooseActivityOptions(option: 'ACHIEVEMENTS' | 'SESSIONS' | 'SETTINGS'){
    this.activityOption.update((o) => o = option);
  }
}

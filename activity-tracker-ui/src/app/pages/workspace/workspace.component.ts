import {Component, signal} from '@angular/core';
import {Activity} from "../../services/models/activity";
import {AchievementsComponent} from "./achievements/achievements.component";
import {FormsModule} from "@angular/forms";
import {ActivityRequest} from "../../services/models/activity-request";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    AchievementsComponent,
    FormsModule
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
  isSidebarActive = signal(false);
  groups: {name: string, activities: Activity[], isActive: boolean, sign: string}[] = [
    {
      name: 'Uncategorized',
      activities: this.userActivities,
      isActive: false,
      sign: '+',
    }
  ];
  newGroup = signal(false);
  newGroupName = '';
  newActivity: Activity = {
    info: "",
    type: "",
    name: ''
  };

  ngOnInit(){
    console.log(this.chosenActivity().name);
  }

  onChooseActivity(chosenActivity: Activity){
    this.chosenActivity.update((a) => a = chosenActivity);
  }

  onChooseActivityOptions(option: 'ACHIEVEMENTS' | 'SESSIONS' | 'SETTINGS'){
    this.activityOption.update((o) => o = option);
  }

  onToggleSidebar(){
    this.isSidebarActive.set(!this.isSidebarActive());
  }

  openCreateGroupPopup(){
    this.newGroup.update((value) => value = true);
  }

  createGroup(name: string){
   this.groups.push({
     name: name,
     activities: [],
     isActive: false,
     sign: '+',
   });
   this.newGroup.set(false);
  }

  toggleIsGroupActive(name: string){
    this.groups.some((g) => {
      if(g.name === name){
        g.isActive = !g.isActive;
        if(g.isActive){
          g.sign = '-';
        }else{
          g.sign = '+';
        }
      }
    });
  }

  showNewActivityWindow(){
    this.chosenActivity.set({});
  }

  addNewActivity(){
    // this.userActivities.push(this.newActivity);
    //send request to backend here
    // const activityToAdd = new Activity{
    //   this.newActivity.name,
    //   info: this.newActivity.info,
    //   category: this.newActivity.category,
    //   private: this.newActivity.private,
    //   type: this.newActivity.type
    // }
    const activityToAdd: Activity = {
      name: this.newActivity.name,
      info: this.newActivity.info,
      category: this.newActivity.category,
      isPrivate: this.newActivity.isPrivate,
      type: this.newActivity.type
    }
    this.userActivities.push(activityToAdd);
    // this.groups.some((g) => {
    //   if(g.name === 'Uncategorized'){
    //     g.activities.push(activityToAdd);
    //   }
    // });
    this.chosenActivity.set(activityToAdd);
    this.newActivity = {};
    console.log(this.userActivities);
  }
}


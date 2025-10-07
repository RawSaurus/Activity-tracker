import {afterNextRender, Component, DestroyRef, effect, inject, signal} from '@angular/core';
import {Activity} from "../../services/models/activity";
import {AchievementsComponent} from "./achievements/achievements.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivityRequest} from "../../services/models/activity-request";
import {debounceTime, of} from "rxjs";
import {SessionsComponent} from "./sessions/sessions.component";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    AchievementsComponent,
    FormsModule,
    ReactiveFormsModule,
    SessionsComponent
  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.scss'
})
export class WorkspaceComponent {

  private destroyRef = inject(DestroyRef);

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
      ],
      sessions: [
        {
          name: 'learning',
          duration: '2min',
          info: 'test session',
          start: '2.12. 24',
          notes: ['none', 'none2']
        },
        {
          name: 'learning 2',
          duration: '2min',
          info: 'test session',
          start: '2.12. 24',
          notes: ['none', 'none2']
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
  isSidebarActive = signal(true);
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

  newActivityForm = new FormGroup({
    name: new FormControl('',{
      validators: [Validators.required]
    }),
    info: new FormControl('',{
      validators: [Validators.required]
    }),
    category: new FormControl('', {
      validators: [Validators.required]
    }),
    isPrivate: new FormControl(false),
    type: new FormControl('',{
      validators: [Validators.required]
    }),
  });

  constructor() {
    afterNextRender(() => {
      const savedIsSidebarActive = localStorage.getItem('is-sidebar-active');

      if (savedIsSidebarActive) {
        this.isSidebarActive.update((v) => JSON.parse(savedIsSidebarActive));
      }
    });

    // const subscription = of(this.isSidebarActive()).pipe(debounceTime(500))
    //   .subscribe({
    //     next: value => {
      //   }
      // });
    //
    // this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }


  onChooseActivity(chosenActivity: Activity){
    this.chosenActivity.update((a) => a = chosenActivity);
  }

  onChooseActivityOptions(option: 'ACHIEVEMENTS' | 'SESSIONS' | 'SETTINGS'){
    this.activityOption.update((o) => o = option);
  }

  onToggleSidebar(){
    this.isSidebarActive.set(!this.isSidebarActive());
    localStorage.setItem('is-sidebar-active', JSON.stringify(this.isSidebarActive()));
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
      name: this.newActivityForm.controls.name.value!,
      info: this.newActivityForm.controls.info.value!,
      category: this.newActivityForm.controls.category.value!,
      isPrivate: this.newActivityForm.controls.isPrivate.value!,
      type: this.newActivityForm.controls.type.value!
    }
    this.userActivities.push(activityToAdd);
    // this.groups.some((g) => {
    //   if(g.name === 'Uncategorized'){
    //     g.activities.push(activityToAdd);
    //   }
    // });
    this.chosenActivity.set(activityToAdd);
    this.newActivityForm.reset();
    console.log(this.userActivities);
  }
}


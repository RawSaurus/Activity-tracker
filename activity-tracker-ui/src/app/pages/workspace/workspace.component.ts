import {afterNextRender, Component, DestroyRef, inject, OnInit, signal} from '@angular/core';
import {Activity} from "../../services/models/activity";
import {AchievementsComponent} from "./achievements/achievements.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SessionsComponent} from "./sessions/sessions.component";
import {ActivityControllerService} from "../../services/services/activity-controller.service";
import {Session} from "../../services/models/session";
import {ActivityResponse} from "../../services/models/activity-response";
import {delay, firstValueFrom, interval, lastValueFrom} from "rxjs";

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
export class WorkspaceComponent implements OnInit{
  private activityService = inject(ActivityControllerService);

  private destroyRef = inject(DestroyRef);

  userActivities: Activity[] = [];

  userActivitiesTest: Activity[] = [
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
          achievementId: 1,
          name: 'achievement test21',
          type: 'GOAL',
          info: 'info about test21',
        },
        {
          achievementId: 2,
          name: 'achievement test22',
          type: 'AMOUNT',
          info: 'info about test22',
        },
        {
          achievementId: 3,
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

  async ngOnInit(){
    //call for getUserActivities
    this.groups = [];
    let list: ActivityResponse[] = [];

    console.log(1);
    this.activityService.findAll1().subscribe({
      next: activities =>{
        list = activities.content ?? [];
      }
    });

    const i = await lastValueFrom(this.activityService.findAll1());


    console.log(3);
    console.log(list);

    this.userActivities = [];

    list.forEach(value => {
      this.userActivities.push({
        name: value.name,
        info: value.info,
        type: value.type,
        category: value.category
      })
    });

    console.log(4);
    console.log(this.userActivities);

    this.userActivities[0].activityId = 1;
    this.userActivities[1].activityId = 2;

    if(this.userActivities.length !== 0){
      this.chosenActivity.set(this.userActivities[0]);
    }
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
    this.activityService.createActivity({
      body: {
        name: activityToAdd.name ?? '',
        info: activityToAdd.info ?? '',
        type: activityToAdd.type ?? '',
        category: activityToAdd.category,
        isPrivate: activityToAdd.isPrivate
      }
    }).subscribe();
    this.chosenActivity.set(activityToAdd);
    this.newActivityForm.reset();
    console.log(this.userActivities);
  }

  addNewSession(session: Session){
    this.userActivities.filter((a) => {
      if(a.name === this.chosenActivity().name){
        a.sessions?.push(session);
      }
    });
  }

  deleteActivity(){
  }
}


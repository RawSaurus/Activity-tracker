import {Routes} from "@angular/router";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {AppComponent} from "./app.component";
import {WorkspaceComponent} from "./pages/workspace/workspace.component";
import {TemplatesComponent} from "./pages/templates/templates.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {SettingsComponent} from "./pages/settings/settings.component";
import {AchievementsComponent} from "./pages/workspace/achievements/achievements.component";
import {SessionsComponent} from "./pages/workspace/sessions/sessions.component";
import {SessionDisplayComponent} from "./pages/workspace/sessions/session-display/session-display.component";
import {NewSessionComponent} from "./pages/workspace/sessions/new-session/new-session.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'activate-account',
    component: ActivateAccountComponent
  },
  {
    path: 'workspace',
    component: WorkspaceComponent,
    children: [
      {
        path: 'achievements',
        component: AchievementsComponent
      },
      {
        path: 'sessions',
        component: SessionsComponent,
        children: [
          {
            path: 'new',
            component: NewSessionComponent
          }
        ]
      }
    ]
  },
  {
    path: 'templates',
    component: TemplatesComponent
  },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
];

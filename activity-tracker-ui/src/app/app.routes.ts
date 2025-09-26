import {Routes} from "@angular/router";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {AppComponent} from "./app.component";
import {WorkspaceComponent} from "./pages/workspace/workspace.component";
import {TemplatesComponent} from "./pages/templates/templates.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {SettingsComponent} from "./pages/settings/settings.component";

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
    path: 'workspace',
    component: WorkspaceComponent
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

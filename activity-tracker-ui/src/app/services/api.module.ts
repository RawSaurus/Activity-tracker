/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { TemplateControllerService } from './services/template-controller.service';
import { SessionControllerService } from './services/session-controller.service';
import { ProfileControllerService } from './services/profile-controller.service';
import { GroupControllerService } from './services/group-controller.service';
import { FileControllerService } from './services/file-controller.service';
import { CommentControllerService } from './services/comment-controller.service';
import { CalendarControllerService } from './services/calendar-controller.service';
import { ActivityControllerService } from './services/activity-controller.service';
import { AchievementControllerService } from './services/achievement-controller.service';
import { AuthenticationService } from './services/authentication.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    TemplateControllerService,
    SessionControllerService,
    ProfileControllerService,
    GroupControllerService,
    FileControllerService,
    CommentControllerService,
    CalendarControllerService,
    ActivityControllerService,
    AchievementControllerService,
    AuthenticationService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}

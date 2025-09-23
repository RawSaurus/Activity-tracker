/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementRequest } from '../../models/achievement-request';

export interface CreateGoalAchievement$Params {
  'activity-id': number;
  deadline: string;
  setXpGain: number;
      body: AchievementRequest
}

export function createGoalAchievement(http: HttpClient, rootUrl: string, params: CreateGoalAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, createGoalAchievement.PATH, 'post');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.query('deadline', params.deadline, {});
    rb.query('setXpGain', params.setXpGain, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return (r as HttpResponse<any>).clone({ body: parseFloat(String((r as HttpResponse<any>).body)) }) as StrictHttpResponse<number>;
    })
  );
}

createGoalAchievement.PATH = '/achievement/goal-achievement/{activity-id}';

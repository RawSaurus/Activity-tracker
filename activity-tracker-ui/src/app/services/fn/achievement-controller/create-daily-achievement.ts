/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementRequest } from '../../models/achievement-request';

export interface CreateDailyAchievement$Params {
  'activity-id': number;
  setXpGain: number;
      body: AchievementRequest
}

export function createDailyAchievement(http: HttpClient, rootUrl: string, params: CreateDailyAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, createDailyAchievement.PATH, 'post');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
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

createDailyAchievement.PATH = '/achievement/daily-achievement/{activity-id}';

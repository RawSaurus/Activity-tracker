/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementRequest } from '../../models/achievement-request';

export interface CreateAmountAchievement$Params {
  'activity-id': number;
  setXpGain: number;
  unit: string;
      body: AchievementRequest
}

export function createAmountAchievement(http: HttpClient, rootUrl: string, params: CreateAmountAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, createAmountAchievement.PATH, 'post');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.query('setXpGain', params.setXpGain, {});
    rb.query('unit', params.unit, {});
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

createAmountAchievement.PATH = '/achievement/amount-achievement/{activity-id}';

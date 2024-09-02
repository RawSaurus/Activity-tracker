/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementRequest } from '../../models/achievement-request';

export interface UpdateAchievement$Params {
  'activity-id': number;
  'achievement-id': number;
      body: AchievementRequest
}

export function updateAchievement(http: HttpClient, rootUrl: string, params: UpdateAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, updateAchievement.PATH, 'put');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.path('achievement-id', params['achievement-id'], {});
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

updateAchievement.PATH = '/achievement/{activity-id}/{achievement-id}';

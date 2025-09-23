/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementResponseV2 } from '../../models/achievement-response-v-2';

export interface GetFromActivity$Params {
  'activity-id': number;
  'achievement-id': number;
}

export function getFromActivity(http: HttpClient, rootUrl: string, params: GetFromActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponseV2>> {
  const rb = new RequestBuilder(rootUrl, getFromActivity.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.path('achievement-id', params['achievement-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AchievementResponseV2>;
    })
  );
}

getFromActivity.PATH = '/achievement/get-from-activity/{activity-id}/{achievement-id}';

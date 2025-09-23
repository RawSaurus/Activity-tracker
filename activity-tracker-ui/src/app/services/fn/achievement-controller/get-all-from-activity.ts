/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementResponseV2 } from '../../models/achievement-response-v-2';

export interface GetAllFromActivity$Params {
  'activity-id': number;
}

export function getAllFromActivity(http: HttpClient, rootUrl: string, params: GetAllFromActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AchievementResponseV2>>> {
  const rb = new RequestBuilder(rootUrl, getAllFromActivity.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<AchievementResponseV2>>;
    })
  );
}

getAllFromActivity.PATH = '/achievement/get-all-from-activity/{activity-id}';

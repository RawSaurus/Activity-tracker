/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementResponse } from '../../models/achievement-response';

export interface FindById3$Params {
  'activity-id': number;
  'achievement-id': number;
}

export function findById3(http: HttpClient, rootUrl: string, params: FindById3$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponse>> {
  const rb = new RequestBuilder(rootUrl, findById3.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.path('achievement-id', params['achievement-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AchievementResponse>;
    })
  );
}

findById3.PATH = '/achievement/{activity-id}/{achievement-id}';

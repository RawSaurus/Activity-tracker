/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementResponse } from '../../models/achievement-response';

export interface FindAllById$Params {
  'activity-id': number;
}

export function findAllById(http: HttpClient, rootUrl: string, params: FindAllById$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AchievementResponse>>> {
  const rb = new RequestBuilder(rootUrl, findAllById.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<AchievementResponse>>;
    })
  );
}

findAllById.PATH = '/achievement/{activity-id}';

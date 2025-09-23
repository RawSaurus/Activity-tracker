/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AchievementResponseV2 } from '../../models/achievement-response-v-2';

export interface FindById4$Params {
  'achievement-id': number;
}

export function findById4(http: HttpClient, rootUrl: string, params: FindById4$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponseV2>> {
  const rb = new RequestBuilder(rootUrl, findById4.PATH, 'get');
  if (params) {
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

findById4.PATH = '/achievement/find-one/{achievement-id}';

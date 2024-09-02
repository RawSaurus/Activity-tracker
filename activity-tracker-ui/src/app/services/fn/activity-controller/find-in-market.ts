/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityResponse } from '../../models/activity-response';

export interface FindInMarket$Params {
  'activity-id': number;
}

export function findInMarket(http: HttpClient, rootUrl: string, params: FindInMarket$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, findInMarket.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ActivityResponse>;
    })
  );
}

findInMarket.PATH = '/activity/market/{activity-id}';

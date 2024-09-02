/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SessionRequest } from '../../models/session-request';

export interface UpdateSession$Params {
  'activity-id': number;
  'session-id': number;
      body: SessionRequest
}

export function updateSession(http: HttpClient, rootUrl: string, params: UpdateSession$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, updateSession.PATH, 'put');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.path('session-id', params['session-id'], {});
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

updateSession.PATH = '/session/{activity-id}/{session-id}';

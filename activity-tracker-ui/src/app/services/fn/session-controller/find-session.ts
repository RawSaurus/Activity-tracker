/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SessionResponse } from '../../models/session-response';

export interface FindSession$Params {
  'session-id': number;
  'activity-id': number;
}

export function findSession(http: HttpClient, rootUrl: string, params: FindSession$Params, context?: HttpContext): Observable<StrictHttpResponse<SessionResponse>> {
  const rb = new RequestBuilder(rootUrl, findSession.PATH, 'get');
  if (params) {
    rb.path('session-id', params['session-id'], {});
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<SessionResponse>;
    })
  );
}

findSession.PATH = '/session/{activity-id}/{session-id}';

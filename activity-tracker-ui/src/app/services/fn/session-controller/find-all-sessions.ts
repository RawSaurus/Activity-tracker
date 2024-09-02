/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SessionResponse } from '../../models/session-response';

export interface FindAllSessions$Params {
  'activity-id': number;
}

export function findAllSessions(http: HttpClient, rootUrl: string, params: FindAllSessions$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<SessionResponse>>> {
  const rb = new RequestBuilder(rootUrl, findAllSessions.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<SessionResponse>>;
    })
  );
}

findAllSessions.PATH = '/session/all/{activity-id}';

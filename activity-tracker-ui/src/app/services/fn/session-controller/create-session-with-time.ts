/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SessionRequest } from '../../models/session-request';

export interface CreateSessionWithTime$Params {
  'activity-id': number;
      body: SessionRequest
}

export function createSessionWithTime(http: HttpClient, rootUrl: string, params: CreateSessionWithTime$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
  const rb = new RequestBuilder(rootUrl, createSessionWithTime.PATH, 'post');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
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

createSessionWithTime.PATH = '/session/input-time/{activity-id}';

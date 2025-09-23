/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageSessionResponse } from '../../models/page-session-response';

export interface FindAllSessions$Params {
  'activity-id': number;
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAllSessions(http: HttpClient, rootUrl: string, params: FindAllSessions$Params, context?: HttpContext): Observable<StrictHttpResponse<PageSessionResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllSessions.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
    rb.query('sort', params.sort, {});
    rb.query('sort-direction', params['sort-direction'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageSessionResponse>;
    })
  );
}

findAllSessions.PATH = '/session/all/{activity-id}';

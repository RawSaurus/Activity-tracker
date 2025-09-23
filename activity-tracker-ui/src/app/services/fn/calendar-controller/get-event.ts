/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { EntityModelEventResponse } from '../../models/entity-model-event-response';

export interface GetEvent$Params {
  'event-id': number;
}

export function getEvent(http: HttpClient, rootUrl: string, params: GetEvent$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelEventResponse>> {
  const rb = new RequestBuilder(rootUrl, getEvent.PATH, 'get');
  if (params) {
    rb.path('event-id', params['event-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<EntityModelEventResponse>;
    })
  );
}

getEvent.PATH = '/calendar/{event-id}';

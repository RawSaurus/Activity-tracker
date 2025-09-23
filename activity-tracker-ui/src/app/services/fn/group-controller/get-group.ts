/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { GroupResponse } from '../../models/group-response';

export interface GetGroup$Params {
  'group-id': number;
}

export function getGroup(http: HttpClient, rootUrl: string, params: GetGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
  const rb = new RequestBuilder(rootUrl, getGroup.PATH, 'get');
  if (params) {
    rb.path('group-id', params['group-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<GroupResponse>;
    })
  );
}

getGroup.PATH = '/group/{group-id}';

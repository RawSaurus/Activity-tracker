/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { GroupRequest } from '../../models/group-request';
import { GroupResponse } from '../../models/group-response';

export interface UpdateGroup$Params {
  'group-id': number;
      body: GroupRequest
}

export function updateGroup(http: HttpClient, rootUrl: string, params: UpdateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
  const rb = new RequestBuilder(rootUrl, updateGroup.PATH, 'put');
  if (params) {
    rb.path('group-id', params['group-id'], {});
    rb.body(params.body, 'application/json');
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

updateGroup.PATH = '/group/{group-id}';

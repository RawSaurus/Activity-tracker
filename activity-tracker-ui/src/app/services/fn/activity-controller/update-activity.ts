/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityRequest } from '../../models/activity-request';
import { ActivityResponse } from '../../models/activity-response';

export interface UpdateActivity$Params {
  'activity-id': number;
      body: ActivityRequest
}

export function updateActivity(http: HttpClient, rootUrl: string, params: UpdateActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, updateActivity.PATH, 'put');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.body(params.body, 'application/json');
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

updateActivity.PATH = '/activity/{activity-id}';

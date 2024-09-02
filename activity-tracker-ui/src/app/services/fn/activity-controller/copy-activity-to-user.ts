/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityResponse } from '../../models/activity-response';

export interface CopyActivityToUser$Params {
  'activity-id': number;
}

export function copyActivityToUser(http: HttpClient, rootUrl: string, params: CopyActivityToUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, copyActivityToUser.PATH, 'post');
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

copyActivityToUser.PATH = '/activity/copy-activity/{activity-id}';

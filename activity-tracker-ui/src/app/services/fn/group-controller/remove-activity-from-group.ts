/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { GroupResponse } from '../../models/group-response';

export interface RemoveActivityFromGroup$Params {
  'group-id': number;
  'activity-id': number;
}

export function removeActivityFromGroup(http: HttpClient, rootUrl: string, params: RemoveActivityFromGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
  const rb = new RequestBuilder(rootUrl, removeActivityFromGroup.PATH, 'put');
  if (params) {
    rb.path('group-id', params['group-id'], {});
    rb.path('activity-id', params['activity-id'], {});
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

removeActivityFromGroup.PATH = '/group/remove-from-group/{group-id}/{activity-id}';

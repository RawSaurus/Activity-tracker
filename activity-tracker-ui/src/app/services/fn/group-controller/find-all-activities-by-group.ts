/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityResponse } from '../../models/activity-response';

export interface FindAllActivitiesByGroup$Params {
  'group-id': number;
}

export function findAllActivitiesByGroup(http: HttpClient, rootUrl: string, params: FindAllActivitiesByGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ActivityResponse>>> {
  const rb = new RequestBuilder(rootUrl, findAllActivitiesByGroup.PATH, 'get');
  if (params) {
    rb.path('group-id', params['group-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<ActivityResponse>>;
    })
  );
}

findAllActivitiesByGroup.PATH = '/group/{group-id}/activities';

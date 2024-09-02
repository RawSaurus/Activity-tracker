/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityResponse } from '../../models/activity-response';

export interface FindAllByCategory$Params {
  'activity-category': string;
}

export function findAllByCategory(http: HttpClient, rootUrl: string, params: FindAllByCategory$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ActivityResponse>>> {
  const rb = new RequestBuilder(rootUrl, findAllByCategory.PATH, 'get');
  if (params) {
    rb.path('activity-category', params['activity-category'], {});
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

findAllByCategory.PATH = '/activity/category/{activity-category}';

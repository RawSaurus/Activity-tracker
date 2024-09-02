/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ActivityResponse } from '../../models/activity-response';

export interface FindInUserLibraryByName$Params {
  name: string;
}

export function findInUserLibraryByName(http: HttpClient, rootUrl: string, params: FindInUserLibraryByName$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, findInUserLibraryByName.PATH, 'get');
  if (params) {
    rb.path('name', params.name, {});
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

findInUserLibraryByName.PATH = '/activity/library/name/{name}';

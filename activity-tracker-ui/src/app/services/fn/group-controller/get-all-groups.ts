/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageGroupResponse } from '../../models/page-group-response';

export interface GetAllGroups$Params {
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function getAllGroups(http: HttpClient, rootUrl: string, params?: GetAllGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<PageGroupResponse>> {
  const rb = new RequestBuilder(rootUrl, getAllGroups.PATH, 'get');
  if (params) {
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
      return r as StrictHttpResponse<PageGroupResponse>;
    })
  );
}

getAllGroups.PATH = '/group/all';

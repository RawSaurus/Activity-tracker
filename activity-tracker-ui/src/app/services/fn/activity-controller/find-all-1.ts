/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageActivityResponse } from '../../models/page-activity-response';

export interface FindAll1$Params {
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAll1(http: HttpClient, rootUrl: string, params?: FindAll1$Params, context?: HttpContext): Observable<StrictHttpResponse<PageActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, findAll1.PATH, 'get');
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
      return r as StrictHttpResponse<PageActivityResponse>;
    })
  );
}

findAll1.PATH = '/activity/all';

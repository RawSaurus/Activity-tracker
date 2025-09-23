/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageEntityModelEventResponse } from '../../models/page-entity-model-event-response';

export interface FindAll$Params {
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAll(http: HttpClient, rootUrl: string, params?: FindAll$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
  const rb = new RequestBuilder(rootUrl, findAll.PATH, 'get');
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
      return r as StrictHttpResponse<PageEntityModelEventResponse>;
    })
  );
}

findAll.PATH = '/calendar/all';

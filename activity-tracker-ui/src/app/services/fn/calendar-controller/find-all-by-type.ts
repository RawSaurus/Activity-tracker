/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageEntityModelEventResponse } from '../../models/page-entity-model-event-response';

export interface FindAllByType$Params {
  type: string;
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAllByType(http: HttpClient, rootUrl: string, params: FindAllByType$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllByType.PATH, 'get');
  if (params) {
    rb.query('type', params.type, {});
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

findAllByType.PATH = '/calendar/type';

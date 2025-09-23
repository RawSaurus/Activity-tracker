/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageEntityModelEventResponse } from '../../models/page-entity-model-event-response';

export interface FindAllInTimePeriodByType$Params {
  start: string;
  end: string;
  type: string;
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAllInTimePeriodByType(http: HttpClient, rootUrl: string, params: FindAllInTimePeriodByType$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllInTimePeriodByType.PATH, 'get');
  if (params) {
    rb.query('start', params.start, {});
    rb.query('end', params.end, {});
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

findAllInTimePeriodByType.PATH = '/calendar/type-&-time-period';

/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageEntityModelTemplateResponse } from '../../models/page-entity-model-template-response';

export interface FindAllTemplatesWithLinks$Params {
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAllTemplatesWithLinks(http: HttpClient, rootUrl: string, params?: FindAllTemplatesWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelTemplateResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllTemplatesWithLinks.PATH, 'get');
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
      return r as StrictHttpResponse<PageEntityModelTemplateResponse>;
    })
  );
}

findAllTemplatesWithLinks.PATH = '/template/links/all';

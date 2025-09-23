/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageCommentResponse } from '../../models/page-comment-response';

export interface FindByTemplateId$Params {
  'template-id': number;
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findByTemplateId(http: HttpClient, rootUrl: string, params: FindByTemplateId$Params, context?: HttpContext): Observable<StrictHttpResponse<PageCommentResponse>> {
  const rb = new RequestBuilder(rootUrl, findByTemplateId.PATH, 'get');
  if (params) {
    rb.path('template-id', params['template-id'], {});
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
      return r as StrictHttpResponse<PageCommentResponse>;
    })
  );
}

findByTemplateId.PATH = '/comment/template/{template-id}';

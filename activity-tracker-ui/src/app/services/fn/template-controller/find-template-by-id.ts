/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TemplateResponse } from '../../models/template-response';

export interface FindTemplateById$Params {
  'template-id': number;
}

export function findTemplateById(http: HttpClient, rootUrl: string, params: FindTemplateById$Params, context?: HttpContext): Observable<StrictHttpResponse<TemplateResponse>> {
  const rb = new RequestBuilder(rootUrl, findTemplateById.PATH, 'get');
  if (params) {
    rb.path('template-id', params['template-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<TemplateResponse>;
    })
  );
}

findTemplateById.PATH = '/template/{template-id}';

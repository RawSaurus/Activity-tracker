/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TemplateRequest } from '../../models/template-request';
import { TemplateResponse } from '../../models/template-response';

export interface UpdateTemplate$Params {
  'template-id': number;
      body: TemplateRequest
}

export function updateTemplate(http: HttpClient, rootUrl: string, params: UpdateTemplate$Params, context?: HttpContext): Observable<StrictHttpResponse<TemplateResponse>> {
  const rb = new RequestBuilder(rootUrl, updateTemplate.PATH, 'put');
  if (params) {
    rb.path('template-id', params['template-id'], {});
    rb.body(params.body, 'application/json');
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

updateTemplate.PATH = '/template/{template-id}';

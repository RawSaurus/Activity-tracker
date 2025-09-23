/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { EntityModelTemplateResponse } from '../../models/entity-model-template-response';

export interface FindTemplateWithLinks$Params {
  'template-id': number;
}

export function findTemplateWithLinks(http: HttpClient, rootUrl: string, params: FindTemplateWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelTemplateResponse>> {
  const rb = new RequestBuilder(rootUrl, findTemplateWithLinks.PATH, 'get');
  if (params) {
    rb.path('template-id', params['template-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<EntityModelTemplateResponse>;
    })
  );
}

findTemplateWithLinks.PATH = '/template/links/{template-id}';

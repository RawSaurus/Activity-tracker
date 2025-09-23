/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { EntityModelActivityResponse } from '../../models/entity-model-activity-response';

export interface FindByIdWithLinks$Params {
  'activity-id': number;
}

export function findByIdWithLinks(http: HttpClient, rootUrl: string, params: FindByIdWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelActivityResponse>> {
  const rb = new RequestBuilder(rootUrl, findByIdWithLinks.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<EntityModelActivityResponse>;
    })
  );
}

findByIdWithLinks.PATH = '/activity/links/{activity-id}';

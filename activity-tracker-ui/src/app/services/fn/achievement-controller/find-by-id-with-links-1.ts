/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { EntityModelAchievementResponseV2 } from '../../models/entity-model-achievement-response-v-2';

export interface FindByIdWithLinks1$Params {
  'achievement-id': number;
}

export function findByIdWithLinks1(http: HttpClient, rootUrl: string, params: FindByIdWithLinks1$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelAchievementResponseV2>> {
  const rb = new RequestBuilder(rootUrl, findByIdWithLinks1.PATH, 'get');
  if (params) {
    rb.path('achievement-id', params['achievement-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<EntityModelAchievementResponseV2>;
    })
  );
}

findByIdWithLinks1.PATH = '/achievement/links/{achievement-id}';

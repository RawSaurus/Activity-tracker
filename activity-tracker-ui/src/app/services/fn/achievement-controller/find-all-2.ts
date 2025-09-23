/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageAchievementResponseV2 } from '../../models/page-achievement-response-v-2';

export interface FindAll2$Params {
  'activity-id': number;
  page?: number;
  size?: number;
  sort?: string;
  'sort-direction'?: string;
}

export function findAll2(http: HttpClient, rootUrl: string, params: FindAll2$Params, context?: HttpContext): Observable<StrictHttpResponse<PageAchievementResponseV2>> {
  const rb = new RequestBuilder(rootUrl, findAll2.PATH, 'get');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
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
      return r as StrictHttpResponse<PageAchievementResponseV2>;
    })
  );
}

findAll2.PATH = '/achievement/all/{activity-id}';

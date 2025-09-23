/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CommentResponse } from '../../models/comment-response';

export interface FindById1$Params {
  'comment-id': number;
}

export function findById1(http: HttpClient, rootUrl: string, params: FindById1$Params, context?: HttpContext): Observable<StrictHttpResponse<CommentResponse>> {
  const rb = new RequestBuilder(rootUrl, findById1.PATH, 'get');
  if (params) {
    rb.path('comment-id', params['comment-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CommentResponse>;
    })
  );
}

findById1.PATH = '/comment/{comment-id}';

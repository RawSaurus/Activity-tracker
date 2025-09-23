/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CommentRequest } from '../../models/comment-request';
import { CommentResponse } from '../../models/comment-response';

export interface UpdateComment$Params {
  'comment-id': number;
      body: CommentRequest
}

export function updateComment(http: HttpClient, rootUrl: string, params: UpdateComment$Params, context?: HttpContext): Observable<StrictHttpResponse<CommentResponse>> {
  const rb = new RequestBuilder(rootUrl, updateComment.PATH, 'put');
  if (params) {
    rb.path('comment-id', params['comment-id'], {});
    rb.body(params.body, 'application/json');
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

updateComment.PATH = '/comment/{comment-id}';

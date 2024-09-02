/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface DeleteSession$Params {
  'activity-id': number;
  'session-id': number;
}

export function deleteSession(http: HttpClient, rootUrl: string, params: DeleteSession$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
  const rb = new RequestBuilder(rootUrl, deleteSession.PATH, 'delete');
  if (params) {
    rb.path('activity-id', params['activity-id'], {});
    rb.path('session-id', params['session-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<string>;
    })
  );
}

deleteSession.PATH = '/session/{activity-id}/{session-id}';

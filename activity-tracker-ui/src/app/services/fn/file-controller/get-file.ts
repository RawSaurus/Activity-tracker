/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { FileResponse } from '../../models/file-response';

export interface GetFile$Params {
  'file-code': string;
}

export function getFile(http: HttpClient, rootUrl: string, params: GetFile$Params, context?: HttpContext): Observable<StrictHttpResponse<FileResponse>> {
  const rb = new RequestBuilder(rootUrl, getFile.PATH, 'get');
  if (params) {
    rb.path('file-code', params['file-code'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<FileResponse>;
    })
  );
}

getFile.PATH = '/file/{file-code}';

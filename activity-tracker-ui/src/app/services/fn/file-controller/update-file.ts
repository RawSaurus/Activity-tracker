/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { File } from '../../models/file';

export interface UpdateFile$Params {
  'file-code': string;
      body?: {
'file': Blob;
}
}

export function updateFile(http: HttpClient, rootUrl: string, params: UpdateFile$Params, context?: HttpContext): Observable<StrictHttpResponse<File>> {
  const rb = new RequestBuilder(rootUrl, updateFile.PATH, 'put');
  if (params) {
    rb.path('file-code', params['file-code'], {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<File>;
    })
  );
}

updateFile.PATH = '/file/{file-code}';

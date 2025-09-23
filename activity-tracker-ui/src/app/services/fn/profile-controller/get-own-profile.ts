/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ProfileResponse } from '../../models/profile-response';

export interface GetOwnProfile$Params {
}

export function getOwnProfile(http: HttpClient, rootUrl: string, params?: GetOwnProfile$Params, context?: HttpContext): Observable<StrictHttpResponse<ProfileResponse>> {
  const rb = new RequestBuilder(rootUrl, getOwnProfile.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ProfileResponse>;
    })
  );
}

getOwnProfile.PATH = '/profile';

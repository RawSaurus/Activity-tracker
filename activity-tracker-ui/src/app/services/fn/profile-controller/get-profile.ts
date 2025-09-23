/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Profile } from '../../models/profile';

export interface GetProfile$Params {
  'profile-id': number;
}

export function getProfile(http: HttpClient, rootUrl: string, params: GetProfile$Params, context?: HttpContext): Observable<StrictHttpResponse<Profile>> {
  const rb = new RequestBuilder(rootUrl, getProfile.PATH, 'get');
  if (params) {
    rb.path('profile-id', params['profile-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Profile>;
    })
  );
}

getProfile.PATH = '/profile/{profile-id}';

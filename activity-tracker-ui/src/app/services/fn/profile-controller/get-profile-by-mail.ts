/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Profile } from '../../models/profile';

export interface GetProfileByMail$Params {
  email: string;
}

export function getProfileByMail(http: HttpClient, rootUrl: string, params: GetProfileByMail$Params, context?: HttpContext): Observable<StrictHttpResponse<Profile>> {
  const rb = new RequestBuilder(rootUrl, getProfileByMail.PATH, 'get');
  if (params) {
    rb.path('email', params.email, {});
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

getProfileByMail.PATH = '/profile/mail/{email}';

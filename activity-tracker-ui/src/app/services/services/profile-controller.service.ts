/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addImage1 } from '../fn/profile-controller/add-image-1';
import { AddImage1$Params } from '../fn/profile-controller/add-image-1';
import { createProfile } from '../fn/profile-controller/create-profile';
import { CreateProfile$Params } from '../fn/profile-controller/create-profile';
import { deleteAccount } from '../fn/profile-controller/delete-account';
import { DeleteAccount$Params } from '../fn/profile-controller/delete-account';
import { deleteOwnAccount } from '../fn/profile-controller/delete-own-account';
import { DeleteOwnAccount$Params } from '../fn/profile-controller/delete-own-account';
import { disableAccount } from '../fn/profile-controller/disable-account';
import { DisableAccount$Params } from '../fn/profile-controller/disable-account';
import { EntityModelProfileResponse } from '../models/entity-model-profile-response';
import { getOwnProfile } from '../fn/profile-controller/get-own-profile';
import { GetOwnProfile$Params } from '../fn/profile-controller/get-own-profile';
import { getProfile } from '../fn/profile-controller/get-profile';
import { GetProfile$Params } from '../fn/profile-controller/get-profile';
import { getProfileByMail } from '../fn/profile-controller/get-profile-by-mail';
import { GetProfileByMail$Params } from '../fn/profile-controller/get-profile-by-mail';
import { getProfileWithLinks } from '../fn/profile-controller/get-profile-with-links';
import { GetProfileWithLinks$Params } from '../fn/profile-controller/get-profile-with-links';
import { Profile } from '../models/profile';
import { ProfileResponse } from '../models/profile-response';

@Injectable({ providedIn: 'root' })
export class ProfileControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `disableAccount()` */
  static readonly DisableAccountPath = '/profile/disable/{profile-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `disableAccount()` instead.
   *
   * This method doesn't expect any request body.
   */
  disableAccount$Response(params: DisableAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return disableAccount(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `disableAccount$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  disableAccount(params: DisableAccount$Params, context?: HttpContext): Observable<number> {
    return this.disableAccount$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getOwnProfile()` */
  static readonly GetOwnProfilePath = '/profile';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getOwnProfile()` instead.
   *
   * This method doesn't expect any request body.
   */
  getOwnProfile$Response(params?: GetOwnProfile$Params, context?: HttpContext): Observable<StrictHttpResponse<ProfileResponse>> {
    return getOwnProfile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getOwnProfile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getOwnProfile(params?: GetOwnProfile$Params, context?: HttpContext): Observable<ProfileResponse> {
    return this.getOwnProfile$Response(params, context).pipe(
      map((r: StrictHttpResponse<ProfileResponse>): ProfileResponse => r.body)
    );
  }

  /** Path part for operation `createProfile()` */
  static readonly CreateProfilePath = '/profile';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createProfile()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createProfile$Response(params: CreateProfile$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createProfile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createProfile$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createProfile(params: CreateProfile$Params, context?: HttpContext): Observable<number> {
    return this.createProfile$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `addImage1()` */
  static readonly AddImage1Path = '/profile';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addImage1()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage1$Response(params?: AddImage1$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return addImage1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addImage1$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage1(params?: AddImage1$Params, context?: HttpContext): Observable<{
}> {
    return this.addImage1$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `getProfile()` */
  static readonly GetProfilePath = '/profile/{profile-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getProfile()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfile$Response(params: GetProfile$Params, context?: HttpContext): Observable<StrictHttpResponse<Profile>> {
    return getProfile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getProfile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfile(params: GetProfile$Params, context?: HttpContext): Observable<Profile> {
    return this.getProfile$Response(params, context).pipe(
      map((r: StrictHttpResponse<Profile>): Profile => r.body)
    );
  }

  /** Path part for operation `deleteAccount()` */
  static readonly DeleteAccountPath = '/profile/{profile-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteAccount()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAccount$Response(params: DeleteAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteAccount(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteAccount$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAccount(params: DeleteAccount$Params, context?: HttpContext): Observable<string> {
    return this.deleteAccount$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `getProfileByMail()` */
  static readonly GetProfileByMailPath = '/profile/mail/{email}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getProfileByMail()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfileByMail$Response(params: GetProfileByMail$Params, context?: HttpContext): Observable<StrictHttpResponse<Profile>> {
    return getProfileByMail(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getProfileByMail$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfileByMail(params: GetProfileByMail$Params, context?: HttpContext): Observable<Profile> {
    return this.getProfileByMail$Response(params, context).pipe(
      map((r: StrictHttpResponse<Profile>): Profile => r.body)
    );
  }

  /** Path part for operation `getProfileWithLinks()` */
  static readonly GetProfileWithLinksPath = '/profile/links/{profile-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getProfileWithLinks()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfileWithLinks$Response(params: GetProfileWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelProfileResponse>> {
    return getProfileWithLinks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getProfileWithLinks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getProfileWithLinks(params: GetProfileWithLinks$Params, context?: HttpContext): Observable<EntityModelProfileResponse> {
    return this.getProfileWithLinks$Response(params, context).pipe(
      map((r: StrictHttpResponse<EntityModelProfileResponse>): EntityModelProfileResponse => r.body)
    );
  }

  /** Path part for operation `deleteOwnAccount()` */
  static readonly DeleteOwnAccountPath = '/profile/delete';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteOwnAccount()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteOwnAccount$Response(params?: DeleteOwnAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteOwnAccount(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteOwnAccount$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteOwnAccount(params?: DeleteOwnAccount$Params, context?: HttpContext): Observable<string> {
    return this.deleteOwnAccount$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

}

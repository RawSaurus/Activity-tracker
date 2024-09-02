/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { AchievementResponse } from '../models/achievement-response';
import { createAchievement } from '../fn/achievement-controller/create-achievement';
import { CreateAchievement$Params } from '../fn/achievement-controller/create-achievement';
import { deleteAchievement } from '../fn/achievement-controller/delete-achievement';
import { DeleteAchievement$Params } from '../fn/achievement-controller/delete-achievement';
import { findAllById } from '../fn/achievement-controller/find-all-by-id';
import { FindAllById$Params } from '../fn/achievement-controller/find-all-by-id';
import { findById2 } from '../fn/achievement-controller/find-by-id-2';
import { FindById2$Params } from '../fn/achievement-controller/find-by-id-2';
import { findPublicById } from '../fn/achievement-controller/find-public-by-id';
import { FindPublicById$Params } from '../fn/achievement-controller/find-public-by-id';
import { updateAchievement } from '../fn/achievement-controller/update-achievement';
import { UpdateAchievement$Params } from '../fn/achievement-controller/update-achievement';

@Injectable({ providedIn: 'root' })
export class AchievementControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById2()` */
  static readonly FindById2Path = '/achievement/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById2()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2$Response(params: FindById2$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponse>> {
    return findById2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById2$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2(params: FindById2$Params, context?: HttpContext): Observable<AchievementResponse> {
    return this.findById2$Response(params, context).pipe(
      map((r: StrictHttpResponse<AchievementResponse>): AchievementResponse => r.body)
    );
  }

  /** Path part for operation `updateAchievement()` */
  static readonly UpdateAchievementPath = '/achievement/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateAchievement()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAchievement$Response(params: UpdateAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateAchievement$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAchievement(params: UpdateAchievement$Params, context?: HttpContext): Observable<number> {
    return this.updateAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `deleteAchievement()` */
  static readonly DeleteAchievementPath = '/achievement/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteAchievement()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAchievement$Response(params: DeleteAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteAchievement$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAchievement(params: DeleteAchievement$Params, context?: HttpContext): Observable<string> {
    return this.deleteAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `findAllById()` */
  static readonly FindAllByIdPath = '/achievement/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllById$Response(params: FindAllById$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AchievementResponse>>> {
    return findAllById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllById(params: FindAllById$Params, context?: HttpContext): Observable<Array<AchievementResponse>> {
    return this.findAllById$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<AchievementResponse>>): Array<AchievementResponse> => r.body)
    );
  }

  /** Path part for operation `createAchievement()` */
  static readonly CreateAchievementPath = '/achievement/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createAchievement()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAchievement$Response(params: CreateAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createAchievement$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAchievement(params: CreateAchievement$Params, context?: HttpContext): Observable<number> {
    return this.createAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findPublicById()` */
  static readonly FindPublicByIdPath = '/achievement/market/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findPublicById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findPublicById$Response(params: FindPublicById$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponse>> {
    return findPublicById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findPublicById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findPublicById(params: FindPublicById$Params, context?: HttpContext): Observable<AchievementResponse> {
    return this.findPublicById$Response(params, context).pipe(
      map((r: StrictHttpResponse<AchievementResponse>): AchievementResponse => r.body)
    );
  }

}

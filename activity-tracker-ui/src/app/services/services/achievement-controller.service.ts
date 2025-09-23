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
import { AchievementResponseV2 } from '../models/achievement-response-v-2';
import { addImage3 } from '../fn/achievement-controller/add-image-3';
import { AddImage3$Params } from '../fn/achievement-controller/add-image-3';
import { createAmountAchievement } from '../fn/achievement-controller/create-amount-achievement';
import { CreateAmountAchievement$Params } from '../fn/achievement-controller/create-amount-achievement';
import { createDailyAchievement } from '../fn/achievement-controller/create-daily-achievement';
import { CreateDailyAchievement$Params } from '../fn/achievement-controller/create-daily-achievement';
import { createGoalAchievement } from '../fn/achievement-controller/create-goal-achievement';
import { CreateGoalAchievement$Params } from '../fn/achievement-controller/create-goal-achievement';
import { deleteAchievement } from '../fn/achievement-controller/delete-achievement';
import { DeleteAchievement$Params } from '../fn/achievement-controller/delete-achievement';
import { EntityModelAchievementResponseV2 } from '../models/entity-model-achievement-response-v-2';
import { findAll2 } from '../fn/achievement-controller/find-all-2';
import { FindAll2$Params } from '../fn/achievement-controller/find-all-2';
import { findById3 } from '../fn/achievement-controller/find-by-id-3';
import { FindById3$Params } from '../fn/achievement-controller/find-by-id-3';
import { findById4 } from '../fn/achievement-controller/find-by-id-4';
import { FindById4$Params } from '../fn/achievement-controller/find-by-id-4';
import { findByIdWithLinks1 } from '../fn/achievement-controller/find-by-id-with-links-1';
import { FindByIdWithLinks1$Params } from '../fn/achievement-controller/find-by-id-with-links-1';
import { getAllFromActivity } from '../fn/achievement-controller/get-all-from-activity';
import { GetAllFromActivity$Params } from '../fn/achievement-controller/get-all-from-activity';
import { getFromActivity } from '../fn/achievement-controller/get-from-activity';
import { GetFromActivity$Params } from '../fn/achievement-controller/get-from-activity';
import { markFinished } from '../fn/achievement-controller/mark-finished';
import { MarkFinished$Params } from '../fn/achievement-controller/mark-finished';
import { PageAchievementResponseV2 } from '../models/page-achievement-response-v-2';
import { updateAchievement } from '../fn/achievement-controller/update-achievement';
import { UpdateAchievement$Params } from '../fn/achievement-controller/update-achievement';

@Injectable({ providedIn: 'root' })
export class AchievementControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById3()` */
  static readonly FindById3Path = '/achievement/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById3()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById3$Response(params: FindById3$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponse>> {
    return findById3(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById3$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById3(params: FindById3$Params, context?: HttpContext): Observable<AchievementResponse> {
    return this.findById3$Response(params, context).pipe(
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

  /** Path part for operation `createGoalAchievement()` */
  static readonly CreateGoalAchievementPath = '/achievement/goal-achievement/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createGoalAchievement()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGoalAchievement$Response(params: CreateGoalAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createGoalAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createGoalAchievement$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGoalAchievement(params: CreateGoalAchievement$Params, context?: HttpContext): Observable<number> {
    return this.createGoalAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `createDailyAchievement()` */
  static readonly CreateDailyAchievementPath = '/achievement/daily-achievement/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createDailyAchievement()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createDailyAchievement$Response(params: CreateDailyAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createDailyAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createDailyAchievement$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createDailyAchievement(params: CreateDailyAchievement$Params, context?: HttpContext): Observable<number> {
    return this.createDailyAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `createAmountAchievement()` */
  static readonly CreateAmountAchievementPath = '/achievement/amount-achievement/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createAmountAchievement()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAmountAchievement$Response(params: CreateAmountAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createAmountAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createAmountAchievement$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createAmountAchievement(params: CreateAmountAchievement$Params, context?: HttpContext): Observable<number> {
    return this.createAmountAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `markFinished()` */
  static readonly MarkFinishedPath = '/achievement/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `markFinished()` instead.
   *
   * This method doesn't expect any request body.
   */
  markFinished$Response(params: MarkFinished$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return markFinished(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `markFinished$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  markFinished(params: MarkFinished$Params, context?: HttpContext): Observable<{
}> {
    return this.markFinished$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `addImage3()` */
  static readonly AddImage3Path = '/achievement/image/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addImage3()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage3$Response(params: AddImage3$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return addImage3(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addImage3$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage3(params: AddImage3$Params, context?: HttpContext): Observable<{
}> {
    return this.addImage3$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `findByIdWithLinks1()` */
  static readonly FindByIdWithLinks1Path = '/achievement/links/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findByIdWithLinks1()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByIdWithLinks1$Response(params: FindByIdWithLinks1$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelAchievementResponseV2>> {
    return findByIdWithLinks1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findByIdWithLinks1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByIdWithLinks1(params: FindByIdWithLinks1$Params, context?: HttpContext): Observable<EntityModelAchievementResponseV2> {
    return this.findByIdWithLinks1$Response(params, context).pipe(
      map((r: StrictHttpResponse<EntityModelAchievementResponseV2>): EntityModelAchievementResponseV2 => r.body)
    );
  }

  /** Path part for operation `getFromActivity()` */
  static readonly GetFromActivityPath = '/achievement/get-from-activity/{activity-id}/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFromActivity()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFromActivity$Response(params: GetFromActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponseV2>> {
    return getFromActivity(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFromActivity$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFromActivity(params: GetFromActivity$Params, context?: HttpContext): Observable<AchievementResponseV2> {
    return this.getFromActivity$Response(params, context).pipe(
      map((r: StrictHttpResponse<AchievementResponseV2>): AchievementResponseV2 => r.body)
    );
  }

  /** Path part for operation `getAllFromActivity()` */
  static readonly GetAllFromActivityPath = '/achievement/get-all-from-activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllFromActivity()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFromActivity$Response(params: GetAllFromActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<AchievementResponseV2>>> {
    return getAllFromActivity(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllFromActivity$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFromActivity(params: GetAllFromActivity$Params, context?: HttpContext): Observable<Array<AchievementResponseV2>> {
    return this.getAllFromActivity$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<AchievementResponseV2>>): Array<AchievementResponseV2> => r.body)
    );
  }

  /** Path part for operation `findById4()` */
  static readonly FindById4Path = '/achievement/find-one/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById4()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById4$Response(params: FindById4$Params, context?: HttpContext): Observable<StrictHttpResponse<AchievementResponseV2>> {
    return findById4(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById4$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById4(params: FindById4$Params, context?: HttpContext): Observable<AchievementResponseV2> {
    return this.findById4$Response(params, context).pipe(
      map((r: StrictHttpResponse<AchievementResponseV2>): AchievementResponseV2 => r.body)
    );
  }

  /** Path part for operation `findAll2()` */
  static readonly FindAll2Path = '/achievement/all/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAll2()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll2$Response(params: FindAll2$Params, context?: HttpContext): Observable<StrictHttpResponse<PageAchievementResponseV2>> {
    return findAll2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAll2$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll2(params: FindAll2$Params, context?: HttpContext): Observable<PageAchievementResponseV2> {
    return this.findAll2$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageAchievementResponseV2>): PageAchievementResponseV2 => r.body)
    );
  }

  /** Path part for operation `deleteAchievement()` */
  static readonly DeleteAchievementPath = '/achievement/delete-achievement/{achievement-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteAchievement()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAchievement$Response(params: DeleteAchievement$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteAchievement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteAchievement$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAchievement(params: DeleteAchievement$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteAchievement$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

}

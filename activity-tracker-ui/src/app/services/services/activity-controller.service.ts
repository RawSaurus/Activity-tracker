/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ActivityResponse } from '../models/activity-response';
import { addToGroup } from '../fn/activity-controller/add-to-group';
import { AddToGroup$Params } from '../fn/activity-controller/add-to-group';
import { copyActivityToUser } from '../fn/activity-controller/copy-activity-to-user';
import { CopyActivityToUser$Params } from '../fn/activity-controller/copy-activity-to-user';
import { createActivity } from '../fn/activity-controller/create-activity';
import { CreateActivity$Params } from '../fn/activity-controller/create-activity';
import { createGroup } from '../fn/activity-controller/create-group';
import { CreateGroup$Params } from '../fn/activity-controller/create-group';
import { deleteOriginalActivityById } from '../fn/activity-controller/delete-original-activity-by-id';
import { DeleteOriginalActivityById$Params } from '../fn/activity-controller/delete-original-activity-by-id';
import { findAllByCategory } from '../fn/activity-controller/find-all-by-category';
import { FindAllByCategory$Params } from '../fn/activity-controller/find-all-by-category';
import { findById1 } from '../fn/activity-controller/find-by-id-1';
import { FindById1$Params } from '../fn/activity-controller/find-by-id-1';
import { findInMarket } from '../fn/activity-controller/find-in-market';
import { FindInMarket$Params } from '../fn/activity-controller/find-in-market';
import { findInMarketByName } from '../fn/activity-controller/find-in-market-by-name';
import { FindInMarketByName$Params } from '../fn/activity-controller/find-in-market-by-name';
import { findInUserLibrary } from '../fn/activity-controller/find-in-user-library';
import { FindInUserLibrary$Params } from '../fn/activity-controller/find-in-user-library';
import { findInUserLibraryByName } from '../fn/activity-controller/find-in-user-library-by-name';
import { FindInUserLibraryByName$Params } from '../fn/activity-controller/find-in-user-library-by-name';
import { removeFromUserLibrary } from '../fn/activity-controller/remove-from-user-library';
import { RemoveFromUserLibrary$Params } from '../fn/activity-controller/remove-from-user-library';
import { updateActivity } from '../fn/activity-controller/update-activity';
import { UpdateActivity$Params } from '../fn/activity-controller/update-activity';

@Injectable({ providedIn: 'root' })
export class ActivityControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById1()` */
  static readonly FindById1Path = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById1()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById1$Response(params: FindById1$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return findById1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById1(params: FindById1$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.findById1$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `updateActivity()` */
  static readonly UpdateActivityPath = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateActivity()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateActivity$Response(params: UpdateActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return updateActivity(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateActivity$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateActivity(params: UpdateActivity$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.updateActivity$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `deleteOriginalActivityById()` */
  static readonly DeleteOriginalActivityByIdPath = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteOriginalActivityById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteOriginalActivityById$Response(params: DeleteOriginalActivityById$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteOriginalActivityById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteOriginalActivityById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteOriginalActivityById(params: DeleteOriginalActivityById$Params, context?: HttpContext): Observable<string> {
    return this.deleteOriginalActivityById$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `createGroup()` */
  static readonly CreateGroupPath = '/activity/create-group';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGroup$Response(params: CreateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return createGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGroup(params: CreateGroup$Params, context?: HttpContext): Observable<string> {
    return this.createGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `addToGroup()` */
  static readonly AddToGroupPath = '/activity/add-to-group/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addToGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addToGroup$Response(params: AddToGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return addToGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addToGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addToGroup(params: AddToGroup$Params, context?: HttpContext): Observable<string> {
    return this.addToGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `createActivity()` */
  static readonly CreateActivityPath = '/activity';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createActivity()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createActivity$Response(params: CreateActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createActivity(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createActivity$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createActivity(params: CreateActivity$Params, context?: HttpContext): Observable<number> {
    return this.createActivity$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `copyActivityToUser()` */
  static readonly CopyActivityToUserPath = '/activity/copy-activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `copyActivityToUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  copyActivityToUser$Response(params: CopyActivityToUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return copyActivityToUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `copyActivityToUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  copyActivityToUser(params: CopyActivityToUser$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.copyActivityToUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `findInMarket()` */
  static readonly FindInMarketPath = '/activity/market/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findInMarket()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInMarket$Response(params: FindInMarket$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return findInMarket(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findInMarket$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInMarket(params: FindInMarket$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.findInMarket$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `findInMarketByName()` */
  static readonly FindInMarketByNamePath = '/activity/market/name/{name}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findInMarketByName()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInMarketByName$Response(params: FindInMarketByName$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ActivityResponse>>> {
    return findInMarketByName(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findInMarketByName$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInMarketByName(params: FindInMarketByName$Params, context?: HttpContext): Observable<Array<ActivityResponse>> {
    return this.findInMarketByName$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ActivityResponse>>): Array<ActivityResponse> => r.body)
    );
  }

  /** Path part for operation `findInUserLibrary()` */
  static readonly FindInUserLibraryPath = '/activity/library/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findInUserLibrary()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInUserLibrary$Response(params: FindInUserLibrary$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return findInUserLibrary(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findInUserLibrary$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInUserLibrary(params: FindInUserLibrary$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.findInUserLibrary$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `findInUserLibraryByName()` */
  static readonly FindInUserLibraryByNamePath = '/activity/library/name/{name}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findInUserLibraryByName()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInUserLibraryByName$Response(params: FindInUserLibraryByName$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return findInUserLibraryByName(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findInUserLibraryByName$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findInUserLibraryByName(params: FindInUserLibraryByName$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.findInUserLibraryByName$Response(params, context).pipe(
      map((r: StrictHttpResponse<ActivityResponse>): ActivityResponse => r.body)
    );
  }

  /** Path part for operation `findAllByCategory()` */
  static readonly FindAllByCategoryPath = '/activity/category/{activity-category}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllByCategory()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllByCategory$Response(params: FindAllByCategory$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ActivityResponse>>> {
    return findAllByCategory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllByCategory$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllByCategory(params: FindAllByCategory$Params, context?: HttpContext): Observable<Array<ActivityResponse>> {
    return this.findAllByCategory$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ActivityResponse>>): Array<ActivityResponse> => r.body)
    );
  }

  /** Path part for operation `removeFromUserLibrary()` */
  static readonly RemoveFromUserLibraryPath = '/activity/remove/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeFromUserLibrary()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeFromUserLibrary$Response(params: RemoveFromUserLibrary$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return removeFromUserLibrary(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `removeFromUserLibrary$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeFromUserLibrary(params: RemoveFromUserLibrary$Params, context?: HttpContext): Observable<string> {
    return this.removeFromUserLibrary$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

}

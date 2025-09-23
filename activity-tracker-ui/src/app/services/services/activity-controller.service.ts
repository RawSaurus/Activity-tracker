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
import { addImage2 } from '../fn/activity-controller/add-image-2';
import { AddImage2$Params } from '../fn/activity-controller/add-image-2';
import { createActivity } from '../fn/activity-controller/create-activity';
import { CreateActivity$Params } from '../fn/activity-controller/create-activity';
import { deleteActivityById } from '../fn/activity-controller/delete-activity-by-id';
import { DeleteActivityById$Params } from '../fn/activity-controller/delete-activity-by-id';
import { EntityModelActivityResponse } from '../models/entity-model-activity-response';
import { findAll1 } from '../fn/activity-controller/find-all-1';
import { FindAll1$Params } from '../fn/activity-controller/find-all-1';
import { findAllWithLinks } from '../fn/activity-controller/find-all-with-links';
import { FindAllWithLinks$Params } from '../fn/activity-controller/find-all-with-links';
import { findById2 } from '../fn/activity-controller/find-by-id-2';
import { FindById2$Params } from '../fn/activity-controller/find-by-id-2';
import { findByIdWithLinks } from '../fn/activity-controller/find-by-id-with-links';
import { FindByIdWithLinks$Params } from '../fn/activity-controller/find-by-id-with-links';
import { findInUserLibrary } from '../fn/activity-controller/find-in-user-library';
import { FindInUserLibrary$Params } from '../fn/activity-controller/find-in-user-library';
import { findInUserLibraryByName } from '../fn/activity-controller/find-in-user-library-by-name';
import { FindInUserLibraryByName$Params } from '../fn/activity-controller/find-in-user-library-by-name';
import { PageActivityResponse } from '../models/page-activity-response';
import { PageEntityModelActivityResponse } from '../models/page-entity-model-activity-response';
import { updateActivity } from '../fn/activity-controller/update-activity';
import { UpdateActivity$Params } from '../fn/activity-controller/update-activity';

@Injectable({ providedIn: 'root' })
export class ActivityControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById2()` */
  static readonly FindById2Path = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById2()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2$Response(params: FindById2$Params, context?: HttpContext): Observable<StrictHttpResponse<ActivityResponse>> {
    return findById2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById2$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById2(params: FindById2$Params, context?: HttpContext): Observable<ActivityResponse> {
    return this.findById2$Response(params, context).pipe(
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

  /** Path part for operation `deleteActivityById()` */
  static readonly DeleteActivityByIdPath = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteActivityById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteActivityById$Response(params: DeleteActivityById$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteActivityById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteActivityById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteActivityById(params: DeleteActivityById$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteActivityById$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `addImage2()` */
  static readonly AddImage2Path = '/activity/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addImage2()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage2$Response(params: AddImage2$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return addImage2(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addImage2$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage2(params: AddImage2$Params, context?: HttpContext): Observable<{
}> {
    return this.addImage2$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
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

  /** Path part for operation `findInUserLibraryByName()` */
  static readonly FindInUserLibraryByNamePath = '/activity/name/{name}';

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

  /** Path part for operation `findByIdWithLinks()` */
  static readonly FindByIdWithLinksPath = '/activity/links/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findByIdWithLinks()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByIdWithLinks$Response(params: FindByIdWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelActivityResponse>> {
    return findByIdWithLinks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findByIdWithLinks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByIdWithLinks(params: FindByIdWithLinks$Params, context?: HttpContext): Observable<EntityModelActivityResponse> {
    return this.findByIdWithLinks$Response(params, context).pipe(
      map((r: StrictHttpResponse<EntityModelActivityResponse>): EntityModelActivityResponse => r.body)
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

  /** Path part for operation `findAll1()` */
  static readonly FindAll1Path = '/activity/all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAll1()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll1$Response(params?: FindAll1$Params, context?: HttpContext): Observable<StrictHttpResponse<PageActivityResponse>> {
    return findAll1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAll1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll1(params?: FindAll1$Params, context?: HttpContext): Observable<PageActivityResponse> {
    return this.findAll1$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageActivityResponse>): PageActivityResponse => r.body)
    );
  }

  /** Path part for operation `findAllWithLinks()` */
  static readonly FindAllWithLinksPath = '/activity/all/links';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllWithLinks()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllWithLinks$Response(params?: FindAllWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelActivityResponse>> {
    return findAllWithLinks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllWithLinks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllWithLinks(params?: FindAllWithLinks$Params, context?: HttpContext): Observable<PageEntityModelActivityResponse> {
    return this.findAllWithLinks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelActivityResponse>): PageEntityModelActivityResponse => r.body)
    );
  }

}

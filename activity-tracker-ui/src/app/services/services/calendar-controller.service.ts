/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createPlan } from '../fn/calendar-controller/create-plan';
import { CreatePlan$Params } from '../fn/calendar-controller/create-plan';
import { deleteEvent } from '../fn/calendar-controller/delete-event';
import { DeleteEvent$Params } from '../fn/calendar-controller/delete-event';
import { EntityModelEventResponse } from '../models/entity-model-event-response';
import { findAll } from '../fn/calendar-controller/find-all';
import { FindAll$Params } from '../fn/calendar-controller/find-all';
import { findAllByType } from '../fn/calendar-controller/find-all-by-type';
import { FindAllByType$Params } from '../fn/calendar-controller/find-all-by-type';
import { findAllInTimePeriod } from '../fn/calendar-controller/find-all-in-time-period';
import { FindAllInTimePeriod$Params } from '../fn/calendar-controller/find-all-in-time-period';
import { findAllInTimePeriodByType } from '../fn/calendar-controller/find-all-in-time-period-by-type';
import { FindAllInTimePeriodByType$Params } from '../fn/calendar-controller/find-all-in-time-period-by-type';
import { getEvent } from '../fn/calendar-controller/get-event';
import { GetEvent$Params } from '../fn/calendar-controller/get-event';
import { PageEntityModelEventResponse } from '../models/page-entity-model-event-response';
import { updateEvent } from '../fn/calendar-controller/update-event';
import { UpdateEvent$Params } from '../fn/calendar-controller/update-event';

@Injectable({ providedIn: 'root' })
export class CalendarControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getEvent()` */
  static readonly GetEventPath = '/calendar/{event-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getEvent()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEvent$Response(params: GetEvent$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelEventResponse>> {
    return getEvent(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getEvent$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEvent(params: GetEvent$Params, context?: HttpContext): Observable<EntityModelEventResponse> {
    return this.getEvent$Response(params, context).pipe(
      map((r: StrictHttpResponse<EntityModelEventResponse>): EntityModelEventResponse => r.body)
    );
  }

  /** Path part for operation `updateEvent()` */
  static readonly UpdateEventPath = '/calendar/{event-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateEvent()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateEvent$Response(params: UpdateEvent$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateEvent(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateEvent$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateEvent(params: UpdateEvent$Params, context?: HttpContext): Observable<number> {
    return this.updateEvent$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `deleteEvent()` */
  static readonly DeleteEventPath = '/calendar/{event-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteEvent()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteEvent$Response(params: DeleteEvent$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteEvent(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteEvent$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteEvent(params: DeleteEvent$Params, context?: HttpContext): Observable<string> {
    return this.deleteEvent$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `createPlan()` */
  static readonly CreatePlanPath = '/calendar';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createPlan()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createPlan$Response(params: CreatePlan$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createPlan(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createPlan$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createPlan(params: CreatePlan$Params, context?: HttpContext): Observable<number> {
    return this.createPlan$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllByType()` */
  static readonly FindAllByTypePath = '/calendar/type';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllByType()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllByType$Response(params: FindAllByType$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
    return findAllByType(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllByType$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllByType(params: FindAllByType$Params, context?: HttpContext): Observable<PageEntityModelEventResponse> {
    return this.findAllByType$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelEventResponse>): PageEntityModelEventResponse => r.body)
    );
  }

  /** Path part for operation `findAllInTimePeriodByType()` */
  static readonly FindAllInTimePeriodByTypePath = '/calendar/type-&-time-period';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllInTimePeriodByType()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllInTimePeriodByType$Response(params: FindAllInTimePeriodByType$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
    return findAllInTimePeriodByType(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllInTimePeriodByType$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllInTimePeriodByType(params: FindAllInTimePeriodByType$Params, context?: HttpContext): Observable<PageEntityModelEventResponse> {
    return this.findAllInTimePeriodByType$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelEventResponse>): PageEntityModelEventResponse => r.body)
    );
  }

  /** Path part for operation `findAllInTimePeriod()` */
  static readonly FindAllInTimePeriodPath = '/calendar/time-period';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllInTimePeriod()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllInTimePeriod$Response(params: FindAllInTimePeriod$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
    return findAllInTimePeriod(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllInTimePeriod$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllInTimePeriod(params: FindAllInTimePeriod$Params, context?: HttpContext): Observable<PageEntityModelEventResponse> {
    return this.findAllInTimePeriod$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelEventResponse>): PageEntityModelEventResponse => r.body)
    );
  }

  /** Path part for operation `findAll()` */
  static readonly FindAllPath = '/calendar/all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAll()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll$Response(params?: FindAll$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelEventResponse>> {
    return findAll(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAll$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAll(params?: FindAll$Params, context?: HttpContext): Observable<PageEntityModelEventResponse> {
    return this.findAll$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelEventResponse>): PageEntityModelEventResponse => r.body)
    );
  }

}

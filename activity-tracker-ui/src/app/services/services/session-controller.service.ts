/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addNote } from '../fn/session-controller/add-note';
import { AddNote$Params } from '../fn/session-controller/add-note';
import { createSession } from '../fn/session-controller/create-session';
import { CreateSession$Params } from '../fn/session-controller/create-session';
import { createSessionWithTime } from '../fn/session-controller/create-session-with-time';
import { CreateSessionWithTime$Params } from '../fn/session-controller/create-session-with-time';
import { deleteSession } from '../fn/session-controller/delete-session';
import { DeleteSession$Params } from '../fn/session-controller/delete-session';
import { endSession } from '../fn/session-controller/end-session';
import { EndSession$Params } from '../fn/session-controller/end-session';
import { findAllSessions } from '../fn/session-controller/find-all-sessions';
import { FindAllSessions$Params } from '../fn/session-controller/find-all-sessions';
import { findById } from '../fn/session-controller/find-by-id';
import { FindById$Params } from '../fn/session-controller/find-by-id';
import { findSession } from '../fn/session-controller/find-session';
import { FindSession$Params } from '../fn/session-controller/find-session';
import { PageSessionResponse } from '../models/page-session-response';
import { SessionResponse } from '../models/session-response';
import { updateSession } from '../fn/session-controller/update-session';
import { UpdateSession$Params } from '../fn/session-controller/update-session';

@Injectable({ providedIn: 'root' })
export class SessionControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findSession()` */
  static readonly FindSessionPath = '/session/{activity-id}/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findSession()` instead.
   *
   * This method doesn't expect any request body.
   */
  findSession$Response(params: FindSession$Params, context?: HttpContext): Observable<StrictHttpResponse<SessionResponse>> {
    return findSession(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findSession$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findSession(params: FindSession$Params, context?: HttpContext): Observable<SessionResponse> {
    return this.findSession$Response(params, context).pipe(
      map((r: StrictHttpResponse<SessionResponse>): SessionResponse => r.body)
    );
  }

  /** Path part for operation `updateSession()` */
  static readonly UpdateSessionPath = '/session/{activity-id}/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateSession()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateSession$Response(params: UpdateSession$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateSession(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateSession$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateSession(params: UpdateSession$Params, context?: HttpContext): Observable<number> {
    return this.updateSession$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `deleteSession()` */
  static readonly DeleteSessionPath = '/session/{activity-id}/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteSession()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteSession$Response(params: DeleteSession$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteSession(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteSession$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteSession(params: DeleteSession$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteSession$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `endSession()` */
  static readonly EndSessionPath = '/session/{activity-id}/end-session/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `endSession()` instead.
   *
   * This method doesn't expect any request body.
   */
  endSession$Response(params: EndSession$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return endSession(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `endSession$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  endSession(params: EndSession$Params, context?: HttpContext): Observable<number> {
    return this.endSession$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `addNote()` */
  static readonly AddNotePath = '/session/add-note/{activity-id}/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addNote()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addNote$Response(params: AddNote$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addNote(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addNote$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addNote(params: AddNote$Params, context?: HttpContext): Observable<number> {
    return this.addNote$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `createSession()` */
  static readonly CreateSessionPath = '/session/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSession()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSession$Response(params: CreateSession$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createSession(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createSession$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSession(params: CreateSession$Params, context?: HttpContext): Observable<number> {
    return this.createSession$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `createSessionWithTime()` */
  static readonly CreateSessionWithTimePath = '/session/input-time/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSessionWithTime()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSessionWithTime$Response(params: CreateSessionWithTime$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createSessionWithTime(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createSessionWithTime$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSessionWithTime(params: CreateSessionWithTime$Params, context?: HttpContext): Observable<number> {
    return this.createSessionWithTime$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findById()` */
  static readonly FindByIdPath = '/session/{session-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById$Response(params: FindById$Params, context?: HttpContext): Observable<StrictHttpResponse<SessionResponse>> {
    return findById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById(params: FindById$Params, context?: HttpContext): Observable<SessionResponse> {
    return this.findById$Response(params, context).pipe(
      map((r: StrictHttpResponse<SessionResponse>): SessionResponse => r.body)
    );
  }

  /** Path part for operation `findAllSessions()` */
  static readonly FindAllSessionsPath = '/session/all/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllSessions()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllSessions$Response(params: FindAllSessions$Params, context?: HttpContext): Observable<StrictHttpResponse<PageSessionResponse>> {
    return findAllSessions(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllSessions$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllSessions(params: FindAllSessions$Params, context?: HttpContext): Observable<PageSessionResponse> {
    return this.findAllSessions$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageSessionResponse>): PageSessionResponse => r.body)
    );
  }

}

/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CommentResponse } from '../models/comment-response';
import { createComment } from '../fn/comment-controller/create-comment';
import { CreateComment$Params } from '../fn/comment-controller/create-comment';
import { deleteComment } from '../fn/comment-controller/delete-comment';
import { DeleteComment$Params } from '../fn/comment-controller/delete-comment';
import { findById1 } from '../fn/comment-controller/find-by-id-1';
import { FindById1$Params } from '../fn/comment-controller/find-by-id-1';
import { findByTemplateId } from '../fn/comment-controller/find-by-template-id';
import { FindByTemplateId$Params } from '../fn/comment-controller/find-by-template-id';
import { PageCommentResponse } from '../models/page-comment-response';
import { updateComment } from '../fn/comment-controller/update-comment';
import { UpdateComment$Params } from '../fn/comment-controller/update-comment';

@Injectable({ providedIn: 'root' })
export class CommentControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findById1()` */
  static readonly FindById1Path = '/comment/{comment-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findById1()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById1$Response(params: FindById1$Params, context?: HttpContext): Observable<StrictHttpResponse<CommentResponse>> {
    return findById1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findById1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findById1(params: FindById1$Params, context?: HttpContext): Observable<CommentResponse> {
    return this.findById1$Response(params, context).pipe(
      map((r: StrictHttpResponse<CommentResponse>): CommentResponse => r.body)
    );
  }

  /** Path part for operation `updateComment()` */
  static readonly UpdateCommentPath = '/comment/{comment-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateComment()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateComment$Response(params: UpdateComment$Params, context?: HttpContext): Observable<StrictHttpResponse<CommentResponse>> {
    return updateComment(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateComment$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateComment(params: UpdateComment$Params, context?: HttpContext): Observable<CommentResponse> {
    return this.updateComment$Response(params, context).pipe(
      map((r: StrictHttpResponse<CommentResponse>): CommentResponse => r.body)
    );
  }

  /** Path part for operation `deleteComment()` */
  static readonly DeleteCommentPath = '/comment/{comment-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteComment()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteComment$Response(params: DeleteComment$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteComment(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteComment$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteComment(params: DeleteComment$Params, context?: HttpContext): Observable<string> {
    return this.deleteComment$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `createComment()` */
  static readonly CreateCommentPath = '/comment/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createComment()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createComment$Response(params: CreateComment$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createComment(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createComment$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createComment(params: CreateComment$Params, context?: HttpContext): Observable<number> {
    return this.createComment$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findByTemplateId()` */
  static readonly FindByTemplateIdPath = '/comment/template/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findByTemplateId()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByTemplateId$Response(params: FindByTemplateId$Params, context?: HttpContext): Observable<StrictHttpResponse<PageCommentResponse>> {
    return findByTemplateId(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findByTemplateId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findByTemplateId(params: FindByTemplateId$Params, context?: HttpContext): Observable<PageCommentResponse> {
    return this.findByTemplateId$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageCommentResponse>): PageCommentResponse => r.body)
    );
  }

}

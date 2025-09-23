/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addImage } from '../fn/template-controller/add-image';
import { AddImage$Params } from '../fn/template-controller/add-image';
import { createNewTemplate } from '../fn/template-controller/create-new-template';
import { CreateNewTemplate$Params } from '../fn/template-controller/create-new-template';
import { deleteTemplate } from '../fn/template-controller/delete-template';
import { DeleteTemplate$Params } from '../fn/template-controller/delete-template';
import { EntityModelTemplateResponse } from '../models/entity-model-template-response';
import { findAllTemplates } from '../fn/template-controller/find-all-templates';
import { FindAllTemplates$Params } from '../fn/template-controller/find-all-templates';
import { findAllTemplatesWithLinks } from '../fn/template-controller/find-all-templates-with-links';
import { FindAllTemplatesWithLinks$Params } from '../fn/template-controller/find-all-templates-with-links';
import { findTemplateById } from '../fn/template-controller/find-template-by-id';
import { FindTemplateById$Params } from '../fn/template-controller/find-template-by-id';
import { findTemplateWithLinks } from '../fn/template-controller/find-template-with-links';
import { FindTemplateWithLinks$Params } from '../fn/template-controller/find-template-with-links';
import { PageEntityModelTemplateResponse } from '../models/page-entity-model-template-response';
import { PageTemplateResponse } from '../models/page-template-response';
import { postTemplateFromExistingActivity } from '../fn/template-controller/post-template-from-existing-activity';
import { PostTemplateFromExistingActivity$Params } from '../fn/template-controller/post-template-from-existing-activity';
import { TemplateResponse } from '../models/template-response';
import { updateTemplate } from '../fn/template-controller/update-template';
import { UpdateTemplate$Params } from '../fn/template-controller/update-template';

@Injectable({ providedIn: 'root' })
export class TemplateControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findTemplateById()` */
  static readonly FindTemplateByIdPath = '/template/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findTemplateById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findTemplateById$Response(params: FindTemplateById$Params, context?: HttpContext): Observable<StrictHttpResponse<TemplateResponse>> {
    return findTemplateById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findTemplateById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findTemplateById(params: FindTemplateById$Params, context?: HttpContext): Observable<TemplateResponse> {
    return this.findTemplateById$Response(params, context).pipe(
      map((r: StrictHttpResponse<TemplateResponse>): TemplateResponse => r.body)
    );
  }

  /** Path part for operation `updateTemplate()` */
  static readonly UpdateTemplatePath = '/template/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateTemplate()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateTemplate$Response(params: UpdateTemplate$Params, context?: HttpContext): Observable<StrictHttpResponse<TemplateResponse>> {
    return updateTemplate(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateTemplate$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateTemplate(params: UpdateTemplate$Params, context?: HttpContext): Observable<TemplateResponse> {
    return this.updateTemplate$Response(params, context).pipe(
      map((r: StrictHttpResponse<TemplateResponse>): TemplateResponse => r.body)
    );
  }

  /** Path part for operation `addImage()` */
  static readonly AddImagePath = '/template/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addImage()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage$Response(params: AddImage$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return addImage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addImage$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addImage(params: AddImage$Params, context?: HttpContext): Observable<{
}> {
    return this.addImage$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `createNewTemplate()` */
  static readonly CreateNewTemplatePath = '/template';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createNewTemplate()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewTemplate$Response(params: CreateNewTemplate$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createNewTemplate(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createNewTemplate$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createNewTemplate(params: CreateNewTemplate$Params, context?: HttpContext): Observable<number> {
    return this.createNewTemplate$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `postTemplateFromExistingActivity()` */
  static readonly PostTemplateFromExistingActivityPath = '/template/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `postTemplateFromExistingActivity()` instead.
   *
   * This method doesn't expect any request body.
   */
  postTemplateFromExistingActivity$Response(params: PostTemplateFromExistingActivity$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return postTemplateFromExistingActivity(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `postTemplateFromExistingActivity$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  postTemplateFromExistingActivity(params: PostTemplateFromExistingActivity$Params, context?: HttpContext): Observable<number> {
    return this.postTemplateFromExistingActivity$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findTemplateWithLinks()` */
  static readonly FindTemplateWithLinksPath = '/template/links/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findTemplateWithLinks()` instead.
   *
   * This method doesn't expect any request body.
   */
  findTemplateWithLinks$Response(params: FindTemplateWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<EntityModelTemplateResponse>> {
    return findTemplateWithLinks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findTemplateWithLinks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findTemplateWithLinks(params: FindTemplateWithLinks$Params, context?: HttpContext): Observable<EntityModelTemplateResponse> {
    return this.findTemplateWithLinks$Response(params, context).pipe(
      map((r: StrictHttpResponse<EntityModelTemplateResponse>): EntityModelTemplateResponse => r.body)
    );
  }

  /** Path part for operation `findAllTemplatesWithLinks()` */
  static readonly FindAllTemplatesWithLinksPath = '/template/links/all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllTemplatesWithLinks()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTemplatesWithLinks$Response(params?: FindAllTemplatesWithLinks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageEntityModelTemplateResponse>> {
    return findAllTemplatesWithLinks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllTemplatesWithLinks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTemplatesWithLinks(params?: FindAllTemplatesWithLinks$Params, context?: HttpContext): Observable<PageEntityModelTemplateResponse> {
    return this.findAllTemplatesWithLinks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageEntityModelTemplateResponse>): PageEntityModelTemplateResponse => r.body)
    );
  }

  /** Path part for operation `findAllTemplates()` */
  static readonly FindAllTemplatesPath = '/template/find-all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllTemplates()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTemplates$Response(params?: FindAllTemplates$Params, context?: HttpContext): Observable<StrictHttpResponse<PageTemplateResponse>> {
    return findAllTemplates(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllTemplates$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllTemplates(params?: FindAllTemplates$Params, context?: HttpContext): Observable<PageTemplateResponse> {
    return this.findAllTemplates$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageTemplateResponse>): PageTemplateResponse => r.body)
    );
  }

  /** Path part for operation `deleteTemplate()` */
  static readonly DeleteTemplatePath = '/template/delete/{template-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteTemplate()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteTemplate$Response(params: DeleteTemplate$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteTemplate(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteTemplate$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteTemplate(params: DeleteTemplate$Params, context?: HttpContext): Observable<string> {
    return this.deleteTemplate$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

}

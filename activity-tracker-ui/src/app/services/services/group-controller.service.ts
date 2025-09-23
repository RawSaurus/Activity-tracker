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
import { addActivityToGroup } from '../fn/group-controller/add-activity-to-group';
import { AddActivityToGroup$Params } from '../fn/group-controller/add-activity-to-group';
import { createGroup } from '../fn/group-controller/create-group';
import { CreateGroup$Params } from '../fn/group-controller/create-group';
import { deleteGroup } from '../fn/group-controller/delete-group';
import { DeleteGroup$Params } from '../fn/group-controller/delete-group';
import { findAllActivitiesByGroup } from '../fn/group-controller/find-all-activities-by-group';
import { FindAllActivitiesByGroup$Params } from '../fn/group-controller/find-all-activities-by-group';
import { getAllGroups } from '../fn/group-controller/get-all-groups';
import { GetAllGroups$Params } from '../fn/group-controller/get-all-groups';
import { getGroup } from '../fn/group-controller/get-group';
import { GetGroup$Params } from '../fn/group-controller/get-group';
import { GroupResponse } from '../models/group-response';
import { PageGroupResponse } from '../models/page-group-response';
import { removeActivityFromGroup } from '../fn/group-controller/remove-activity-from-group';
import { RemoveActivityFromGroup$Params } from '../fn/group-controller/remove-activity-from-group';
import { updateGroup } from '../fn/group-controller/update-group';
import { UpdateGroup$Params } from '../fn/group-controller/update-group';

@Injectable({ providedIn: 'root' })
export class GroupControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getGroup()` */
  static readonly GetGroupPath = '/group/{group-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroup$Response(params: GetGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
    return getGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroup(params: GetGroup$Params, context?: HttpContext): Observable<GroupResponse> {
    return this.getGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<GroupResponse>): GroupResponse => r.body)
    );
  }

  /** Path part for operation `updateGroup()` */
  static readonly UpdateGroupPath = '/group/{group-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateGroup$Response(params: UpdateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
    return updateGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateGroup(params: UpdateGroup$Params, context?: HttpContext): Observable<GroupResponse> {
    return this.updateGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<GroupResponse>): GroupResponse => r.body)
    );
  }

  /** Path part for operation `deleteGroup()` */
  static readonly DeleteGroupPath = '/group/{group-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGroup$Response(params: DeleteGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGroup(params: DeleteGroup$Params, context?: HttpContext): Observable<string> {
    return this.deleteGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `removeActivityFromGroup()` */
  static readonly RemoveActivityFromGroupPath = '/group/remove-from-group/{group-id}/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeActivityFromGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeActivityFromGroup$Response(params: RemoveActivityFromGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
    return removeActivityFromGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `removeActivityFromGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeActivityFromGroup(params: RemoveActivityFromGroup$Params, context?: HttpContext): Observable<GroupResponse> {
    return this.removeActivityFromGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<GroupResponse>): GroupResponse => r.body)
    );
  }

  /** Path part for operation `addActivityToGroup()` */
  static readonly AddActivityToGroupPath = '/group/add-to-group/{group-id}/{activity-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addActivityToGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  addActivityToGroup$Response(params: AddActivityToGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupResponse>> {
    return addActivityToGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addActivityToGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addActivityToGroup(params: AddActivityToGroup$Params, context?: HttpContext): Observable<GroupResponse> {
    return this.addActivityToGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<GroupResponse>): GroupResponse => r.body)
    );
  }

  /** Path part for operation `createGroup()` */
  static readonly CreateGroupPath = '/group/create-group';

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

  /** Path part for operation `findAllActivitiesByGroup()` */
  static readonly FindAllActivitiesByGroupPath = '/group/{group-id}/activities';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllActivitiesByGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllActivitiesByGroup$Response(params: FindAllActivitiesByGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ActivityResponse>>> {
    return findAllActivitiesByGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllActivitiesByGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllActivitiesByGroup(params: FindAllActivitiesByGroup$Params, context?: HttpContext): Observable<Array<ActivityResponse>> {
    return this.findAllActivitiesByGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ActivityResponse>>): Array<ActivityResponse> => r.body)
    );
  }

  /** Path part for operation `getAllGroups()` */
  static readonly GetAllGroupsPath = '/group/all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllGroups()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllGroups$Response(params?: GetAllGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<PageGroupResponse>> {
    return getAllGroups(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllGroups$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllGroups(params?: GetAllGroups$Params, context?: HttpContext): Observable<PageGroupResponse> {
    return this.getAllGroups$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageGroupResponse>): PageGroupResponse => r.body)
    );
  }

}

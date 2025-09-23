/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { deleteAll } from '../fn/file-controller/delete-all';
import { DeleteAll$Params } from '../fn/file-controller/delete-all';
import { deleteDir } from '../fn/file-controller/delete-dir';
import { DeleteDir$Params } from '../fn/file-controller/delete-dir';
import { deleteFile } from '../fn/file-controller/delete-file';
import { DeleteFile$Params } from '../fn/file-controller/delete-file';
import { deleteFile1 } from '../fn/file-controller/delete-file-1';
import { DeleteFile1$Params } from '../fn/file-controller/delete-file-1';
import { downloadFile } from '../fn/file-controller/download-file';
import { DownloadFile$Params } from '../fn/file-controller/download-file';
import { downloadFile1 } from '../fn/file-controller/download-file-1';
import { DownloadFile1$Params } from '../fn/file-controller/download-file-1';
import { File } from '../models/file';
import { FileResponse } from '../models/file-response';
import { getFile } from '../fn/file-controller/get-file';
import { GetFile$Params } from '../fn/file-controller/get-file';
import { getFile1 } from '../fn/file-controller/get-file-1';
import { GetFile1$Params } from '../fn/file-controller/get-file-1';
import { updateFile } from '../fn/file-controller/update-file';
import { UpdateFile$Params } from '../fn/file-controller/update-file';
import { uploadFile } from '../fn/file-controller/upload-file';
import { UploadFile$Params } from '../fn/file-controller/upload-file';

@Injectable({ providedIn: 'root' })
export class FileControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getFile()` */
  static readonly GetFilePath = '/file/{file-code}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFile()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFile$Response(params: GetFile$Params, context?: HttpContext): Observable<StrictHttpResponse<FileResponse>> {
    return getFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFile(params: GetFile$Params, context?: HttpContext): Observable<FileResponse> {
    return this.getFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<FileResponse>): FileResponse => r.body)
    );
  }

  /** Path part for operation `updateFile()` */
  static readonly UpdateFilePath = '/file/{file-code}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateFile()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateFile$Response(params: UpdateFile$Params, context?: HttpContext): Observable<StrictHttpResponse<File>> {
    return updateFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateFile$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateFile(params: UpdateFile$Params, context?: HttpContext): Observable<File> {
    return this.updateFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<File>): File => r.body)
    );
  }

  /** Path part for operation `deleteFile()` */
  static readonly DeleteFilePath = '/file/{file-code}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteFile()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFile$Response(params: DeleteFile$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteFile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFile(params: DeleteFile$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `uploadFile()` */
  static readonly UploadFilePath = '/file';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadFile()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  uploadFile$Response(params?: UploadFile$Params, context?: HttpContext): Observable<StrictHttpResponse<File>> {
    return uploadFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadFile$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  uploadFile(params?: UploadFile$Params, context?: HttpContext): Observable<File> {
    return this.uploadFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<File>): File => r.body)
    );
  }

  /** Path part for operation `getFile1()` */
  static readonly GetFile1Path = '/file/id/{file-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFile1()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFile1$Response(params: GetFile1$Params, context?: HttpContext): Observable<StrictHttpResponse<FileResponse>> {
    return getFile1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFile1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFile1(params: GetFile1$Params, context?: HttpContext): Observable<FileResponse> {
    return this.getFile1$Response(params, context).pipe(
      map((r: StrictHttpResponse<FileResponse>): FileResponse => r.body)
    );
  }

  /** Path part for operation `deleteFile1()` */
  static readonly DeleteFile1Path = '/file/id/{file-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteFile1()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFile1$Response(params: DeleteFile1$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteFile1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteFile1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFile1(params: DeleteFile1$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteFile1$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `downloadFile()` */
  static readonly DownloadFilePath = '/file/download/{file-code}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `downloadFile()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile$Response(params: DownloadFile$Params, context?: HttpContext): Observable<StrictHttpResponse<Blob>> {
    return downloadFile(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `downloadFile$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile(params: DownloadFile$Params, context?: HttpContext): Observable<Blob> {
    return this.downloadFile$Response(params, context).pipe(
      map((r: StrictHttpResponse<Blob>): Blob => r.body)
    );
  }

  /** Path part for operation `downloadFile1()` */
  static readonly DownloadFile1Path = '/file/download/id/{file-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `downloadFile1()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile1$Response(params: DownloadFile1$Params, context?: HttpContext): Observable<StrictHttpResponse<Blob>> {
    return downloadFile1(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `downloadFile1$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadFile1(params: DownloadFile1$Params, context?: HttpContext): Observable<Blob> {
    return this.downloadFile1$Response(params, context).pipe(
      map((r: StrictHttpResponse<Blob>): Blob => r.body)
    );
  }

  /** Path part for operation `deleteDir()` */
  static readonly DeleteDirPath = '/file/delete-dir';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteDir()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDir$Response(params?: DeleteDir$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteDir(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteDir$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDir(params?: DeleteDir$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteDir$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `deleteAll()` */
  static readonly DeleteAllPath = '/file/delete-all';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteAll()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAll$Response(params?: DeleteAll$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteAll(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteAll$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteAll(params?: DeleteAll$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteAll$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

}

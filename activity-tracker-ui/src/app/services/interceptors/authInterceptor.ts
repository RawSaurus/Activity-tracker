import {HttpHandlerFn, HttpRequest} from "@angular/common/http";

export function authInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn){
  const req = request.clone({
    headers: request.headers.set(
      'Authorization',
      'Bearer '
    )
  });
  return next(req);
}

import {HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {inject} from "@angular/core";
import {TokenService} from "../token/token.service";

export function authInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn){
  const token = inject(TokenService);
  if(token.token){
    const req = request.clone({
      headers: request.headers.set(
        'Authorization',
        'Bearer ' + token.token
      )
    });
    return next(req);
  }
  return next(request);
}

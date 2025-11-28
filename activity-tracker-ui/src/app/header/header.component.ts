import {Component, inject, signal} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {TokenService} from "../services/token/token.service";

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  imports: [
    RouterOutlet,
    RouterLink
  ],
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  router = inject(Router);
  isLoggedIn = signal(false);
  private tokenService = inject(TokenService);

  ngDoCheck(){
    if(this.tokenService.token){
      this.isLoggedIn.set(true);
    }else{
      this.isLoggedIn.set(false);
    }
  }


  onClickLogout(){
    this.router.navigate(['login']);
  }

  onClickLogin(){
    this.router.navigate(['login']);
  }

  onClickRegister(){
    this.router.navigate(['register']);
  }
}

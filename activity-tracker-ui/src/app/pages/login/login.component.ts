import { Component } from '@angular/core';
import {AuthenticationRequest, AuthenticationResponse} from '../../services/models';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services';
import {TokenService} from '../../services/token/token.service';
import {NgFor, NgIf} from '@angular/common';
import {FormsModule, NgModel} from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [NgIf, NgFor, FormsModule]
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ){}

  login(){
    this.errorMsg = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe(
    {
      next: (res: AuthenticationResponse) =>{
        this.tokenService.token = res.token as string;
        this.router.navigate(['books']);
    },
    error: (err) =>{
      console.log(err);
      if(err.error.validationErrors){
        this.errorMsg = err.error.validationErrors;
      } else{
        this.errorMsg.push(err.error.error);
      }
    }
    })
  }

  register(){
    this.router.navigate(['register'])
  }
}

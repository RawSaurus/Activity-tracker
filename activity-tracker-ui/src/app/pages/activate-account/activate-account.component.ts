import { Component, NgModule } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services';
import {NgIf} from '@angular/common';
import {CodeInputModule} from 'angular-code-input';

@Component({
  standalone: true,
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss',
  imports: [NgIf, CodeInputModule]
})
export class ActivateAccountComponent {

  message: string = '';
  isOkay: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ){}

  onCodeCompletion(token: string){
    this.confirmAccount(token);
  }

  redirectToLogin(){
    this.router.navigate(['login'])
  }

  private confirmAccount(token: string){
    this.authService.confirm({token})
    .subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated. \nNow you can proceed to log in'
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid'
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }
}

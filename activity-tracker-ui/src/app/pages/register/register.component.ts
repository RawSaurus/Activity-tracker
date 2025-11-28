import {Component, OnInit} from '@angular/core';
import {RegistrationRequest} from '../../services/models';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services';
import {NgFor, NgIf} from '@angular/common';
import {FormsModule, NgModel} from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  imports: [NgIf, NgFor, FormsModule]
})
export class RegisterComponent implements OnInit{

  registerRequest: RegistrationRequest = {firstname: '', lastname: '', email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ){}

  ngOnInit(){
    window.localStorage.setItem('token', '');
  }

  register(){
    this.errorMsg = [];
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: () =>{
        this.router.navigate(['activate-account']);
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    })
  }

  login(){
    this.router.navigate(['login'])
  }

}

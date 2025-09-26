import {Component, inject, signal} from '@angular/core';
import {Router, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  imports: [
    RouterOutlet
  ],
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private router = inject(Router);
  isLoggedIn = signal(false);

  onClickLogin(){
    this.router.navigate(['login']);
  }

  onClickRegister(){
    this.router.navigate(['register']);
  }
}

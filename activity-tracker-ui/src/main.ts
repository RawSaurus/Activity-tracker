import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


import { provideClientHydration, BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AppRoutingModule } from './app/app-routing.module';
import { FormsModule } from '@angular/forms';
import { CodeInputModule } from 'angular-code-input';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';


bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(BrowserModule, AppRoutingModule, FormsModule, CodeInputModule),
        provideClientHydration(),
        HttpClient,
        provideHttpClient(withInterceptorsFromDi())
    ]
})
  .catch(err => console.error(err));

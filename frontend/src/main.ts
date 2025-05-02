import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthInterceptor } from './app/interceptors/auth.interceptor';
import { AuthService } from './app/core/services/auth.service';
import { AuthGuard } from './app/guards/auth.guard';
import { importProvidersFrom } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([AuthInterceptor])),
    AuthService,
    AuthGuard,
    importProvidersFrom(FormsModule),
    importProvidersFrom(ReactiveFormsModule),
  ],
}).catch((err) => console.error(err));

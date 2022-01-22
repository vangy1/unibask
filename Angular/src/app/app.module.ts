import {LOCALE_ID, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import localeSk from '@angular/common/locales/sk';
import {registerLocaleData} from "@angular/common";
import {AuthenticationService} from "./authentication/authentication.service";
import {SharedModule} from "./shared.module";
import {RoutingModule} from "./routing/routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NavigationComponent} from "./navigation/navigation.component";
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';

registerLocaleData(localeSk);

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    RoutingModule,
    SharedModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
  ],
  providers: [AuthenticationService, {provide: LOCALE_ID, useValue: 'sk-SK'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

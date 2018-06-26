import { NgModule, APP_INITIALIZER } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidemenuComponent } from './sidemenu/sidemenu.component';
import { SidemenuItemComponent } from './sidemenu-item/sidemenu-item.component';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTabsModule } from '@angular/material';
import { RouterModule } from '@angular/router';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { FullscreenComponent } from './fullscreen/fullscreen.component';
import { UserMenuComponent } from './user-menu/user-menu.component';
import {
  MatSidenavModule,
  MatSliderModule,
  MatProgressBarModule,
} from '@angular/material';
import { AuthService, ApiService, UserService, ConfigService } from '../service';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { LoginGuard, GuestGuard, AdminGuard } from '../guard';
import { ConfirmationService } from 'primeng/api';
import { AuthorityService } from '../service/authority.service';

export function initUserFactory(userService: UserService) {
  return () => userService.initUser();
}

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({

  declarations: [
    SidemenuComponent,
    SidemenuItemComponent,
    ToolbarComponent,
    FullscreenComponent,
    UserMenuComponent
  ],

  imports: [
    CommonModule,
    MatListModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatChipsModule,
    RouterModule,
    PerfectScrollbarModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatTabsModule,
    MatSliderModule,
    MatProgressBarModule,
    HttpClientModule,
    HttpModule,
  ],


  exports: [
    SidemenuComponent,
    SidemenuItemComponent,
    ToolbarComponent,
    FullscreenComponent,
    UserMenuComponent
  ],

  providers: [
    LoginGuard, 
    ConfirmationService,
    GuestGuard,
    AdminGuard,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    AuthorityService,
    {
      'provide': APP_INITIALIZER,
      'useFactory': initUserFactory,
      'deps': [UserService],
      'multi': true
    },
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }
  ]
})
export class CoreModule { }

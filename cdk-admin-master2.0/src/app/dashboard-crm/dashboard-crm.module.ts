import { NgModule, APP_INITIALIZER } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router'; 
import { DashboardCrmComponent } from './dashboard-crm.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { DashboardWidgetModule } from '../dashboard-widget/dashboard-widget.module';
import { UserService } from '../service';

export const appRoutes: Routes = [
    { path: '', component: DashboardCrmComponent },
]
export function initUserFactory(userService: UserService) {
  return () => userService.initUser();
}

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(appRoutes),
    FlexLayoutModule,
    MatCardModule,
    DashboardWidgetModule
  ],
  declarations: [DashboardCrmComponent],
  providers: [
 
    UserService,
    {
      'provide': APP_INITIALIZER,
      'useFactory': initUserFactory,
      'deps': [UserService],
      'multi': true
    }
  ],exports:[ ]
})
export class DashboardCrmModule { }

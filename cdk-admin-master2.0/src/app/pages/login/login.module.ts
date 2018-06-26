import { ConfigService } from './../../service/config.service';
import { NgModule } from '@angular/core';
import { LoginComponent } from './login.component';
import {
        MatCardModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatInputModule,
        MatToolbarModule
       } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { UserService, AuthService, ApiService } from '../../service';
import { AuthorityService } from '../../service/authority.service';
import {MatGridListModule} from '@angular/material/grid-list';

const routes: Routes = [
    {path: '', component: LoginComponent},
  ];
@NgModule({
    imports: [
        MatCardModule,
        CommonModule,
        FlexLayoutModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatInputModule,
        MatToolbarModule,
        FormsModule,
        HttpModule,
        MatGridListModule,
        HttpClientModule,
        ReactiveFormsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [
        LoginComponent,
    ],
    exports: [
        RouterModule
    ],
    providers: [
      UserService,
      AuthService,
      AuthorityService,
      ApiService, ConfigService
    ]
})
export class LoginModule {
}

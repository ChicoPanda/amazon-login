import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { TablesRouterModule } from './tables.router';

import { TableModule } from 'primeng/table';


import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MatSelectModule } from '@angular/material';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule,
         MatDialogModule,
         MatSortModule,
         MatCheckboxModule,
         MatTooltipModule,
         MatChipsModule,
         MatButtonToggleModule } from '@angular/material';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { GrowlModule, DropdownModule } from 'primeng/primeng';

import * as hljs from 'highlight.js';
import { HighlightJsModule, HIGHLIGHT_JS } from 'angular-highlight-js';
import * as hljsTypescript from 'highlight.js/lib/languages/typescript';
import { UserTableComponent } from './user-table/user-table.component';
import { ConfirmationService } from 'primeng/api';
import { AuthService, ApiService, UserService, ConfigService } from '../service';
import { AuthorityService } from '../service/authority.service';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { CdkTableModule } from '@angular/cdk/table';
import { UserEditComponent } from './../dialog-material/user-edit/user-edit.component';
import { UserDeleteComponent } from '../dialog-material/user-delete/user-delete.component';
import { UserInsertComponent } from '../dialog-material/user-insert/user-insert.component';

export function highlightJsFactory(): any {
  hljs.registerLanguage('typescript', hljsTypescript);
  return hljs;
}

@NgModule({
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    GrowlModule,
    DropdownModule,
    TableModule,
    ConfirmDialogModule,
    DialogModule,
    CdkTableModule,
    CommonModule,
    FlexLayoutModule,
    MatButtonModule,
    MatIconModule,
    MatTabsModule,
    MatToolbarModule,
    MatListModule,
    MatStepperModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCheckboxModule,
    MatTooltipModule,
    MatChipsModule,
    MatButtonToggleModule,
    HighlightJsModule.forRoot({
      provide: HIGHLIGHT_JS,
      useFactory: highlightJsFactory
    }),
    TablesRouterModule,
    HttpModule,
    HttpClientModule,
    ConfirmDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule 
  ],
  declarations: [
   UserTableComponent,
   UserEditComponent,
   UserDeleteComponent,
   UserInsertComponent
  ],
  entryComponents: [
    UserEditComponent,
    UserDeleteComponent,
    UserInsertComponent
  ],
  exports: [],
  providers: [
    ConfirmationService,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    AuthorityService,
  ]
})
export class TablesModule { }



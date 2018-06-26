import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserTableComponent } from './user-table/user-table.component';

const materialWidgetRoutes: Routes = [
  	{ path: 'users', component: UserTableComponent ,data: { animation: 'responsive' }}
];

@NgModule({
  imports: [
    RouterModule.forChild(materialWidgetRoutes)
  	],
  exports: [
    RouterModule
  ]
})
export class TablesRouterModule {}

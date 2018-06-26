import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent, Sort, MatDialog, MatDialogConfig } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { ConfigService, UserService, AuthService } from '../../service';
import { ConfirmationService, Message, SortEvent } from 'primeng/api';
import { Router } from '@angular/router';
import { AuthorityService } from '../../service/authority.service';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Authority } from '../../models/authority.model';
import { User } from '../../models/user.model';
//
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import { UserEditComponent } from '../../dialog-material/user-edit/user-edit.component';
import { UserDeleteComponent } from '../../dialog-material/user-delete/user-delete.component';
import { UserInsertComponent } from '../../dialog-material/user-insert/user-insert.component';
//

@Component({
  selector: 'user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.scss']
})
export class UserTableComponent implements OnInit {
//
userEdit: MatDialogRef<UserEditComponent>;
userDialogRef: any;


//
  displayedColumns = ['Name', 'Rol'];
  rows: Array<any> = [];
  showResponsiveTableCode;

  @ViewChild(MatPaginator) paginator1: MatPaginator;
  pageLength = 0;
  pageSize = 10;

  @Input() status;
  @Input() actionStatus;
  pageEvent: PageEvent;
  @Output() view = new EventEmitter();
  @Output() page = new EventEmitter();
  @Output() sort = new EventEmitter();
  @Output() dup = new EventEmitter();

  next(event):any {
    this.rows = [];
    for (var i = 1 * event.pageIndex * event.pageSize; i < event.pageSize + event.pageIndex * event.pageSize; i++) {
      if (i <this.users.length) {
        this.rows = [...this.rows, this.users[i]];
      }
    }
    return event;
  }
  getRows() {

  }
  sortData(sort: Sort) {
    const data = this.users.slice();
    if (!sort.active || sort.direction == '') {
      this.rows = data;
      return;
    }

    this.rows = data.sort((a, b) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case 'authority': return compare(a.authority, b.authority, isAsc);

        case 'firstname': return compare(a.firstname, b.firstname, isAsc);
        default: return 0;
      }
    });
  }





  form: any;

  cols: { field: string; header: string; }[];
  msgs: Message[] = [];
  users: User[];
  authorities: Authority[];
  length = 100;
  pageSizeOptions = [1, 5, 10, 25, 100];
  visible: boolean = true;
  submitted = false;
  displayDialog: boolean;

  selectedAuthority: Authority;
  selectedUser: User = {};
  newselectedUser: boolean;

  constructor(private config: ConfigService,
    private userService: UserService,
    protected http: HttpClient,
    protected confirmationService: ConfirmationService,
    private router: Router,
    private authService: AuthService,
    private authorityService: AuthorityService,
    private dialog: MatDialog) {
    this.form = new FormGroup({
      username: new FormControl("", Validators.required),
      firstname: new FormControl("", Validators.required),
      password: new FormControl(""),
      repeatPassword: new FormControl(""),
      permission: new FormControl(),
    });
  }

  ngOnInit() {
    this.findAllUsers();
    this.cols = [
      { field: 'firstname', header: 'Name' },
      { field: 'authority', header: 'Rol' }
    ];
if( typeof this.users != "undefined"){
this.pageLength = this.users.length;
}

  }

  openDialogEdit(user: User) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = user;

    this.userDialogRef= this.dialog.open(UserEditComponent, dialogConfig);
    
    this.userDialogRef.afterClosed()
    .subscribe(() => {
      console.log("me recargo por ti")
      this.reload();
    })
  }

  openDialogDelete(user: User) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = user;

    this.userDialogRef= this.dialog.open(UserDeleteComponent, dialogConfig);
    
    this.userDialogRef.afterClosed()
    .subscribe(() => {
      console.log("me recargo por ti")
      this.reload();
    })
  }

  openDialogInsert(user: User) {
    console.log("g");
  
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    console.log(user);
    this.userDialogRef= this.dialog.open(UserInsertComponent, dialogConfig);
    
    this.userDialogRef.afterClosed()
    .subscribe(() => {
      console.log("me recargo por ti")
      this.reload();
    })
  }

  findAllUsers(): void {
    this.userService.getAll()
      .map(res => res)
      .subscribe((result: any) => {
        this.users = result;
        this.users.forEach(user => {
          var authorityi = user.rol.name.split("_")[1];
          user.authority = authorityi.toUpperCase();

        });
        this.rows = this.users;
      })
  }

  findAllAuthorities(): void {
    this.authorityService.getAll()
      .map(res => res)
      .subscribe((result: any) => {
        this.authorities = result;
        this.selectedAuthority = this.authorities[0];
      });
  }

  showDialogToAdd() {
    this.newselectedUser = true;
    this.selectedUser = {};
    this.form.get('repeatPassword').setValidators(Validators.required);
    this.form.get('password').setValidators(Validators.required);
    this.form.get('password').updateValueAndValidity();
    this.form.get('repeatPassword').updateValueAndValidity();
    this.form.get('username').setValidators(Validators.required);
    this.form.get('username').updateValueAndValidity();
    this.displayDialog = true;
  }


  onRowSelect(event) {
    this.newselectedUser = false;
    this.selectedUser = this.cloneselectedUser(event.data);
    this.form.get('repeatPassword').clearValidators();
    this.form.get('repeatPassword').updateValueAndValidity();
    this.form.get('password').clearValidators();
    this.form.get('password').updateValueAndValidity();
    this.form.get('username').clearValidators();
    this.form.get('username').updateValueAndValidity();
    if (this.selectedUser.id == this.userService.currentUser.id) {
      this.form.get('permission').disable();
    } else {
      this.form.get('permission').enable();
    }
    // this is to rerun form validation after removing the validation for a field.
    this.displayDialog = true;

  }

  cloneselectedUser(c: User): User {
    let selectedUser = {};
    for (let prop in c) {
      selectedUser[prop] = c[prop];
    }
    return selectedUser;
  }

  reload(): void {
    this.findAllUsers();
    this.visible = false;
    setTimeout(() => this.visible = true, 0);
    this.selectedUser = null;
    this.displayDialog = false;
  }

  public mostrar() {
    document.getElementById("gif").style.display = "block"
  }

  public ocultar() {
    this.displayDialog = false;
  }

  signUp() {
    this.mostrar();
    this.ocultar();
    this.authService.signup(this.selectedUser)
      .subscribe(
        response => {
          this.msgs.push({
            severity: 'success',
            summary: 'User add',
            detail: `${this.selectedUser.firstname} Add succesfully`
          });
          this.reload();
          document.getElementById("gif").style.display = "none"
        },
        error => this.msgs.push({
          severity: 'error',
          summary: 'Error SignUp User'
        })
      );
  }

}

function compare(a, b, isAsc) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}


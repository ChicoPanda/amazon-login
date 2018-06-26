
import { User } from '../../models/user.model';

import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormBuilder, Validators, FormGroup,  FormControl} from "@angular/forms";
import { Authority } from '../../models/authority.model';
import { AuthorityService } from '../../service/authority.service';
import { UserService } from '../../service/user.service';
import { Message } from 'primeng/primeng';
@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.scss']
})
export class UserDeleteComponent implements OnInit{
  msgs: Message[] = [];
  authorities:Authority[];
  form: any;
  selectUser: User;
  selectedAuthority: Authority;

   constructor( 
    private fb: FormBuilder,
    private authorityService: AuthorityService,
    private userService: UserService,
    private dialogRef: MatDialogRef<UserDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) data) {
      this.selectUser = data;
      
      console.log("selectUser ");
      console.log(this.selectUser);
      this.form = new FormGroup(
        {
         
        }
      );
    }

    ngOnInit() {
      
    }
  
    delete(user:User): void {
      console.log("me elimina");
      this.userService.remove( this.selectUser).subscribe(
        response => {
          this.msgs.push({
            severity: 'success',
            summary: 'User updated',
          });
      
        },
        error => this.msgs.push({
          severity: 'error',
          summary: 'Error updating User',
        })
      );
    }


    submit(form){
      console.log("usuario");
      console.log(this.selectUser);
      this.delete(this.selectUser);
      this.selectUser=null;
      this.dialogRef.close(`${form.value.filename}`);
    }
}

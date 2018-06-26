
import { User } from '../../models/user.model';

import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormBuilder, Validators, FormGroup,  FormControl} from "@angular/forms";
import { Authority } from '../../models/authority.model';
import { AuthorityService } from '../../service/authority.service';
import { UserService } from '../../service/user.service';
import { Message } from 'primeng/primeng';
@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit{
  msgs: Message[] = [];
  authorities:Authority[];
  form: any;
  selectUser: User;
  selectedAuthority: Authority;

   constructor( 
    private fb: FormBuilder,
    private authorityService: AuthorityService,
    private userService: UserService,
    private dialogRef: MatDialogRef<UserEditComponent>,
    @Inject(MAT_DIALOG_DATA) data) {
      this.selectUser = data;
      
      console.log("selectUser ");
      console.log(this.selectUser);
      this.form = new FormGroup(
      {
        firstname_input: new FormControl(),
        rol_select: new FormControl()
      }
      );
    }

    ngOnInit() {
      this.findAllAuthorities();
      //console.log(this.authorities);
    }
  
    findAllAuthorities(): void {
      this.authorityService.getAll()
        .map(res => res)
        .subscribe((result: any) => {
          console.log(result);
          this.authorities = result;
        });
    }
    edit(): void {
      this.userService.edit(this.selectUser.id, this.selectUser).subscribe(
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
      this.authorities.forEach(element => {
        if(this.form.value.rol_select==element.id){
          this.selectUser.rol=element;
        }
      });

      this.selectUser.firstname=this.form.value.firstname_input;

      this.edit();

      this.dialogRef.close(`${form.value.filename}`);
    }
}

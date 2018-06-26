
import { User } from '../../models/user.model';

import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormGroup,  FormControl} from "@angular/forms";
import { Authority } from '../../models/authority.model';
import { AuthorityService } from '../../service/authority.service';
import { Message } from 'primeng/primeng';
import { AuthService } from '../../service';
@Component({
  selector: 'app-user-insert',
  templateUrl: './user-insert.component.html',
  styleUrls: ['./user-insert.component.scss']
})
export class UserInsertComponent implements OnInit{
  msgs: Message[] = [];
  authorities:Authority[];
  form: any;
  selectUser: User;
  selectedAuthority: Authority;

   constructor( 

    private authorityService: AuthorityService,
    private authService: AuthService,
    private dialogRef: MatDialogRef<UserInsertComponent>) {
      
      this.selectUser ={
        username: "",
        firstname: "",
        password: "",
        rol:null
      };
      
      this.form = new FormGroup(
        {
          firstname_input: new FormControl(),
          username_input: new FormControl(),
          rol_select: new FormControl(),
          password_input: new FormControl(),
          password_input1: new FormControl()
        }
      );
    }

    ngOnInit() {
      this.findAllAuthorities();
    }
  
    findAllAuthorities(): void {
      this.authorityService.getAll()
        .map(res => res)
        .subscribe((result: any) => {
          console.log(result);
          this.authorities = result;
        });
    }

    submit(form){
      if(this.form.value.password_input==this.form.value.password_input1){
        this.authorities.forEach(element => {
          if(this.form.value.rol_select==element.id){
            this.selectUser.rol=element;
          }
        });
      }

      //this.selectUser.firstname=this.form.value.firstname_input;
      this.selectUser.firstname=this.form.value.firstname_input;
      this.selectUser.password=this.form.value.password_input;

      this.insert();

      this.dialogRef.close(`${form.value.filename}`);
    }

    insert(){
      console.log("insertar");
      console.log(this.selectUser);
      this.authService.signup(this.selectUser)
      .subscribe(
        response => {
          this.msgs.push({
            severity: 'success',
            summary: 'User add',
            detail: `${this.selectUser.firstname} Add succesfully`
          });
        },
        error => this.msgs.push({
          severity: 'error',
          summary: 'Error SignUp User'
        })
      );
    }

    onFileChange(event) {
      let reader = new FileReader();
      if (event.target.files && event.target.files.length > 0) {
        let file = event.target.files[0];
        reader.readAsDataURL(file);
  
        reader.onload = () => {
          this.selectUser.username = reader.result.split(',')[1];
        };
      }
    }
}

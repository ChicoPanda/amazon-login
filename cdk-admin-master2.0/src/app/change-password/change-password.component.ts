import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../app/service';
import { Router } from '@angular/router';
import { DisplayMessage } from '../models/display-message';
import { CustomValidators } from '../utilities/passwordValid';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  form: FormGroup;

  submitted = false;

  notification: DisplayMessage;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {

    this.form = this.formBuilder.group({
      oldPassword: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      newPassword: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])],
      repeatPassword: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
    this.form.get('repeatPassword').setValidators(
      CustomValidators.equals(this.form.get('newPassword'))
    );

  }

  onSubmit() {

    this.notification = undefined;
    this.submitted = true;

    this.authService.changePassowrd(this.form.value)
    // show me the animation
    .delay(1000)
    .mergeMap(() => this.authService.logout())
    .subscribe(() => {
      this.router.navigate(['/login', { msgType: 'success', msgBody: 'Success! Please sign in with your new password.'}]);
    }, error => {
      this.submitted = false;
      this.notification = { msgType: 'error', msgBody: 'Invalid old password.'};
    });

  }

}

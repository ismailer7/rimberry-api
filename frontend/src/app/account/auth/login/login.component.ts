import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthfakeauthenticationService } from '../../../core/services/authfake.service';

import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

/**
 * Login component
 */
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  error = '';
  isError = false;
  isSuccess = false;
  returnUrl: string;

  // password display
  password = "password";
  showPassword = false;

  // set the currenr year
  year: number = new Date().getFullYear();

  loading: any = 'Log in'

  // tslint:disable-next-line: max-line-length
  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router,
    private authFackservice: AuthfakeauthenticationService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['ismaa@rimberry.ma', [Validators.required, Validators.email]],
      password: ['password', [Validators.required]],
    });

    // reset login status
    // this.authenticationService.logout();
    // get return url from route parameters or default to '/'
    // tslint:disable-next-line: no-string-literal
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    const currentUser = this.authFackservice.currentUserValue;
    if(currentUser) {
      // already logged in and active token.
      this.router.navigate(['/dashboard']);
    }
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  /**
   * Form submit
   */
  onSubmit() {
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    } else {
      this.submitted = true;
        this.authFackservice.login(this.f.email.value, this.f.password.value)
          .pipe(first())
          .subscribe(
            data => {
              this.isSuccess = true;
              this.isError = false;
              setTimeout(() => {
                console.log('sleep');
                this.router.navigate(['/dashboard']);
                // And any other code that should run only after 5s
              }, 1500);
              //this.router.navigate(['/dashboard']);
            },
            error => {
              this.error = error ? error : '';
              this.isError = true;
              this.isSuccess = false;
              this.submitted = false;
              this.error = this.error['error']['message'];
            });
    }
  }

  onShow() {
    console.log('onShow clicked.');
    this.showPassword = !this.showPassword;
    if(this.showPassword == false) this.password = "text";
    else this.password = "password";
    this.ngOnInit();
  }

}

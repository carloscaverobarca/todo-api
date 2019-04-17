/**
Copyright © 2018  Atos Spain SA. All rights reserved.
This file is part of the Personal Health System (PHS) for HeartMan project.
Personal Health System (PHS) is free software: you can redistribute it and/or modify it under the terms of Apache 2.0.
THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
See README file for the full disclaimer information and LICENSE file for full license information in the project root.
*/
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../auth.service';
import { HttpErrorResponse } from '@angular/common/http';

import { AutofocusDirective } from '../directives/autofocus.directive';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userName: string;
  userPassword: string;
  loginStatus: string;
  authError: boolean;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.loginStatus = 'Logged ' + (this.authService.isLoggedIn ? 'in' : 'out');
   }

  ngOnInit() {
  }

  login() {
    this.loginStatus = 'Trying to log in';
    this.authService.login(this.userName, this.userPassword).subscribe(() => {
      this.loginStatus = 'Logged ' + (this.authService.isLoggedIn ? 'in' : 'out');
      if (this.authService.isLoggedIn) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        const redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/list';
        // Redirect the user
        this.router.navigate([redirect]);
      } else {
        this.loginStatus = 'Could not log in.';
        this.showAuthenticationError();
      }
    },
    (err) => this.handleError(err));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred: ', error.error.message);
      this.loginStatus = '' + error.error.message;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
      this.loginStatus = '' + error.status;
    }
    this.showAuthenticationError();
  }

  showAuthenticationError() {
    this.authError = true;
    setTimeout(() => {
      this.authError = false;
      this.userName = '';
      this.userPassword = '';
    }, 3000);
  }

  public closeAlert() {
    this.authError = false;
  }
}

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
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { ApiService } from './api.service';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';

import { User } from './data-model/user';

@Injectable()
export class AuthService {
  user: User;
  isLoggedIn = false;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(
    private apiService: ApiService
  ) { }

  login(user, password): Observable<boolean> {
    return this.apiService.login(user, password).do(val => {
      if (val) {
        console.log('Setting user and isLoggedIn');
        this.user = {username: user, password: password};
        this.isLoggedIn = true;

        // Assigning token in backend service
        // TODO the backend should return an OAuth compliant object
        // this.backendService.setToken(val._body);
        this.apiService.setToken(val._body);
      } else {
        console.log('Login was not successful');
      }
    });
  }
}

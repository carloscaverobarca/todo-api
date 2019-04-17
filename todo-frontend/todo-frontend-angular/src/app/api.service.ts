import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { HttpErrorResponse } from '@angular/common/http';

import { Todo } from './todo';
import { UserCommunicationService } from './user-communication.service';
import { User } from './data-model/user';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';

const API_URL = environment.apiUrl;

@Injectable()
export class ApiService {

  // Auth-token
  token: string;
  
  constructor(
    private http: Http,
    private userComumunicationService: UserCommunicationService
  ) {
  }

  setToken(token) {
    this.token = token;
  }

  login(userName: string, password: string): Observable<any>  {
    console.log('Login with ' + userName);
    const user = new User();
    user.username = userName;
    user.password = password;
    return this.http.post(API_URL + '/login', user);
  }

  public getAllTodos(): Observable<Todo[]> {
    console.log('Get all Todos');
    const headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.token);
    return this.http
      .get(API_URL + '/todos',
      { headers: headers })
      .map(response => {
        const todos = response.json();
        return todos.map((todo) => new Todo(todo));
      });
  }

  public createTodo(todo: Todo): Observable<Todo> {
    console.log('Create todo');
    const headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.token);
    return this.http
      .post(API_URL + '/todos', todo,
      { headers: headers })
      .map(response => {
        return new Todo(response.json());
      });
  }

  public getTodoById(todoId: number): Observable<Todo> {
    console.log('Get todo by id');
    const headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.token);
    return this.http
      .get(API_URL + '/todos/' + todoId,
      { headers: headers })
      .map(response => {
        return new Todo(response.json());
      });
  }

  public updateTodo(todo: Todo): Observable<Todo> {
   console.log('Update todo');
   const headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.token);
    return this.http
      .put(API_URL + '/todos/' + todo.id, todo,
      { headers: headers })
      .map(response => {
        return new Todo(response.json());
      });
  }

  public deleteTodoById(todoId: number): Observable<null> {
   console.log('Delete todo');
    const headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.token);
    return this.http
      .delete(API_URL + '/todos/' + todoId,
      { headers: headers })
      .map(response => null);
  }

  handleError(where: string, error: HttpErrorResponse) {
    let errorText = 'An error occurred in ' + where;
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      errorText = errorText + ': ' + error.error.message;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorText = errorText + `: code ${error.status}, ${error.error}`;
      errorText = errorText.replace(', undefined', ', contact support');

      /* if (error.status == 401) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        const redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/list';
        // Redirect the user
        this.router.navigate([redirect]);
        
      } */
    }
    console.error(errorText);
    this.userComumunicationService.createMessage(this.userComumunicationService.ERROR, errorText);
  }
}

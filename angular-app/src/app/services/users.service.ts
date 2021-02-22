import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

const baseURL = 'http://localhost:8080/rest/user';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  constructor(private httpClient: HttpClient) {}

  readAll(): Observable<User[]> {
    return this.httpClient.get<User[]>(baseURL);
  }

  getUser(login: string): Observable<User> {
    return this.httpClient.get<User>(baseURL + '/' + login);
  }

  createUser(user: User): Observable<any> {
    console.log('POST User: ' + JSON.stringify(user));
    return this.httpClient.post<User>(baseURL, user);
  }

  getRoles(): Observable<any> {
    return this.httpClient.get(baseURL + '/roles');
  }

  deleteUser(login: string): Observable<any> {
    console.log('DELETE User: ' + login);
    return this.httpClient.delete<string>(baseURL + '/' + login);
  }

  updateUser(user: User): Observable<any> {
    console.log('PUT User: ' + JSON.stringify(user));
    return this.httpClient.put<User>(baseURL, user);
  }
}

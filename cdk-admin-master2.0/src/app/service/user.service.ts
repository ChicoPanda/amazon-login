import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ConfigService } from './config.service';
import { Observable } from 'rxjs/Observable';
import { User } from '../models/user.model';

@Injectable()
export class UserService {

  currentUser;
  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  initUser() {
    const promise = this.apiService.get(this.config.refresh_token_url).toPromise()
      .then(res => {
        if (res.access_token !== null) {
          return this.getMyInfo().toPromise()
            .then(user => {
              this.currentUser = user;
            });
        }
      })
      .catch(() => null);
    return promise;
  }

  resetCredentials() {
    return this.apiService.get(this.config.reset_credentials_url);
  }

  getMyInfo() {
     this.apiService.get(this.config.whoami_url).map(user => localStorage.setItem("userRol",user.rol.name));
    return this.apiService.get(this.config.whoami_url).map(user => localStorage.setItem("userName",user.firstname));
  }

  getAll() {
    console.log(this.currentUser);

    return this.apiService.get(this.config.users_url);
  }

  remove(user: User): Observable<any> {
    return this.apiService.delete(this.config.user_url + `/${user.id}`, user).map(() => {
      console.log("Remove success");
    });
  }

  edit(id: number, user: User): Observable<any> {
    return this.apiService.put(this.config.user_url + `/${id}`, user).map(() => {
      console.log("Edit success");
    });
  }

  findById(id: number): Observable<any> {
    return this.apiService.get(this.config.user_url + `/${id}`);
  }
}

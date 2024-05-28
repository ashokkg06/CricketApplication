import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { AdminResponse } from '../Model/AdminResponse/admin-response';

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {
  public baseurl = environment.apiUrl;
  public apiUrl = this.baseurl.replace('iplseries', '') + "admin";
  public _token?: string;
  public response?: AdminResponse;
  public admin?: string;

  constructor(private http: HttpClient, private router: Router) {}

  set token(value: string) {
    localStorage.setItem('token', value);
    this._token = value;
  }

  register(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data).pipe(
      tap((response: any) => {
        if(response.token !== null) {
          this.setSession(response.token);
          this.setAdminName(response.name);
          this._token = response.token;
          this.response = response;
          this.router.navigate(['/admin-panel']);
        }
      })
    );
  }

  login(data: any): Observable<AdminResponse> {
    return this.http.post(`${this.apiUrl}/login`, data).pipe(
      tap((response: any) => {
        if(response.token !== null) {
          this.setSession(response.token);
          this.setAdminName(response.name);
          this._token = response.token;
          this.response = response;
          this.router.navigate(['/admin-panel']);
        }
      })
    );
  }

  private setSession(token: string): void {
    localStorage.setItem('jwt_token', token);
  }

  private setAdminName(name: string): void {
    localStorage.setItem('admin_name', name);
  }

  logout(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.clear();
      this.router.navigate(['/login']);
    }
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}

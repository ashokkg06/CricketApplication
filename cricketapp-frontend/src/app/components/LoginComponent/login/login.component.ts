import { Component } from '@angular/core';
import {FormControl, FormControlDirective, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import { AuthserviceService } from '../../../core/Services/authservice.service';
import { Router } from '@angular/router';
import { AdminResponse } from '../../../core/Model/AdminResponse/admin-response';
import { error } from 'console';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  public email?: FormControl;
  public password?: FormControl;
  public response?: AdminResponse;

  constructor(private authService: AuthserviceService, private router: Router) {}

  login(form: any): void {
    if (form.valid) {
      this.authService.login(form.value).subscribe((loginResponse) => {
        if(loginResponse.message === "Admin Not Found" || loginResponse.message === "Invalid email or password") {
          this.response = loginResponse;
        }
        else {
          this.response = loginResponse;
          this.authService._token = loginResponse.token;
          this.authService.response = loginResponse;
          this.router.navigate(['/admin-panel']);
        }
    })
    }
  }

  homepage(): void {
    this.router.navigate(['/iplseries/' + localStorage.getItem("season") + '/matches']);
  }
}

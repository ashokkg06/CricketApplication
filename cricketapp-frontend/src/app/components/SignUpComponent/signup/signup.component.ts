import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthserviceService } from '../../../core/Services/authservice.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  constructor(private authService: AuthserviceService, private router: Router) {}

  signup(form: any): void {
    if (form.valid) {
      this.authService.register(form.value).subscribe(() => {
        this.router.navigate(['/admin-panel']);
      });
    }
  }
}

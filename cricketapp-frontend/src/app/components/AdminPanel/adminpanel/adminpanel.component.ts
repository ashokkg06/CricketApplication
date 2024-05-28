import { Component, OnInit } from '@angular/core';
import { AuthserviceService } from '../../../core/Services/authservice.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FileUploadService } from '../../../core/Services/file-upload.service';
import { error } from 'console';
import { AdminResponse } from '../../../core/Model/AdminResponse/admin-response';

@Component({
  selector: 'app-adminpanel',
  standalone: true,
  imports: [AdminpanelComponent, CommonModule],
  templateUrl: './adminpanel.component.html',
  styleUrl: './adminpanel.component.css'
})
export class AdminpanelComponent implements OnInit {
  public token?: string;
  public message?: string;
  public adminName?: string;
  selectedFile1!: File;
  selectedFile2!: File;
  season?: string;

  constructor(private fileUploadService: FileUploadService, private authservice: AuthserviceService ,private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
      this.setToken();
      if(this.token == undefined) {
        this.router.navigate(['admin/login']);
        // this.message = "You don't have access to this page";
        // this.page = "Go to home page";
        // this.isAdmin = false;
      }
      else {
        this.message = "Welcome Admin - " + this.adminName;
      }
  }

  setToken() :void {
    // console.log("1");
    if (typeof localStorage !== 'undefined') {
      // console.log("2");
      this.token = localStorage['jwt_token'];
      this.adminName = localStorage['admin_name'];
      // console.log("3");
    }
    // console.log("4");
  }

  logout(): void {
    console.log("logout");
    if (typeof localStorage !== 'undefined') {
      localStorage.clear();
      this.router.navigate(['/admin/login']);
    }
  }

  onFile1Selected(event: any) {
    this.selectedFile1 = event.target.files[0];
  }

  onFile2Selected(event: any) {
    this.selectedFile2 = event.target.files[0];
  }

  // onUpload() {
  //   if (this.selectedFile) {
  //     this.fileUploadService.upload(this.selectedFile).subscribe(response => {
  //       console.log(response);
  //       if(response.message === "File Already Exists") {
  //         alert('File Already Exists!');
  //       }
  //       else {
  //         alert('File uploaded successfully');
  //       }
  //     });
  //   } else {
  //     alert('Please select a file first');
  //   }
  // }


  onUpload() {
    if (this.selectedFile1 && this.selectedFile2) {
      this.fileUploadService.upload(this.selectedFile1).subscribe({
        next: (response) => {
          console.log(response);
          alert('File - ' + this.selectedFile1.name + ' uploaded successfully');
          // Upload the second file after the first one is uploaded
          this.fileUploadService.upload(this.selectedFile2).subscribe({
            next: (response) => {
              console.log(response);
              alert('File - ' + this.selectedFile2.name + ' uploaded successfully');
              console.log(this.selectedFile1.name);
              this.fileUploadService.addData(this.selectedFile1.name, this.selectedFile2.name).subscribe(response => {
                console.log(response);
                console.log(this.selectedFile2.name);
                console.log(response[0]);
                this.router.navigate(['/iplseries/' + response[0] + '/matches']);
                // console.log(this.season);
                // console.log(this.season.length);
                // console.log(this.season.substring(10,14));
                // console.log();
              })
            },
            error: (error) => {
              console.error(error);
              alert('File 2 upload failed');
            }
          });
        },
        error: (error) => {
          console.error(error);
          alert('File 1 upload failed');
        }
      });
    } else {
      alert('Please select both files first');
    }
  }

  register(): void {
    this.router.navigate(['admin/register']);
  }
}

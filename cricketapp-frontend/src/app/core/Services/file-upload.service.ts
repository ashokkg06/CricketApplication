import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthserviceService } from './authservice.service';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  private baseUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient, private authservice: AuthserviceService) { }

  upload(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authservice.getToken()}` // Replace with your token retrieval method
    });

    return this.http.post<any>(`${this.baseUrl}/upload`, formData, { headers });
  }

  addData(file1: string, file2: string): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file1', file1);
    formData.append('file2', file2);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authservice.getToken()}` // Replace with your token retrieval method
    });

    return this.http.post<any>(`${this.baseUrl}/addData`, formData, { headers });
  }
}

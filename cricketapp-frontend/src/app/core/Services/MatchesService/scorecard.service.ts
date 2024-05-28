import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Scorecard } from '../../Model/ScorecardData/scorecard';

@Injectable({
  providedIn: 'root'
})
export class ScorecardService {
  private serverurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getScorecard(matchNo: number, season: number): Observable<Scorecard> {
    return this.http.get<Scorecard>(`${this.serverurl}/${season}/matches/${matchNo}`);
  }
}

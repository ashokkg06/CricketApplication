import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchStatistics } from '../../Model/MatchStatisticsData/match-statistics';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MatchStatisticsService {
  private baseUrl = environment.apiUrl;
  private homeUrl = environment.apiUrl.replace("/iplseries","");

  constructor(private http: HttpClient) { }

  getMatchStatistics(season: number): Observable<MatchStatistics[]> {
    return this.http.get<MatchStatistics[]>(`${this.baseUrl}/${season}/matches`);
  }

  getSeasons(): Observable<string[]> {
    return this.http.get<string[]>(`${this.homeUrl}/getSeasons`);
  }
}

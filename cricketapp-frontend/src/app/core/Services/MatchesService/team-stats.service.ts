import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TeamStats } from '../../Model/TeamStats/team-stats';

@Injectable({
  providedIn: 'root'
})
export class TeamStatsService {
  private baseurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getTeamStats(teamName: string, season: number) : Observable<TeamStats> {
    return this.http.get<TeamStats>(`${this.baseurl}/${season}/teams/${teamName}`);
  }
}

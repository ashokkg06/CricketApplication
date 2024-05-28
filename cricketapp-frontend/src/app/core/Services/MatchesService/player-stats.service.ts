import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlayerStats } from '../../Model/PlayerStats/player-stats';

@Injectable({
  providedIn: 'root'
})
export class PlayerStatsService {
  private serverurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getPlayerStats(name: string, season: number): Observable<PlayerStats> {
    return this.http.get<PlayerStats>(`${this.serverurl}/${season}/playerstats/${name}`);
  }
}

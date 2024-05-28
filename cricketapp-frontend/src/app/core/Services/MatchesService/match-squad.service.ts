import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchSquad } from '../../Model/MatchSquadData/match-squad';

@Injectable({
  providedIn: 'root'
})
export class MatchSquadService {
  private baseurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getMatchSquad(matchNumber: number, season: number): Observable<MatchSquad> {
    return this.http.get<MatchSquad>(`${this.baseurl}/${season}/matches/${matchNumber}/squads`);
  }
}

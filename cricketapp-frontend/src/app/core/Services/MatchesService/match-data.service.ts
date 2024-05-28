import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchDataDto } from '../../Model/MatchInfo/match-data-dto';

@Injectable({
  providedIn: 'root'
})
export class MatchDataService {
  private serverurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getMatchInfo(matchNumber: number, season: number): Observable<MatchDataDto> {
    return this.http.get<MatchDataDto>(`${this.serverurl}/${season}/matches/${matchNumber}/info`);
  }
}

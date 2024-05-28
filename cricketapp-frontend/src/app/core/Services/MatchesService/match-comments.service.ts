import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MatchCommentsDto } from '../../Model/MatchCommentsData/match-comments-dto';

@Injectable({
  providedIn: 'root'
})
export class MatchCommentsService {
  private serverurl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getCommentary(matchNumber: number, season: number) : Observable<MatchCommentsDto> {
    return this.http.get<MatchCommentsDto>(`${this.serverurl}/${season}/matches/${matchNumber}/commentary`);
  }
}

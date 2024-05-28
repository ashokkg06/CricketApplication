import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PointsTableDTO } from '../../Model/PointsTableData/points-table-dto';
import { PointsTable } from '../../Model/PointsTableData/points-table';

@Injectable({
  providedIn: 'root'
})

export class PointstableService {
  private serverurl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getPointsTable(matchNo?: number, season?: number): Observable<PointsTableDTO> {
    if (matchNo) {
      return this.http.get<PointsTableDTO>(`${this.serverurl}/${season}/matches/${matchNo}/points-table`);
    } else {
      return this.http.get<PointsTableDTO>(`${this.serverurl}/${season}/points-table`);
    }
  }

  getTeams(season: number): Observable<PointsTable[]> {
    return this.http.get<PointsTable[]>(`${this.serverurl}/${season}/teams`);
  }
}

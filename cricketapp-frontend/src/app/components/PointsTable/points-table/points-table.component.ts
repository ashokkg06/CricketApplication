import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { PointsTableDTO } from '../../../core/Model/PointsTableData/points-table-dto';
import { PointstableService } from '../../../core/Services/MatchesService/pointstable.service';
import { MatchListComponent } from '../../MatchStatistics/match-statistics.component';

@Component({
  selector: 'app-points-table',
  standalone: true,
  imports: [PointsTableComponent, CommonModule, RouterModule, RouterLink, RouterLinkActive, MatchListComponent],
  templateUrl: './points-table.component.html',
  styleUrl: './points-table.component.css'
})
export class PointsTableComponent implements OnInit {
  pointsTable!: PointsTableDTO;
  matchNumber?: number;
  season!: number;

  constructor(private pointsTableService: PointstableService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.matchNumber = params['matchNumber'] ? +params['matchNumber'] : undefined;
        this.season = params['season'];
        this.getPointsTable(this.matchNumber? this.matchNumber: undefined, this.season);
      })
  }

  getPointsTable(matchNumber?: number, season?: number) :void {
    this.pointsTableService.getPointsTable(matchNumber, season)
      .subscribe(data => {
        this.pointsTable = data;
    })
  }
}

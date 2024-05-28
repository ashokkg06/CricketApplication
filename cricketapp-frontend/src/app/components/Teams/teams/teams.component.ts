import { Component, OnInit } from '@angular/core';
import { PointsTable } from '../../../core/Model/PointsTableData/points-table';
import { PointstableService } from '../../../core/Services/MatchesService/pointstable.service';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [TeamsComponent, RouterOutlet, CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.css'
})
export class TeamsComponent implements OnInit{
  teams!: PointsTable[];
  season!: number;

  constructor(private PointsTableService: PointstableService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.season = params["season"];
        this.getTeams(this.season);
      })
  }

  getTeams(season: number): void {
    this.PointsTableService.getTeams(season).subscribe(team => {
      this.teams = team;
    })
  }
}

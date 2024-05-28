import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { TeamStats } from '../../../core/Model/TeamStats/team-stats';
import { TeamStatsService } from '../../../core/Services/MatchesService/team-stats.service';

@Component({
  selector: 'app-team-stats',
  standalone: true,
  imports: [TeamStatsComponent, RouterOutlet, CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './team-stats.component.html',
  styleUrl: './team-stats.component.css'
})
export class TeamStatsComponent implements OnInit{
  public teamStats!: TeamStats;
  public teamName!: string;
  public season!: number;

  constructor(private TeamStatsService: TeamStatsService, private route: ActivatedRoute){}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.teamName = params["teamName"];
        this.season = params["season"];
        this.getTeamStats(this.teamName, this.season);
      })
  }

  getTeamStats(teamName: string, season: number): void {
    this.TeamStatsService.getTeamStats(teamName, season).subscribe(response => {
      this.teamStats = response;
    })
  }
}

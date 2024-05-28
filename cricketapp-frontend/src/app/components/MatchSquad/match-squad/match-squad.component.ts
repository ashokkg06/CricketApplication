import { Component, OnInit } from '@angular/core';
import { MatchStatistics } from '../../../core/Model/MatchStatisticsData/match-statistics';
import { MatchSquadService } from '../../../core/Services/MatchesService/match-squad.service';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatchSquad } from '../../../core/Model/MatchSquadData/match-squad';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-match-squad',
  standalone: true,
  imports: [MatchSquadComponent, CommonModule, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './match-squad.component.html',
  styleUrl: './match-squad.component.css'
})
export class MatchSquadComponent implements OnInit {
  public matchSquad!: MatchSquad;
  public matchNumber!: number;
  public season!: number;

  constructor(private matchSquadService: MatchSquadService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.matchNumber = params["matchNumber"];
        this.season = params["season"];
        this.getMatchSquad(this.matchNumber, this.season);
      })
  }

  getMatchSquad(matchNumber: number, season: number): void {
    this.matchSquadService.getMatchSquad(matchNumber, season).subscribe(response => {
      this.matchSquad = response;
    });
  }
}

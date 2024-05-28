import { Component, OnInit } from '@angular/core';
import { Scorecard } from '../../core/Model/ScorecardData/scorecard';
import { ScorecardService } from '../../core/Services/MatchesService/scorecard.service';
import { error } from 'console';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatchListComponent } from '../MatchStatistics/match-statistics.component';
import { MatchStatistics } from '../../core/Model/MatchStatisticsData/match-statistics';
import { MatchStatisticsService } from '../../core/Services/HomeService/match-statistics.service';

@Component({
  selector: 'app-scorecard',
  standalone: true,
  imports: [ScorecardComponent, RouterModule, CommonModule, RouterLink, RouterLinkActive, MatchListComponent],
  templateUrl: './scorecard.component.html',
  styleUrl: './scorecard.component.css'
})
export class ScorecardComponent implements OnInit {
  public scorecard!: Scorecard;  

  matchNumber!: number;

  season!: number;

  constructor(private scorecardservice : ScorecardService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.matchNumber = params['matchNumber'];
      this.season = params['season'];
      // Now you can use this.matchNumber to fetch corresponding match statistics
      this.getMatchScorecard(this.matchNumber, this.season);
    });
  }

  getMatchScorecard(matchNumber: number, season: number): void {
    this.scorecardservice.getScorecard(matchNumber, season)
    .subscribe(response => { 
        this.scorecard = response; 
    })
  }
}

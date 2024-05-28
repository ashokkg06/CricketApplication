import { Component, OnInit } from '@angular/core';
import { MatchStatistics } from '../../core/Model/MatchStatisticsData/match-statistics';
import { MatchStatisticsService } from '../../core/Services/HomeService/match-statistics.service';
import { CommonModule } from '@angular/common';
import { PageNotFoundComponent } from '../PageNotFound/page-not-found.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-match-statistics',
  standalone: true,
  imports: [MatchListComponent, FormsModule, CommonModule, RouterOutlet, RouterLinkActive, RouterLink, PageNotFoundComponent],
  templateUrl: './match-statistics.component.html',
  styleUrl: './match-statistics.component.css'
})
export class MatchListComponent implements OnInit {
  public matchStatistics!: MatchStatistics[];
  public season!: number;
  public seasons!: string[];

  constructor(private matchStatisticsService: MatchStatisticsService, private route: ActivatedRoute, private router: Router) { }  

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.season = params["season"];
      if (typeof localStorage !== 'undefined') {
        localStorage.setItem("season", this.season + '');
      }
      this.fetchMatchStatistics(this.season);
      this.fetchSeasons();
    })
  }

  fetchMatchStatistics(season: number): void {
    this.matchStatisticsService.getMatchStatistics(season)
      .subscribe(data => {
        this.matchStatistics = data;
      });
  }

  fetchSeasons(): void {
    this.matchStatisticsService.getSeasons().subscribe(data => {
      this.seasons = data;
      console.log(this.seasons);
    })
  }

  searchMatches(key: string):void {
    const results: MatchStatistics[] = [];
    for(const matches of this.matchStatistics) {      
      if((matches.date.toLowerCase().includes(key.toLowerCase()))
        || (matches.location.toLowerCase().includes(key.toLowerCase()))
        || (matches.shortTeam1.toLowerCase().includes(key.toLowerCase()))
        || (matches.shortTeam2.toLowerCase().includes(key.toLowerCase()))
        || (matches.team1.toLowerCase().includes(key.toLowerCase()))
        || (matches.team2.toLowerCase().includes(key.toLowerCase()))
        || (matches.winner.toLowerCase().includes(key.toLowerCase()))
        || ((""+matches.matchNumber).toLowerCase().includes(key.toLowerCase()))) {
          results.push(matches);
        }
    }
    this.matchStatistics = results;
    if(results.length === 0 || !key) {
      this.fetchMatchStatistics(this.season);
    }
  }

  onNavigate(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedValue = target.value;
    if (selectedValue) {
      this.router.navigate([selectedValue]);
    }
  }
}
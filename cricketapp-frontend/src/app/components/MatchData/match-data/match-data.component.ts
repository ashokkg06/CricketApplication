import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatchDataService } from '../../../core/Services/MatchesService/match-data.service';
import { MatchDataDto } from '../../../core/Model/MatchInfo/match-data-dto';

@Component({
  selector: 'app-match-data',
  standalone: true,
  imports: [MatchDataComponent, RouterOutlet,CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './match-data.component.html',
  styleUrl: './match-data.component.css'
})
export class MatchDataComponent implements OnInit {
  public matchData!: MatchDataDto;
  public matchNumber!: number;
  public season!: number;

  constructor(private matchDataService: MatchDataService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.matchNumber = params["matchNumber"];
        this.season = params["season"];
        this.getMatchInfo(this.matchNumber, this.season);
      })
  }

  getMatchInfo(matchNumber: number, season: number): void {
    this.matchDataService.getMatchInfo(matchNumber, season).subscribe(response => {
      this.matchData = response;
    })
  }
}

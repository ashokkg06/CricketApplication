import { Component, OnInit } from '@angular/core';
import { PlayerStats } from '../../../core/Model/PlayerStats/player-stats';
import { PlayerStatsService } from '../../../core/Services/MatchesService/player-stats.service';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { response } from 'express';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-player-stats',
  standalone: true,
  imports: [PlayerStatsComponent, RouterOutlet, CommonModule, RouterLinkActive, RouterLink],
  templateUrl: './player-stats.component.html',
  styleUrl: './player-stats.component.css'
})
export class PlayerStatsComponent implements OnInit {
  playerStats!: PlayerStats;
  name!: string;
  season!: number;

  constructor(private PlayerStatsService: PlayerStatsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.name = params["name"];
        this.season = params["season"];
        this.getPlayerStats(this.name, this.season);
      })
  }

  getPlayerStats(name: string, season: number): void {
    this.PlayerStatsService.getPlayerStats(name, season).subscribe(response => {
      this.playerStats = response;
    })
  }
}

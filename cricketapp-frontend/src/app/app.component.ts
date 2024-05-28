import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatchListComponent } from './components/MatchStatistics/match-statistics.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { PageNotFoundComponent } from './components/PageNotFound/page-not-found.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatchListComponent, RouterOutlet, PageNotFoundComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'cricketapp';
}

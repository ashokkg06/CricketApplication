import { HttpClientModule } from '@angular/common/http';
import { Routes } from '@angular/router';
import { MatchListComponent } from './components/MatchStatistics/match-statistics.component';
import { PageNotFoundComponent } from './components/PageNotFound/page-not-found.component';
import { ScorecardComponent } from './components/Scorecard/scorecard.component';
import { PointsTableComponent } from './components/PointsTable/points-table/points-table.component';
import { MatchSquadComponent } from './components/MatchSquad/match-squad/match-squad.component';
import { MatchDataComponent } from './components/MatchData/match-data/match-data.component';
import { MatchCommentsComponent } from './components/MatchComments/match-comments/match-comments.component';
import { PlayerStatsComponent } from './components/PlayerStats/player-stats/player-stats.component';
import { TeamsComponent } from './components/Teams/teams/teams.component';
import { TeamStatsComponent } from './components/TeamStats/team-stats/team-stats.component';
import { LoginComponent } from './components/LoginComponent/login/login.component';
import { SignupComponent } from './components/SignUpComponent/signup/signup.component';
import { AdminpanelComponent } from './components/AdminPanel/adminpanel/adminpanel.component';

export const routes: Routes = [
    {path:"", redirectTo:"matches", pathMatch:"full"},
    {path:"iplseries/:season/matches", component: MatchListComponent},
    {path: "iplseries/:season/matches/:matchNumber", loadComponent: () => ScorecardComponent},
    {path: "iplseries/:season/matches/:matchNumber/scorecard", loadComponent: () => ScorecardComponent},
    {path: "iplseries/:season/matches/:matchNumber/points-table", loadComponent: () => PointsTableComponent},
    {path: "iplseries/:season/matches/:matchNumber/squads", loadComponent: () => MatchSquadComponent},
    {path: "iplseries/:season/matches/:matchNumber/info", loadComponent: () => MatchDataComponent},
    {path: "iplseries/:season/matches/:matchNumber/commentary", loadComponent: () => MatchCommentsComponent},
    {path: "iplseries/:season/playerstats/:name", loadComponent: () => PlayerStatsComponent},
    {path: "iplseries/:season/points-table", component: PointsTableComponent},
    {path: "iplseries/:season/teams", component: TeamsComponent},
    {path: "iplseries/:season/teams/:teamName", component: TeamStatsComponent},
    {path: "admin/login", component: LoginComponent},
    {path: "admin/register", component: SignupComponent},
    {path: "admin-panel", component: AdminpanelComponent},
    {path: '**', component: PageNotFoundComponent}
];
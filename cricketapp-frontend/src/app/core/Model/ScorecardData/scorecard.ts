import { MatchStatistics } from '../MatchStatisticsData/match-statistics';
import { BatterMatchStats } from '../BatterMatchStatsData/batter-match-stats';
import { BowlerMatchStats } from '../BowlerMatchStatsData/bowler-match-stats';
import { ExtrasData } from '../ExtrasData/extras';
import { TotalData } from '../TotalData/totaldata';

export interface Scorecard {
  matchStatistics: MatchStatistics;
  batting: BatterMatchStats[][];
  bowling: BowlerMatchStats[][];
  extras: ExtrasData[];
  total: TotalData[];
}
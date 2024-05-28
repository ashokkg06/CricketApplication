import { MatchStatistics } from "../MatchStatisticsData/match-statistics";

export interface MatchSquad {
    matchStatistics: MatchStatistics;
    squads: string[][];
}
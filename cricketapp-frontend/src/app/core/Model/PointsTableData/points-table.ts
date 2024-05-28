import { MatchStatistics } from "../MatchStatisticsData/match-statistics";

export interface PointsTable {
    team: string;
    wins: number;
    lose: number;
    points: number;
    netRunRate: number;
}

import { MatchStatistics } from "../MatchStatisticsData/match-statistics";
import { PointsTable } from "./points-table";

export interface PointsTableDTO {
    matchStatistics : MatchStatistics;
    pointsTableData: PointsTable[];
}
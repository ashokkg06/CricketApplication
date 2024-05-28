import { MatchStatistics } from "../MatchStatisticsData/match-statistics";
import { MatchData } from "./match-data";

export interface MatchDataDto {
    matchStatistics: MatchStatistics;
    matchData: MatchData;
}

import { MatchStatistics } from "../MatchStatisticsData/match-statistics";
import { MatchComments } from "./match-comments";

export interface MatchCommentsDto {
    matchStatistics: MatchStatistics;
    matchComments: MatchComments[][];
}
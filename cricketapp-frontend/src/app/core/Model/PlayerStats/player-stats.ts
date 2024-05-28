import { PlayerBattingStats } from "./player-batting-stats";
import { PlayerBowlingStats } from "./player-bowling-stats";

export interface PlayerStats {
    playerBattingStats: PlayerBattingStats;
    playerBowlingStats: PlayerBowlingStats;
}

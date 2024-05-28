export interface MatchStatistics {
    date: string;
    season: number;
    matchNumber: number;
    venue: string;
    location: string;
    team1: string;
    team2: string;
    winner: string;
    team1Score: number;
    team2Score: number;
    team1Overs: number;
    team2Overs: number;
    team1Wickets: number;
    team2Wickets: number;
    winnerRuns: number;
    winnerWickets: number;
    shortTeam2: string;
    shortTeam1: string;
}

export function getShortTeam1(team1: string) : string {
    return getShort(team1);
}

export function getShortTeam2(team2: string): string {
    return getShort(team2);
}

export function getShort(team: string): string {
    switch (team.toLowerCase()) {
        case "gujarat titans":
            return "GT";
        case "lucknow super giants":
            return "LSG";
        case "chennai super kings":
            return "CSK";
        case "mumbai indians":
            return "MI";
        case "rajasthan royals":
            return "RR";
        case "punjab kings":
            return "PBKS";
        case "kolkata knight riders":
            return "KKR";
        case "delhi capitals":
            return "DC";
        case "sunrisers hyderabad":
            return "SRH";
        case "royal challengers bangalore":
            return "RCB";
        default:
            return "Unknown";
    }
}
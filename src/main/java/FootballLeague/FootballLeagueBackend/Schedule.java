package FootballLeague.FootballLeagueBackend;

import java.util.ArrayList;

import FootballLeague.LogHandler.LogHandler;

import static FootballLeague.FootballLeagueBackend.Match.writeMatch;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;

public class Schedule {

	public static LogHandler log = new LogHandler("FootballLeague.FootballLeagueBackend.Schedule");

	String scheduleCode;
	ArrayList<String> teamCodes;
	ArrayList<Match> matches;
	int numberOfTeams;
	int gameWeeks;

	// Empty constructor
	Schedule(){}

	// Construct from league string
	public Schedule(String league) {
		matches = new ArrayList<>();
		teamCodes = new ArrayList<>();
		for(Team team : readAllTeams("WHERE league ='" + league + "';")){
			teamCodes.add(team.getTeamCode());
		}
		this.numberOfTeams = teamCodes.size();
		// Each team needs to play every other team, twice
		this.gameWeeks = (numberOfTeams - 1);
	}

	Schedule(ArrayList<String> teamCodes) {
		this.teamCodes = teamCodes;
		this.numberOfTeams = teamCodes.size();
		// Each team needs to play every other team, twice
		this.gameWeeks = numberOfTeams - 1;
		this.matches = new ArrayList<Match>();
	}

	public void createSchedule() {
		log.log("Begin schedule creation.");
		int subTeamsLength = numberOfTeams / 2;
		ArrayList<String> subTeamCodesA = new ArrayList<>(teamCodes.subList(0, subTeamsLength));
		ArrayList<String> subTeamCodesB = new ArrayList<>(teamCodes.subList(subTeamsLength, numberOfTeams));

		for (int gameWeek=1; gameWeek<gameWeeks+1; gameWeek++) {

			ArrayList<String> subTeamCodesC = new ArrayList<String>();
			ArrayList<String> subTeamCodesD = new ArrayList<String>();

			subTeamCodesC.addAll(subTeamCodesA);
			subTeamCodesD.addAll(subTeamCodesB);
			
			subTeamCodesC.set(1, subTeamCodesB.get(0));

			for (int i=2; i<subTeamsLength; i++) {
				subTeamCodesC.set(i, subTeamCodesA.get(i-1));
			}

			subTeamCodesD.set(subTeamsLength-1, subTeamCodesA.get(subTeamsLength-1));

			for (int i=0; i<subTeamsLength-1; i++) {
				subTeamCodesD.set(i, subTeamCodesB.get(i+1));
			}

			subTeamCodesA = subTeamCodesC;
			subTeamCodesB = subTeamCodesD;

			for (int m=0; m<subTeamsLength; m++) {
				String gameWeekString = Integer.toString(gameWeek);
				String gameWeekStringReverse = Integer.toString(gameWeeks+gameWeek);
				Match match = new Match(subTeamCodesA.get(m), subTeamCodesB.get(m), gameWeekString);
				Match matchReverse = new Match(subTeamCodesB.get(m), subTeamCodesA.get(m), gameWeekStringReverse);

				matches.add(match);
				matches.add(matchReverse);
			}

		}
	}

	public void printMatches() {
		matches.forEach(match -> log.log(match.getMatchCode()));
	}

	public void writeMatches(){
		for(Match match : matches){
			writeMatch(match);
		}
	}
}
package FootballLeagueBackend;
import java.util.ArrayList;
public class Schedule {
	String scheduleCode;
	ArrayList<String> teamCodes;
	ArrayList<Match> matches;
	int numberOfTeams;
	int gameWeeks;

	// Empty constructor
	Schedule(){}

	// Construct from league string
	Schedule(String league) {
		this.teamCodes = Database.readTeamsFromLeague(league);
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

	// TODO add dummy team in case of odd number of teams
	public void createSchedule() {
		System.out.println("Begin schedule creation.");
		// ArrayList<ArrayList> schedule = new ArrayList<ArrayList>();
		int subTeamsLength = numberOfTeams / 2;
		ArrayList<String> subTeamCodesA = new ArrayList<String>(teamCodes.subList(0, subTeamsLength));
		ArrayList<String> subTeamCodesB = new ArrayList<String>(teamCodes.subList(subTeamsLength, numberOfTeams));

		for (int gameWeek=1; gameWeek<gameWeeks+1; gameWeek++) {

			System.out.println(subTeamCodesA);
			System.out.println(subTeamCodesB);
			System.out.println("---------");

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
				Match match = new Match(subTeamCodesA.get(m)+subTeamCodesB.get(m)+gameWeekString, subTeamCodesA.get(m), subTeamCodesB.get(m), gameWeekString);
				Match matchReverse = new Match(subTeamCodesB.get(m)+subTeamCodesA.get(m)+gameWeekStringReverse, subTeamCodesB.get(m), subTeamCodesA.get(m), gameWeekStringReverse);
				matches.add(match);
				matches.add(matchReverse);
			}

		}
	}

	// TODO can use this technique to write to database
	public void printMatches() {
		matches.forEach(match -> System.out.println(match.getMatchCode()));
	}

	public static void main(String[] args) {
    }
}
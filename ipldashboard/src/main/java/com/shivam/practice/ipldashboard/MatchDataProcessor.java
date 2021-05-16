package com.shivam.practice.ipldashboard;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {

		Match match = new Match();
		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		match.setWinner(matchInput.getWinner());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(matchInput.getDate(), formatter);
		match.setDate(date);
		String firstInningsTeam, secondInningsTeam;
		if ("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			secondInningsTeam = firstInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		} else {
			secondInningsTeam = matchInput.getToss_winner();
			firstInningsTeam = secondInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();

		}
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		match.setTossWinner(matchInput.getToss_winner());
		match.setResult(matchInput.getResult());
		match.setTossDecision(matchInput.getToss_decision());
		match.setResultMargin(matchInput.getResult_margin());
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		match.setVenue(matchInput.getVenue());
		return match;
	}

}

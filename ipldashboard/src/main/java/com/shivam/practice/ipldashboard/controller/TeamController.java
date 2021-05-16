package com.shivam.practice.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shivam.practice.ipldashboard.Match;
import com.shivam.practice.ipldashboard.Team;
import com.shivam.practice.ipldashboard.repo.MatchRepo;
import com.shivam.practice.ipldashboard.repo.TeamRepo;

@RestController
@CrossOrigin
public class TeamController {

	@Autowired
	private TeamRepo teamRepo;
	@Autowired
	private MatchRepo matchRepo;

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable final String teamName) {
		Team team = teamRepo.findByTeamName(teamName);
		team.setMatches(matchRepo.findLatestMatchesByTeam(teamName, 4));
		return team;
	}
	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam (@PathVariable final String teamName,@RequestParam int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year+1, 1, 1);
		return matchRepo.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
	}
}

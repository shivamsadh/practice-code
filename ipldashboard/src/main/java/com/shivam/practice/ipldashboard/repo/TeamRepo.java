package com.shivam.practice.ipldashboard.repo;

import org.springframework.data.repository.CrudRepository;

import com.shivam.practice.ipldashboard.Team;


public interface TeamRepo extends CrudRepository<Team, Long>{

	public Team findByTeamName(String teamName);
}

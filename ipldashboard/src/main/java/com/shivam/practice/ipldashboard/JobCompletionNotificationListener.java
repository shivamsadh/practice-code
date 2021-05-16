package com.shivam.practice.ipldashboard;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final EntityManager em;

	@Autowired
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			Map<String, Team> teamData = new HashMap<>();
			em.createQuery("select m.team1, count(*) from Match m group by m.team1 ", Object[].class).getResultList()
					.stream().map(e -> new Team((String) e[0], (long) e[1]))
					.forEach(t -> teamData.put(t.getTeamName(), t));

			em.createQuery("select m.team2, count(*) from Match m group by m.team2 ", Object[].class).getResultList()
					.stream().forEach(t -> {
						Team tm = teamData.get((String) t[0]);
						tm.setTotalMatches(tm.getTotalMatches() + (long) t[1]);
					});

			em.createQuery("select m.winner, count(*) from Match m group by m.winner ", Object[].class)
					.getResultList().stream().forEach(t -> {
						Team tm = teamData.get((String) t[0]);
						if(tm!=null)tm.setTotalWins((long) t[1]);
					});
			teamData.values().forEach(t -> em.persist(t));
			teamData.values().forEach(t->System.out.println(t.toString()));
		}
	}
}

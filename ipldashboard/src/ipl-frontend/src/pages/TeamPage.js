import  { React,useEffect,useState } from 'react';
import { useParams } from 'react-router-dom';
import {MatchDetailsCard} from '../components/MatchDetailsCard.js';
import {MatchSmallCard} from '../components/MatchSmallCard.js';
import './TeamPage.scss';
import { PieChart } from 'react-minimal-pie-chart';
import { Link } from 'react-router-dom';
export const TeamPage = () => {
	
	const[team,setTeam] = useState({matches:[]});
	const { teamName } = useParams();
	useEffect(
				() => {
				const fetchMatches = async () => {
					const response = await fetch(`http://localhost:8080/team/${teamName}`);
					const data = await response.json();
					setTeam(data);
				};
				fetchMatches();
				
				},[teamName]
	);
	

	if(!team||!team.teamName){
		return <h1>Team Not Found</h1>;
	}
  return (
    <div className="TeamPage">
    <div className="team-name-section">
    <div className="team-name">
    	<h1>{team.teamName}</h1>
    </div>	
    
    </div>
    <div className="win-loss-section">
    	Wins / Losses
    	<PieChart
    	  data={[
    	    { title: 'Losses', value: team.totalMatches - team.totalWins, color: '#CD5C5C' },
    	    { title: 'Wins', value: team.totalWins, color: '#8FBC8F' },
    	  ]}
    	/>
    </div>
     
    <div className="match-detail-section">
    	<h3>Latest Matches</h3>
     <MatchDetailsCard  teamName={team.teamName} match={team.matches[0]}/>
    </div>
         { team.matches.slice(1).map(match => < MatchSmallCard teamName={team.teamName}  match={match} /> ) }
     <div className="more-link">
         <Link
			to={`/teams/${teamName}/matches/${process.env.REACT_APP_DATA_END_YEAR}`}>More ></Link>
			
     </div>
     
    </div>
  );
}


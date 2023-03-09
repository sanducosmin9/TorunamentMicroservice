package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.exceptions.TeamNotFoundException;
import tournament.model.Team;
import tournament.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;

    @Override
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " was not found"));
    }
}

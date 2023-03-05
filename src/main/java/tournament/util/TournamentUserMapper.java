package tournament.util;

import tournament.dto.TournamentUserDto;
import tournament.model.TournamentUser;

import java.util.ArrayList;

public class TournamentUserMapper {
    public static  TournamentUserDto mapToTournamentUserDto(TournamentUser tournamentUser) {
        return TournamentUserDto.builder()
                .id(tournamentUser.getId())
                .username(tournamentUser.getUsername())
                .password(tournamentUser.getPassword())
                .role(tournamentUser.getRole())
                .tournaments(tournamentUser.getTournaments())
                .build();
    }

    public static TournamentUser mapToTournamentUser(TournamentUserDto tournamentUserDto) {
        return new TournamentUser(
                tournamentUserDto.getId(),
                tournamentUserDto.getUsername(),
                tournamentUserDto.getPassword(),
                tournamentUserDto.getTournaments(),
                tournamentUserDto.getRole(),
                new ArrayList<>()
        );
    }
}

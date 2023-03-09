package tournament.exceptions;

public class MatchupNotFoundException extends RuntimeException{
    public MatchupNotFoundException(String message) {
        super(message);
    }
}

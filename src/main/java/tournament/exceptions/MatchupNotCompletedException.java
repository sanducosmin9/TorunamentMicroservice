package tournament.exceptions;

public class MatchupNotCompletedException extends RuntimeException{
    public MatchupNotCompletedException(String message) {
        super(message);
    }
}

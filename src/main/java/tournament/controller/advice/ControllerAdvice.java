package tournament.controller.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tournament.exceptions.MatchupNotCompletedException;
import tournament.exceptions.MatchupNotFoundException;
import tournament.exceptions.TeamNotFoundException;
import tournament.exceptions.TournamentNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({TournamentNotFoundException.class, MatchupNotFoundException.class, TeamNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(TournamentNotFoundException ex) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(MatchupNotCompletedException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    public ErrorMessage notCompletedException(MatchupNotCompletedException ex) {
        return new ErrorMessage(
                HttpStatus.PRECONDITION_FAILED.toString(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidFormatException(ConstraintViolationException ex) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.toString(),
                ex.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessageTemplate)
                        .toList()
                        .toString()
        );
    }

}

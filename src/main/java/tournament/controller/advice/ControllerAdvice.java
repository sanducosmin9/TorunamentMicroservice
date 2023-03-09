package tournament.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tournament.exceptions.TournamentNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(TournamentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage tournamentNotFoundException(TournamentNotFoundException ex) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage()
        );
    }

}

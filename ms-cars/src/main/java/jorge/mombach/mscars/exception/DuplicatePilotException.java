package jorge.mombach.mscars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatePilotException extends RuntimeException {
    public DuplicatePilotException(String message) {
        super(message);
    }
}

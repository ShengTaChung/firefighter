package api.exceptions;

public class NoFirefighterFoundException extends Exception {

    public NoFirefighterFoundException() {
        super("No firefighter found.");
    }

}

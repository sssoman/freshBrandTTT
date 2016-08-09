package datamodel;

public enum SlackErrors {
	BAD_REQUEST("Bad Request!"), 
	GAME_IN_PROG("A current game of tic tac toe is already in progress. Cannot start a new game!"),
	NOT_TURN("Cannot make a move. It is not your turn!"),
	ILLEGAL_POS("Illegal position specified!"),
	BAD_COMMAND("Unrecognized command!"),
	BAD_TOKEN("Invalid Token!"),
	/*
	 * Exception error messages
	 */
	OUT_OF_BOUNDS("Board position out of bounds!"),
	ALEADY_FILLED("Board position already filled!");
	
	private String value;

    private SlackErrors(String value) {
        this.value = value;
    }
    
    public String getValue(){
    	return this.value;
    }
}

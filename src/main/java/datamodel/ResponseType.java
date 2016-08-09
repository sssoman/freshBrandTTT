package datamodel;

public enum ResponseType {
	EPHEMERAL("ephemeral"), 
	IN_CHANNEL("in_channel");
	
	private String value;

    private ResponseType(String value) {
        this.value = value;
    }
    
    public String getValue(){
    	return this.value;
    }
}

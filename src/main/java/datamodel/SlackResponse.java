package datamodel;

/**
 * Representation of the Slack Response
 */
public class SlackResponse {

    private String responseType;
    private String text;

    public SlackResponse(String text, String responseType) {
        this.text = text;
        this.responseType = responseType;
    }

    public String getText() {
        return text;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setText(String text) {
        this.text = text;
    }
}

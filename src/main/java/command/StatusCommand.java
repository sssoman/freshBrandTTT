package command;
import java.util.Map;
import java.util.Set;

import datamodel.ResponseType;
import datamodel.SlackErrors;
import datamodel.SlackRequest;
import datamodel.SlackResponse;
import game.TTT;

public class StatusCommand implements Command{

	public SlackResponse invoke(SlackRequest slackRequest, Map<String, TTT> channelGames, Set<String> slackUsers) {
		if (slackRequest == null) {
			return new SlackResponse(SlackErrors.BAD_REQUEST.getValue(),
					ResponseType.EPHEMERAL.getValue());
		}
		if (!channelGames.containsKey(slackRequest.getChannelId())) {
            return new SlackResponse("No tic tac toe game currently in progress!", ResponseType.IN_CHANNEL.getValue());
        }
        TTT ttt = channelGames.get(slackRequest.getChannelId());
        StringBuilder sb = new StringBuilder();
        sb.append(ttt.displayBoard());
        sb.append("\n");
        sb.append(ttt.getWhoseTurnToPlay());
        sb.append("'s turn.");
        return new SlackResponse(sb.toString(), ResponseType.IN_CHANNEL.getValue());
    }
}
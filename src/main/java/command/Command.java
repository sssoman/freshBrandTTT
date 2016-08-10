package command;
import java.util.Map;
import java.util.Set;

import datamodel.SlackRequest;
import datamodel.SlackResponse;
import game.TTT;

public interface Command {
	public static final String SEPARATOR = " ";
	public static final Integer BOARD_SIZE = 3;

        /**
	 * 
	 * @param slackRequest
	 * @param channelGames
	 * @param slackUsers
	 * @return The response after the command is invoked
	 */
	public SlackResponse invoke(SlackRequest slackRequest,
			Map<String, TTT> channelGames, Set<String> slackUsers);
}

package command;
import java.util.Map;
import java.util.Set;

import datamodel.SlackRequest;
import datamodel.SlackResponse;
import datamodel.TTT;

public interface Command {
	public static final String SEPARATOR = " ";
	public static final Integer BOARD_SIZE = 3;

	public SlackResponse invoke(SlackRequest slackRequest,
			Map<String, TTT> channelGames, Set<String> slackUsers);
}

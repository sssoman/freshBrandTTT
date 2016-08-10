package command;
import java.util.Map;
import java.util.Set;

import datamodel.ResponseType;
import datamodel.SlackErrors;
import datamodel.SlackRequest;
import datamodel.SlackResponse;
import game.TTT;

public class StartCommand implements Command {
	public SlackResponse invoke(SlackRequest slackRequest,
			Map<String, TTT> channelGames, Set<String> slackUsers) {
		if (slackRequest == null) {
			return new SlackResponse(SlackErrors.BAD_REQUEST.getValue(),
					ResponseType.EPHEMERAL.getValue());
		} else {
			final String[] tokens = slackRequest.getText().split(SEPARATOR);
			if (tokens == null || tokens.length < 2) {
				return new SlackResponse(SlackErrors.BAD_REQUEST.getValue(),
						ResponseType.EPHEMERAL.getValue());
			} else {
				if (channelGames.containsKey(slackRequest.getChannelId())) {
					return new SlackResponse(
							SlackErrors.GAME_IN_PROG.getValue(),
							ResponseType.EPHEMERAL.getValue());
				}
				final String opponent = tokens[1];
				if (slackUsers != null && !slackUsers.isEmpty() && !slackUsers.contains(opponent)) {
					/* For this use case only users with access can issue this command.
					 * Hence we could say invalid user error here but in case this is a 
					 * phishing attempt just show a generic error. 
					 */
					return new SlackResponse(
							SlackErrors.BAD_REQUEST.getValue(),
							ResponseType.EPHEMERAL.getValue());
				} else {
					TTT ttt = new TTT(BOARD_SIZE, slackRequest.getUserName(),
							opponent);
					channelGames.putIfAbsent(slackRequest.getChannelId(), ttt);

					return new SlackResponse(
							"New tic tac toe game initiated! Player 1 : "
									+ ttt.getPlayer1() + " Player 2 : "
									+ ttt.getPlayer2() + "\n"
									+ slackRequest.getUserName() + "'s turn",
							ResponseType.IN_CHANNEL.getValue());
				}
			}
		}
	}
}

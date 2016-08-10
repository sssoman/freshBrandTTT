package command;
import java.util.Map;
import java.util.Set;

import datamodel.ResponseType;
import datamodel.SlackErrors;
import datamodel.SlackRequest;
import datamodel.SlackResponse;
import game.TTT;
import datamodel.TTTStatus;

public class MoveCommand implements Command {

	public SlackResponse invoke(SlackRequest slackRequest,
			Map<String, TTT> channelGames, Set<String> slackUsers) {
		if (slackRequest == null) {
			return new SlackResponse(SlackErrors.BAD_REQUEST.getValue(),
					ResponseType.EPHEMERAL.getValue());
		} else {
			final String[] tokens = slackRequest.getText().split(SEPARATOR);
			if (tokens == null || tokens.length < 3) {
				return new SlackResponse(SlackErrors.BAD_REQUEST.getValue(),
						ResponseType.EPHEMERAL.getValue());
			} else {
				String channelId = slackRequest.getChannelId();
				/*
				 * Do not want to disclose unnecessary information in case it is
				 * a malicious request Hence show generic error
				 */
				if (channelId == null || !channelGames.containsKey(channelId)) {
					return new SlackResponse(
							SlackErrors.BAD_REQUEST.getValue(),
							ResponseType.EPHEMERAL.getValue());
				} else {

					TTT ttt = channelGames.get(channelId);
					if (!isTurn(slackRequest, ttt)) {
						return new SlackResponse(
								SlackErrors.NOT_TURN.getValue(),
								ResponseType.EPHEMERAL.getValue());
					}
					int row, col;

					try {
						row = Integer.valueOf(tokens[1]);
						col = Integer.valueOf(tokens[2]);
					} catch (NumberFormatException e) {
						return new SlackResponse(
								SlackErrors.ILLEGAL_POS.getValue(),
								ResponseType.EPHEMERAL.getValue());
					}

					try {
						ttt.move(row, col);
						String endingMessage = null;
						if (ttt.getTTTStatus() == TTTStatus.PLAYER1_WON
								|| ttt.getTTTStatus() == TTTStatus.PLAYER2_WON) {
							endingMessage = "Game ends! "
									+ ttt.getCurrentPlayer() + " won!" + "\n"
									+ ttt.displayBoard();
						} else if (ttt.getTTTStatus() == TTTStatus.DRAW) {
							endingMessage = "Game ends! It is a draw! " + "\n"
									+ ttt.displayBoard();
						}
						if (endingMessage != null) {
							tearDown(slackRequest, channelGames);
							return new SlackResponse(endingMessage,
									ResponseType.IN_CHANNEL.getValue());

						}
					} catch (IllegalArgumentException e) {
						return new SlackResponse(
								e.getMessage(),
								ResponseType.EPHEMERAL.getValue());
					}
					StringBuilder sb = new StringBuilder();
					sb.append(slackRequest.getUserName() + " made a move at "
							+ row + " " + col + "\n");
					sb.append("Board after move is : \n"
							+ ttt.displayBoard() + "\n");
					sb.append("It is now " + ttt.getWhoseTurnToPlay()
							+ "'s turn.");
					return new SlackResponse(sb.toString(),
							ResponseType.IN_CHANNEL.getValue());
				}
			}
		}
	}

        /**
	 * Check if it is player's turn
	 * @param slackRequest
	 * @param ttt
	 * @return If it is player's turn
	 */
	private boolean isTurn(SlackRequest slackRequest, TTT ttt) {
		boolean isTurn = true;
		if (!ttt.getCurrentPlayer().equals(slackRequest.getUserName())) {
			isTurn = false;
		}
		return isTurn;
	}

	public void tearDown(SlackRequest slackRequest,
			Map<String, TTT> channelGames) {
		channelGames.remove(slackRequest.getChannelId());
	}
}

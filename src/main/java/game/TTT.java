package game;

import datamodel.SlackErrors;
import datamodel.TTTStatus;

public class TTT {
	// X = 1 (player 1), 0 = 0 (player 2), -1 (empty)
    private final int[][] board;
    private final int rowColSize = 3;
    private String player1; 
    private String player2;
    private int symbol;                     
    private int currSize; 
    private String cP;  
    private TTTStatus tttStatus = TTTStatus.IN_PROGRESS;

    public TTT(final int boardSize, String player1, final String player2) {
        board = new int[boardSize][boardSize];
        this.player1 = player1;
        this.player2 = player2;
    	this.symbol = 1;
        this.cP = player1;
        for (int row = 0; row < rowColSize; row ++){
            for (int col = 0; col < rowColSize; col++){
            	board[row][col] = -1;
            }
        }
    }
    
    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getCurrSize() {
        return currSize;
    }

    public String getCurrentPlayer() {
        return cP;
    }

    public TTTStatus getTTTStatus() {
        return tttStatus;
    }
    
    public int[][] getBoard() {
        return board;
    }

    /**
     * Checks if game has been won
     * @param symbol
     * @return True if game has been won , false otherwise
     */
    public boolean hasWon(int symbol) {
        return checkRows(symbol) || checkCols(symbol) || checkDiag(symbol);
    }

    private boolean checkRows(int symbol) {
    	boolean isRow = false;
        for(int i = 0; i < rowColSize; i++){
        	if(checkRow(i, symbol)){
        		isRow = true;
        		break;
        	}
        }
        return isRow;
    }
    
    private boolean checkRow(int row, int symbol) {
        int col = 0;
        for (int i = 0; i < rowColSize; i++) {
          if (board[row][ i] == symbol) {
            col++;
          }
        }
        return col == rowColSize;
      }

    private boolean checkCols(int symbol) {
    	boolean isCol = false;
        for (int i = 0; i < rowColSize ; i++) {
        	if(checkCol(i, symbol)){
        		isCol = true;
        		break;
        	}
        }
        return isCol;
    }
    
    private boolean checkCol(int col, int symbol) {
        int row = 0;
        for (int i = 0; i < rowColSize; i++) {
          if (board[i][col] == symbol) {
            row++;
          }
        }
        return row == rowColSize;
      }
    
    public boolean checkDiag(int symbol) {
        int diag1 = 0;
        int diag2 = 0;
        
        for (int i = 0; i < rowColSize; i++) {
          if (board[i][i] == symbol) {
            diag1++;
          }
          if (board[i][rowColSize -1 -i] == symbol) {
            diag2 ++;
          }
        }
        return diag1 == rowColSize || diag2 == rowColSize;
      }

    /**
     * Makes a move at board[row][col]
     * @param row
     * @param col
     * @throws IllegalArgumentException
     */
    public void move(final int row, final int col) throws IllegalArgumentException {
		if (row < 0 || row > rowColSize - 1 || col < 0 || col > rowColSize - 1) {
			throw new IllegalArgumentException(
					SlackErrors.OUT_OF_BOUNDS.getValue());
		} else if (board[row][col] != -1) {
			throw new IllegalArgumentException(
					SlackErrors.ALEADY_FILLED.getValue());
		} else {
			board[row][col] = symbol;
			currSize++;

			if (hasWon(symbol)) {
				tttStatus = cP.equals(player1) ? TTTStatus.PLAYER1_WON
						: TTTStatus.PLAYER2_WON;
				return;
			}

			else if (currSize == rowColSize * rowColSize) {
				tttStatus = TTTStatus.DRAW;
				return;
			}
			cP = cP.equals(player1) ? player2 : player1;
			symbol ^= 1;
		}
    }
 
    /**
     * Displays the current state of the board
     * @return Current state of the board
     */
    public String displayBoard() {   	
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------------\n");
        for (int row = 0; row < rowColSize; row++) {
        	sb.append("|");
            for (int col = 0; col < rowColSize; col++) {
                switch (board[row][col]) {
                    case 1:
                        sb.append(" X ");
                        break;
                    case 0:
                        sb.append(" O ");
                        break;
                    case -1:
                        sb.append(" # ");
                        break;
                    default:
                        break;
                }
                if (col < rowColSize) {
                    sb.append("|");
                }
            }
            if (row < rowColSize){
                sb.append("\n-------------\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Gets whose turn it is to play
     * @return Player whose turn it is to play
     */
    public String getWhoseTurnToPlay(){
        StringBuilder sb = new StringBuilder();
        if (this.symbol == 1){
            sb.append(player1);
        }
        else if(this.symbol == 0){
            sb.append(player2);
        }
        return sb.toString();
    }

}
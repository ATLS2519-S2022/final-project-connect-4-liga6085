
public class GreedyPlayer {
	int colNum;
	int id;
	public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException{ //is there space to make a move
		if (board.isFull()) {
			throw new Error ("Board full.");
		}
		int[] scoreOpp = new int[7]; //holds a score to check for the best option
		int max = -1000; //considered the highest value for sorting an array
		int colOpp = 0;
		for (int i = 0;i < colNum; i++) { //goes through each column and row to check
			if (board.isValidMove(i)) {
				board.move(i,  3-id);
				scoreOpp[i] = calcScore(board, 3-id);
				board.unmove(i,  3-id);
				if(scoreOpp[i] > max) {
					max = scoreOpp[i];
					colOpp = i; //saving column location
				}
			}
		}
		arb.setMove(colOpp);
	}
	private int calcScore(Connect4Board board, int i) {
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Looking for horizontal connections
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Looking for vertical connections
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Looking for diagonal connections
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
	
	
}

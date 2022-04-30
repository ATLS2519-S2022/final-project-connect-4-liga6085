
public class GreedyPlayer {

	public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException{
		if (board.isFull()) {
			throw new Error ("Board full.");
		}
		int[] scoreOpp = new int[7];
		int max = -1000;
		int colOpp = 0;
		for (int i = 0;i < cols; i++) {
			if (board.isValidMove(i)) {
				board.move(i,  3-id);
				scoreOpp[i] = calcScore(board, 3-id);
				board.unmove(i,  3-id);
				if(scoreOpp[i] > max) {
					max = scoreOpp[i];
					colOpp = i;
				}
			}
		}
		arb.setMove(colOpp);
	}
	
	
}


public class MiniMax implements Player{

	
	int id;
	int colNum;
	int oppId;
	
	public String name() {
		return "Mini Max";
	}
	
	public void init(int id, int msecPerMove, int rows, int cols) {
		this.id = id;
		this.oppId = 3-id;
		this.colNum = cols;
	}

	@Override
	public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException {
		
		if (board.isFull()) {
			throw new Error ("Board full.");
		}
		
		int move = 0;
		int depth = 1;
		
		while (!arb.isTimeUp() && depth <= board.numEmptyCells()) {
			
			int topScore = -1000; //stores highest value
            
        	int [] keptScore = new int[7]; //array that stores temporary scores to eventually see which score is the highest
            
            int colTopScore = 0;
            
            
            // Find maximum score for all possible moves 
            
            for (int i = 0 ; i < colNum ; i++) { //Runs through each column and finds the highest score if there is a possible move

       
            	if (board.isValidMove(i)) { 
            		
    	        	board.move(i, id); 
    	     
    	        	keptScore[i] = minimax(board, depth - 1, false, arb);
    	            board.unmove(i, id);
                
                if (keptScore[i] > topScore) {
                	
                	topScore = keptScore[i];
    	            colTopScore = i; 
                
                }
		}
        }
            arb.setMove(colTopScore); 
            depth++;
	}
	
}

	private int minimax(Connect4Board board, int depth, boolean maximize, Arbitrator arb) {
		int topScore;
    	
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board, id) - calcScore(board, oppId);
    	}
    	
    	if(maximize) {
    		topScore = -1000;
    		for (int i = 0 ; i < colNum ; i++) {
    			if (board.isValidMove(i)) {
    			board.move(i, id);
    			topScore = Math.max(topScore, minimax(board, depth - 1, false, arb));
    			board.unmove(i, id);
    			}
    		}
    		 return topScore;
    	}
    		
    	else {
    		topScore = 1000;
    		for (int i = 0 ; i < colNum ; i++) {
    			if (board.isValidMove(i)) {
    			board.move(i, oppId);
    			topScore = Math.min(topScore, minimax(board, depth - 1, true, arb));
    			board.unmove(i, oppId);
    			
    			}
    			}
    			
    			return topScore;
    			
    		}
    	
	}

	private int calcScore(Connect4Board board, int id) {
		
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
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

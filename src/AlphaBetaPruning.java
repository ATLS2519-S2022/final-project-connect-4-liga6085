
public class AlphaBetaPruning implements Player{
	int id;
	int colNum;
	int oppId;
	int alpha; 
	int beta;
   public String name() { //player name
    	return "Albert";
    }
    public void init(int id, int msecPerMove, int rows, int cols) { //initialize player
    	this.id = id; //player id
    	this.oppId= 3-id;
    	this.colNum = cols;
    }
    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException { //checking next move if there is space
        	if(board.isFull()) { //checking to see if board is full
        		throw new Error ("Complaint: the board is full");
        	}
        	int move = 0; 
        	int depth = 1; 
        	while(!arb.isTimeUp() && depth <= board.numEmptyCells()) { //if there is time and space on the board
            	int topScore = -1000; //highest value
            	int [] keptScore = new int[7]; //keeps scores in temp array to check later
                int colTopScore = 0;
                for (int i = 0 ; i < colNum ; i++) { //going through all columns
                	if (board.isValidMove(i)) { 
        	        	board.move(i, id); 
        	        	keptScore[i] = alphabeta(board, depth - 1, alpha, beta, false, arb);
        	            board.unmove(i, id);
                    if (keptScore[i] > topScore) {
                    	topScore = keptScore[i];
        	            colTopScore = i;  //keeping col location at i
                    }
                  }
                }
                arb.setMove(colTopScore); 
                depth++;
        	}
        }
        public int alphabeta(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {
        	int bestScore; 
        	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
        		return calcScore(board, id) - calcScore(board, oppId);
        	}
        	if(isMaximizing) {
        		bestScore = -1000;
        		for (int i = 0 ; i < colNum ; i++) {
        			if (board.isValidMove(i)) {
    	    			board.move(i, id);
    	    			bestScore = Math.max(bestScore, alphabeta(board, depth - 1, alpha, beta, false, arb));
    	    			alpha = Math.max(alpha, bestScore);
    	    			if (alpha >= beta) {
    	    				break;	
    	    			}
    	    			board.unmove(i, id);
        			}
        		}
        		 return bestScore;
        	}
        	else {
        		bestScore = 1000;
        		for (int i = 0 ; i < colNum ; i++) {
        			if (board.isValidMove(i)) {
    	    			board.move(i, oppId);
    	    			bestScore = Math.min(bestScore, alphabeta(board, depth - 1, alpha, beta, true, arb));
    	    			beta = Math.min(beta, bestScore);
    	    			if (alpha >= beta) {
    	    				break;
    	    			}
    	    			board.unmove(i, oppId);
        			}
        		}
        			return bestScore;
        	}
        	
    	}
        public int calcScore(Connect4Board board, int id)
    	{
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

public class AlphaBetaPruning implements Player{

	int id;
	int colNum;
	int oppId;
	int alpha; 
	int beta;
	

   public String name() {
	   
    	return "Albert";
    }
  
   
    public void init(int id, int msecPerMove, int rows, int cols) {
    	
    	this.id = id;
    	this.oppId= 3-id;
    	this.colNum = cols;
    }

    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) 
    
    
            throws TimeUpException {
        
        	
        	if(board.isFull()) {
        		throw new Error ("Complaint: the board is full");
        	}
        	
        	
        	int move = 0; 
        	int depth = 1; 
        	
        	

        	
        	while(!arb.isTimeUp() && depth <= board.numEmptyCells()) {
        		
        		
        		
        		
            	int topScore = -1000; 
                
            	int [] keptScore = new int[7];
                
                int colTopScore = 0;
                
                
               
                
                for (int i = 0 ; i < colNum ; i++) { 

           
                	if (board.isValidMove(i)) { 
                		
        	        	board.move(i, id); 
        	     
        	        	keptScore[i] = alphabeta(board, depth - 1, alpha, beta, false, arb);
        	            board.unmove(i, id);
                    
                    if (keptScore[i] > topScore) {
                    	
                    	topScore = keptScore[i];
        	            colTopScore = i;  //storing column location in i
                    
                    }
                    
                  }
                	
                }
                
                arb.setMove(colTopScore); 
                depth++;
            	
        	}

        	
        }
        
        public int alphabeta(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {
      	
        	int bestScore; //stores highest value
        	
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
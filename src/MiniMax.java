
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
		// TODO Auto-generated method stub
		return 0;
	}
}

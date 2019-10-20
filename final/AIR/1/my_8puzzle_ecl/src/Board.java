
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Board{

	// N and board
	int N;
	int board[];
	
	// note: blocks[][]
	public Board(int blocks[][]){
	
		// initialize N  .. note: no () after length
		N = blocks[0].length;
		
		// delcare space
		board = new int[N*N];
		
		
		// initialize
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
				board[i*N+j] = blocks[i][j];
		}
	}
	/*public boolean equals(Object y)
    {
        if(y==this) return true;
        if(y==null) return false;
        
        if(y.getClass()!=this.getClass()) return false;
        
        Board that = (Board)y;
        return Arrays.equals(this.board,that.board);
    }*/
	
	public boolean isGoal(){
		
		for(int i=0;i<(N*N)-1;i++){ // note::: N*N -1 and not N*N
			if(board[i]!=i+1)           // note: just one condition
				return false;
		}
		return true;
	}
	
	
	public int manhattan(){
		
		int sum = 0;
		
		// note: i=0,i<N*N
		for(int i=0;i<N*N;i++){
			
			if(board[i]!=0 && board[i]!=i+1)
			{
				sum+=manhattan(board[i],i); // note: board[i],i
			}		
		}
		return sum;
	}
	
	public int manhattan(int goal, int current){
		
		// note: goal-1 and only current, not current-1
		// also note: subtraction in between and not addition
		int row = Math.abs((goal - 1)/N - current/N);
		int col = Math.abs((goal - 1)%N - current%N);
		
		return row+col;
	}
	
	// note::: for next function, we need this constructor
	public Board(int board[]){
		
		N = (int) Math.sqrt(board.length);
		this.board = new int[board.length];        // IMP
		
		for(int i=0;i<board.length;i++){          // note::: not N
			this.board[i] = board[i];
		}
	}
	
	// note::: neighbours iterator
	public Iterable<Board> neighbours(){
		
		// index and found
		int index = 0;
		boolean found = false;
		
		//declare neighbour and Queue... note:: of type board
		Board neighbour;
		Queue<Board> q = new LinkedList<Board>();
		
		// find 0 in the puzzle 
		for(int i=0;i<board.length;i++){
			
			if(board[i]==0){
				index = i;
				found = true;
				break; 
			}
		}
		
		// noet:: IMP;
		if(!found)
			return null;
		
		// check for 4 cases according to index
		
		// case 1: if not top row, then move to 1 row above
		if(index/N != 0){
			
			// initalize neighbour
			neighbour = new Board(board); // IMP
			exch(neighbour,index,index-N);
			q.add(neighbour);			
		}
		
		// case 2: if not in last row, ...
		if(index/N !=(N-1)){
			neighbour = new Board(board);
			exch(neighbour,index,index+N);
			q.add(neighbour);
		}
		
		//case3: if not in left row
		if(index%N !=0){
			neighbour = new Board(board);
			exch(neighbour,index,index-1);
			q.add(neighbour);
		}
		
		
		//case4: if not in right row
		if(index%N !=(N-1)){
			neighbour = new Board(board);
			exch(neighbour,index,index+1);
			q.add(neighbour);
		}
		
		return q;		
	}
	
	public Board exch(Board b,int i,int j){
		
		int temp = b.board[i];           // note::: b.board
		b.board[i]=b.board[j];
		b.board[j] = temp;
		
		return b;
	}
}





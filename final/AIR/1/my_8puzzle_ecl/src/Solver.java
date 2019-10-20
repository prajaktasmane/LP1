import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Solver{

	
	// goal 
	private SearchNode goal;

	private class SearchNode{
	
	// note: each of these are private
		private int moves;
		private SearchNode prev;
		private Board board;
	
	
		// note: Board initial here
		public SearchNode(Board initial){

			this.moves= 0;
			this.prev = null;
			this.board = initial;
		}
		
	}
	
	public Solver(Board initial){
	
		// note:::: counter, priorityorder and priorityQueue
		int counter = 0;
		PriorityOrder order = new PriorityOrder();      // define it
		PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>(order); //note:: so not forget order
		
		
		// note: make searchnode, add it,remove it
		SearchNode node = new SearchNode(initial);
		
		pq.add(node);
		
		SearchNode min = pq.remove(); // note: min is searchnode
		
		// note: min.board.isGoal()
		while(!min.board.isGoal()){
			
			// use of counter is here
			if(counter++==1500)
				System.out.println("Too much time to compute.. no solution");
			
			// note: neightbours to be defined
			for(Board b:min.board.neighbours()){     // note:: for and not while
				
				// if it's 1st node OR if this b does not equal the min.prev.board ie prev board entry
				// then we add it to pq
				if(min.prev==null || !b.equals(min.prev.board)){
					
					SearchNode n = new SearchNode(b);
					n.moves = min.moves+ 1; // noe:: min.moves +1 and not +1
					n.prev = min;
					
					pq.add(n);
				}
			}
			
			// in while loop only
			min = pq.remove();
		}
		
		if(min.board.isGoal())
			goal = min;     // set Goal .. and not true /false
		
		else
			goal =null;
	
	}

	
	public boolean isSolvable(){

		return goal!=null;
	}
	
	public class PriorityOrder implements Comparator<SearchNode>{       // <SearchNode>
	
		public int compare(SearchNode p1, SearchNode p2){
		
			int pa = p1.board.manhattan() + p1.moves; // note: p1.board.manhattan()
			int pb = p2.board.manhattan() + p2.moves;
		
			if(pa>pb) 
				return 1;
			if(pa<pb)
				return -1;
			else
				return 0;
		}
	
	}
	
	public int moves(){
		
		if(!isSolvable())
			return -1;            // and not null
		
		return goal.moves;	// note::: goal moves.. since its the last node
	}
	
	// noet:: iterable and not Iterator
	public Iterable<Board> solution(){
		
		// note:: take into account not solvable case
		if(!isSolvable())
			return null;
		
		Stack<Board> s = new Stack<Board>();
		for(SearchNode  n = goal; n!=null;n=n.prev)
			print_board(n.board);
		
		return s;	
	}
	
	
	public void print_board(Board b){
		
		for(int i=0;i<b.board.length;i++){
			
			if(i%3==0)
				System.out.println();
			
			System.out.print(b.board[i]+" ");
		}
		System.out.println("--------------");
	}
	
	public static void main(String args[]){

		int n=3;
		Scanner sc = new Scanner(System.in);
		
		// blocks[][] declare
		int blocks[][] = new int[n][n];
		
		System.out.println("Enter initial config");
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++){
				blocks[i][j]=sc.nextInt();
			}
		}
		
		
		Board initial = new Board(blocks);
		Solver solver = new Solver(initial);
		// note:: after iteration, solver has the goal nodde
		
		
		if(!solver.isSolvable())
			System.out.println("No solution possible");
			
		else
		{
			// note: solver.moves and not min.moves()
			System.out.println("Min no of moves:"+solver.moves()+"See top to bottom");
			solver.solution();
			// note: solver.solution()
			
			// define these 2 functions along with print board
		}

	}

}





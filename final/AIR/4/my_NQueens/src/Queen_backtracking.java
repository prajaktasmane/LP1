import java.util.Scanner;


public class Queen_backtracking {

	int N;
	
	
	void setN(int N){
		this.N = N;
	}
	
	void printSolution(int board[][]){
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	boolean isSafe(int board[][], int row,int col){
		
		// left side horizontal ie check that row
		for(int i=0;i<col;i++){
			if(board[row][i]==1)
				return false;
		}
		
		// upper diagonal
		// note: i  and j are intialized to row and col
		for(int i=row,j=col;i>=0 && j>=0;i--,j--){
			if(board[i][j]==1)
				return false;
		}
		
		// lower diagonal
		// note: i<N and not <=N
		for(int i=row,j=col;i<N &&j>=0;i++,j--){
			if(board[i][j]==1)
				return false;
		}
		
		return true;
	}
	
	boolean solveUtil(int board[][],int col)
	{
		
		// if all cols are done
		if(col>=N)
			return true;
		
		// note: we need to check across this col only. ie all rows in this col
		// no need for double loop
		// just 1 loop for rows of the col
		// check each position in the row
		for(int i=0;i<N;i++){
				
			// if safe, make board as 1, and recursive function for col+1
			// note: col in isSafe
			if(isSafe(board,i,col)==true)
			{
				board[i][col]=1;
					
				// note: SolveUtil is in 'if'
				if(solveUtil(board,col+1)==true)
					return true;
				
				board[i][col]=0;
				// backtracking // note: in the isSafe if only
			}
		}
		
		return false;
		// after for loop
	}
		
	
	boolean solveQ() {
		
		// declare board
		int board[][] = new int[N][N];
		
		// initialize
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++){
				board[i][j]=0;
			}
		}
		
		if(solveUtil(board,0)==false){
			System.out.println("No solution possible");
			return false;
		}
		
		printSolution(board);
		return true;
	}
	
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		Queen_backtracking q = new Queen_backtracking();
		
		System.out.println("Enter N backtracking");
		int N = sc.nextInt();
		
		q.setN(N);
		q.solveQ();
		
	}
}

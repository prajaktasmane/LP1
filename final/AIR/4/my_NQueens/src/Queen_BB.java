import java.util.Scanner;

public class Queen_BB {

	int N;
	
	void setN(int N){
		this.N = N;
	}
	
	void printSolution(int board[][]){
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	boolean isSafe(int row,int col,int slashcode[][],int backslashcode[][],boolean rowlookup[],boolean slashcodelookup[],boolean backslashcodelookup[])
	{
		// note: || 
		if(rowlookup[row] || slashcodelookup[slashcode[row][col]] || backslashcodelookup[backslashcode[row][col]] ==true)
			return false;
		
		return true;
		
	}
	
	boolean solveUtil(int board[][],int col,int slashcode[][],int backslashcode[][],boolean rowlookup[],boolean slashcodelookup[],boolean backslashcodelookup[])
	{
	
		// check if allcols are over
		if(col>=N)
			return true;
		
		// note: isSafe does not need board argument
		for(int i=0;i<N;i++)
		{
			if(isSafe(i,col,slashcode,backslashcode,rowlookup,slashcodelookup,backslashcodelookup)==true)
			{
				board[i][col]=1;
				// note: do not forget these to make 1
				rowlookup[i]=true;
				slashcodelookup[slashcode[i][col]]=true;
				backslashcodelookup[backslashcode[i][col]]=true;
				
			
				if(solveUtil(board,col+1,slashcode,backslashcode,rowlookup,slashcodelookup,backslashcodelookup)==true)
					return true;
			
				// note: in IsSafe If only
				// do not forget to make all of them false
				board[i][col]=0;
				rowlookup[i]=false;
				slashcodelookup[slashcode[i][col]]=false;
				backslashcodelookup[backslashcode[i][col]]=false;
				
			}
			
		}
		
		return false;
		
	}
	
	boolean solveBB(){
		
		// board declare and initialize
		int board[][] = new int[N][N];
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				board[i][j]=0;
			}
		}
		
		// slashcode and backslashcode declare and initialize
		// note: these are 2D arrays only
		int slashcode[][] = new int[N][N];
		int backslashcode[][] = new int[N][N];
		
		// r+c and r-c+N-1
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				slashcode[i][j]=i+j;
				backslashcode[i][j] = i-j+N-1;
			}
		}
		
		// rowlookup, sl,,backlookup... declare and initialize
		// note: these are boolean arrays
		// note the dimensions
		
		boolean rowlookup[] = new boolean[N];
		for(int i=0;i<N;i++){
			rowlookup[i]=false;
		}
		
		boolean slashcodelookup[] = new boolean[2*N-1];
		boolean backslashcodelookup[] = new boolean[2*N-1];
		
		for(int i=0;i<2*N-1;i++){
			slashcodelookup[i] = false;
			backslashcodelookup[i] = false;
		}
		
		if(solveUtil(board,0,slashcode,backslashcode,rowlookup,slashcodelookup,backslashcodelookup)==false)
		{
			System.out.println("No solution exists");
			return false;
		}
		
		printSolution(board);
		return true;
		
	}
	
	
	public static void main(String args[]){
		
		Queen_BB qbb= new Queen_BB();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter N BB");
		int N = sc.nextInt();
		
		qbb.setN(N);
		qbb.solveBB();
	}
}

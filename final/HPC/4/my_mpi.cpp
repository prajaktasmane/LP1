#include<bits/stdc++.h>
#include<mpi.h>

using namespace std;   // IMP

double sq(double n){                    // IMP
	return n*n;
}

class Training{

public:                   // note::: public
	double r,g,b;
	int skin;
	
	Training(double r,double g,double b,int skin){
	
		this->r =r;
		this->g = g;                         // NOTE:: in cpp, ->
		this->b = b;
		this->skin = skin;
		
	}

	// note:: return double
	public:
	double cal_dist(double rr,double gg,double bb){
	
		return sqrt(sq(rr-r)+sq(gg-g)+sq(bb-b));
	} 

};          // do not forget ;


class Testing{

public:
	double r,g,b;
	
	Testing(double r,double g,double b){
		
		this->r = r;
		this->g = g;
		this->b =b;		
	}
};

//////     IMP
vector<Training> train;

// no public here
//note:: returntype int
int getClass(double r,double g,double b,int k)
{
	// declare multiset
	multiset<pair<double,int> > st; // note:: > > 
	multiset<pair<double,int> > ::iterator it; /// note  >>::iterator it
	
	int kk=0,cnt1=0,cnt2=0;

	for(int i=0;i<train.size();i++)
	{
		double dist = train[i].cal_dist(r,g,b);
		// iMP:: make_pair
		st.insert(make_pair(dist,train[i].skin));          // note:: train[i].skin
 		
 		// all are instted
	}
	
	// note:: it=st.begin()
	for(it = st.begin();it!=st.end();it++)
	{
		if((*it).second==1)        // note:: (*it).second
			cnt1++;
			
		else
			cnt2++;
			
		kk++;
		
		if(kk==k)
			break;
	}
	
	if(cnt1>cnt2)
		return 1;
	else
		return 2;

}

int main()
{

	// initialize
	MPI_Init(NULL,NULL);
	
	// world_size and ranl
	// noet : MPI_COMM_WORLD and &
	int world_size,rank=0;
	MPI_Comm_size(MPI_COMM_WORLD,&world_size);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);
	
	//reuquest and status
	MPI_Request request[(world_size-1)*3];
	MPI_Status status[(world_size-1)*3];
	
	// for trainign, directly
	ifstream fin("training.txt");         // NOTE::: very imp   directly here read
	
	double a,b,c;
	int d;
	while(fin>>a>>b>>c>>d)
	{
		Training obj = Training(a,b,c,d);
		train.push_back(obj);
	}
	
	fin.close();
	
	// IMP::: 
	int k = sqrt(train.size());
	
	// vector test now
	vector<Testing> test;
	
	//// after this, according to rank if-else
	if(rank==0)
	{
		// read from file
		fstream fin("test.txt");
		double a,b,c;
		while(fin>>a>>b>>c)
		{
			Testing t = Testing(a,b,c);
			test.push_back(t);
		}
		fin.close();
		
		// for all the testing values in a loop, send MPI_send
		int index =1;    // IMP
		double r,g;
		
		for(int i=0;i<test.size();i++)
		{
			
			r = test[i].r;
			g = test[i].g;
			b = test[i].b;
			
			MPI_Isend(&r,1,MPI_DOUBLE,i,0,MPI_COMM_WORLD,index+request);          // nte::: Isend .. s is small
			index++;
			
			MPI_Isend(&g,1,MPI_DOUBLE,i,0,MPI_COMM_WORLD,index+request);
			index++;
			
			MPI_Isend(&b,1,MPI_DOUBLE,i,0,MPI_COMM_WORLD,index+request);
			index++;
		
		}
		
		// now after for loop
		int skin = getClass(r,g,b,k);                // NOTE::: passs r,g,b,k do not forget k
		cout<<endl<<"Class of test"<<rank<<" is :"<<skin;            // rank is rank
	
	}
	
	else
	{
		int off = (rank-1)*3+1;       // note::: (rank-1)*3+1
		double r,g,b;
		// after offset, Irecv 
		// noe :recv
		MPI_Irecv(&r,1,MPI_DOUBLE,0,0,MPI_COMM_WORLD,request+off);
		MPI_Irecv(&g,1,MPI_DOUBLE,0,0,MPI_COMM_WORLD,request+off+1);
		MPI_Irecv(&b,1,MPI_DOUBLE,0,0,MPI_COMM_WORLD,request+off+2);
		
		// wait
		// it has request and status along with offset
		MPI_Wait(request+off,status+off);
		MPI_Wait(request+off+1,status+off+1);
		MPI_Wait(request+off+2,status+off+2);
		
		int skin = getClass(r,g,b,k);
		cout<<endl<<"class of test"<<rank<<" is :"<<skin;
	}

	//barrier and finalize after if else
	MPI_Barrier(MPI_COMM_WORLD);
	MPI_Finalize();
	return 0;
}


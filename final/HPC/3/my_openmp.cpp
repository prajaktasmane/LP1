#include<stdio.h>
#include<iostream>
#include<omp.h>
#include<vector>    // do not forget
#include<bits/stdc++.h>    // IMP:: bits/stdc++.h

using namespace std;

void bubble_serial(vector<int> arr,int n){

	for(int i=0;i<n-1;i++)
	{
		for(int j=0;j<n-i-1;j++)
		{
			if(arr[j]>arr[j+1])
				swap(arr[j],arr[j+1]);
		}
	}
	
	// sorted array print:
	//for(int i=0;i<n;i++)
		//cout<<arr[i]<<" ";
}

void bubble_parallel(vector<int> arr,int n)
{
	int changes; // declare out
	
	do{
	
		changes=0;         // intitialzie inside
		
		#pragma omp parallel for reduction(+:changes)
		for(int i=0;i<n-1;i=i+2)                    // i = 0 to n-1  ... also i=i+2
		{
			if(arr[i]>arr[i+1])
				{
					swap(arr[i],arr[i+1]);                        
					changes++;                                
				}
		}
		
		#pragma omp parallel for reduction(+:changes)
		for(int i=1;i<n-1;i=i+2)               // note: i=1 to n-1
		{
			if(arr[i]>arr[i+1])
			{
				swap(arr[i],arr[i+1]);
				changes++;
			}
		
		}
	
	}while(changes);                   // note:; while(changes)
	
	
  
	// sorted array print:
	//for(int i=0;i<n;i++)
		//cout<<arr[i]<<" ";

}


void mergesort(vector<int>&temp, int l, int mid, int r)
{

	int i,j,k;
	int n1 = mid-l+1;        // note::: mid - l +1
	int n2 = r-mid;
	
	int L[n1], R[n2];
	
	for(i = 0;i<n1;i++)
		L[i] = temp[l+i];         // note::: temp[l+i]
		
	for(j=0;j<n2;j++)
		R[j] = temp[mid+l+j];           // note this
	
	i = 0;
	j = 0;
	k = l;    // note:

	while(i<n1 && j<n2)
	{
		if(L[i]<=R[j])       // imp
		{
			temp[k] = L[i];        // note : temp[k] = 
			i++;
		}
		
		else
		{
			temp[k] = R[j];
			j++;
		}
		
		k++;
	}
	
	while(i<n1)
	{
		temp[k] = L[i];
		i++;
		k++;
	}
	
	while(j<n2)
	{
		temp[k] = R[j];
		j++;
		k++;
	}
	
}


// note::: vecctor<int>&a
void merge_serial(vector<int>&temp,int l,int r)
{
	if(l<r)
	{
		// calculate mid
		int mid = (l+r)/2;
		
		// call to same function(serial)
		merge_serial(temp,l,mid);
		merge_serial(temp,mid+1,r);
		
		// other function call
		mergesort(temp,l,mid,r);
	
	}
	
	//for(int i=0;i<temp.size();i++)
//		cout<<temp[i]<<" ";
}

void merge_parallel(vector<int>&temp, int l, int r)
{
	if(l<r)
	{
		int mid = (l+r)/2;
		
		#pragma omp parallel sections             // IMP
		{
			#pragma omp section              // noe::: omp section here and not omp parallel
			merge_serial(temp,l,mid);               // note::: here it is merge_serial call
			
			#pragma omp section
			merge_serial(temp,mid+1,r);
		}
		mergesort(temp,l,mid,r);
	}
	
	//for(int i=0;i<temp.size();i++)
		//cout<<temp[i]<<" ";
}

int main()
{
	
	// rand
	srand(time(NULL));
	
	
	// n take input
	int n;
	cout<<"Enter N"<<endl;
	cin>>n;
	
	// IMP:::::
	omp_set_num_threads(2);
	
	// initialize a
	vector<int> a(n);
	for(int i=0;i<n;i++)
		a[i]=rand()%100;
	
	// bubble
	double st = omp_get_wtime();
	bubble_serial(a,n);
	double en = omp_get_wtime();
	cout<<endl<<"Bubble sort serial: "<<en - st;
	
	st = omp_get_wtime();
	bubble_parallel(a,n);
	en = omp_get_wtime();
	cout<<endl<<"Bubble sort parallel: "<<en - st;
	
	// temp
	vector<int> temp(a.begin(),a.end());
	
	// note:: for merge sort, pass temp,0,n-1
	st = omp_get_wtime();
	merge_serial(temp,0,n-1);
	en = omp_get_wtime();
	cout<<endl<<"merge sort serial: "<<en - st;
	
	st = omp_get_wtime();
	merge_parallel(temp,0,n-1);
	en = omp_get_wtime();
	cout<<endl<<"merge sort parallel: "<<en - st;

}

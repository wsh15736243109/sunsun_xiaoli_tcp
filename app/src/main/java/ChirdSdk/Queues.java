package ChirdSdk;

import ChirdSdk.Apis.st_VideoFrame;

public class Queues {
	
	public static final int MAXQSIZE = 1024*100;
	
	private int front;
	private int rear;
	public st_VideoFrame [] base;
	
	public Queues(){
		front = 0;
		rear  = 0;	
		base = new st_VideoFrame[MAXQSIZE+1];
	}
	
	public int putQueue(st_VideoFrame data){
		
		if(front == (rear + 1) % MAXQSIZE){
			return -1;
		}
		
		synchronized(this) {		
			base[rear] = (st_VideoFrame)data.clone();
			rear = (rear+1) % MAXQSIZE;
		}
		
		return 0;
	}
	
	public st_VideoFrame getQueue(){
		int Cnt = 0;
		if(front == rear){
			return null;
		}
	
		synchronized(this) {
			Cnt = front;
			front = (front+1) % MAXQSIZE;
		}

	    return base[Cnt];
	}
	
	public int getLength(){
		return (rear - front + MAXQSIZE) % MAXQSIZE;
	}

}

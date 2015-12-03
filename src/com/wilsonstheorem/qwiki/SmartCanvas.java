package com.wilsonstheorem.qwiki;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;

public class SmartCanvas extends JPanel implements Runnable {
	public Graphics2D g2;
	//public boolean animate= false;
	//public int t=0; // transition parameter
	public SmartCanvas(String[] fileNames,int width, int height){
		this.width=width;
		this.height=height;
		images = new ArrayList<CanvasImage>();
		backup = new ArrayList<CanvasImage>();
		Random r = new Random();
//((double)i)/fileNames.length
		images.add(new CanvasImage(fileNames[0], 50+r.nextInt(100), 40+r.nextInt(80), (double)1, true, 900));
		head=images.get(0);
		head.prev=null;
		if(head.isActive())active=head;
		//System.out.println("i=0:"+head.getMinWidth());
		for(int i=1;i<fileNames.length;i++){	
			images.add(new CanvasImage(fileNames[i], 50+r.nextInt(100), 40+r.nextInt(80), 30-(double)i, false, 900));
			//backup.add(new CanvasImage(null,images.get(i).getMinWidth(),images.get(i).getMinHeight(),0,false,0)); // Only need to preserve codimen
			if(images.get(i).isActive())active=images.get(i);
			images.get(i-1).next=images.get(i);
			images.get(i).prev=images.get(i-1);
			//System.out.println("i="+i+":"+images.get(i).getMinWidth());
		}
		images.get(images.size()-1).next=head;
		head.prev=images.get(images.size()-1);
		
	}
	public int width;
	public int height;
	public CanvasImage head;
	public ArrayList<CanvasImage> images; // Images are rendered in order that they appear in this arraylist
	public ArrayList<CanvasImage> backup;
	public CanvasImage active=null; // Current active image;
	public int[] indexes; // Store order by pixel value to resize images with large pixel values first
    
	// Note that we use the next/prev parameters to make use of the visual ordering instead of per-pixel ordering
	public void next(){
		// A circular doubly-linked list would be useful in this case, but I think having a circular linked-list interferes with other code
		resetImages();
		if(false){		
			head.setActive(true);
			active=head;
		}
		else{
			active.next.setActive(true);
			active=active.next;
		}
	}
	
	public void backup(){
		backup.clear();
		CanvasImage curr = head;
		for(int i=0;i<images.size();i++){
			backup.add(new CanvasImage(curr.getX(),curr.getY(),curr.getWidth(),curr.getHeight()));
			curr=curr.next;
		}
	}
	
	public void transition(double t){
		// Transition from backup coordinates to new ones
		// Traverse in visual order, which is same in backup and images
		g2=(Graphics2D)getGraphics(); // TODO Why is this line needed?
		g2.clearRect(0, 0, width, height);
		CanvasImage curr = head;
		for(int i=0;i<images.size();i++){
			//System.out.println("backup x:"+backup.get(i).getX()+" images x:"+curr.getX());
			g2.drawImage(curr.getImage(), backup.get(i).getX()+(int)(t*(curr.getX()-backup.get(i).getX())),  backup.get(i).getY()+(int)(t*(curr.getY()-backup.get(i).getY())),(int)( backup.get(i).getWidth()+(t*(curr.getWidth()-backup.get(i).getWidth()))),(int)( backup.get(i).getHeight()+(t*(curr.getHeight()-backup.get(i).getHeight()))),null); 
 		curr=curr.next;
		}
	}
	
	public void prev(){
		// TODO Neater code
		resetImages();
		if(false){		
			images.get(images.size()-1).setActive(true);
			active=images.get(images.size()-1);
		}
		else{
			active.prev.setActive(true);
			active=active.prev;
		}
	}
	
	private void resetImages(){
		for(int i=0;i<images.size();i++){
			images.get(i).setWidth(images.get(i).getMinWidth());
			images.get(i).setHeight(images.get(i).getMinHeight());
			images.get(i).setActive(false);
		}
	}
	
	public void changeActive(int ix){
		/*while(maxHeight>active.getMinHeight()){
			
			try{
				Thread.sleep(10);
			} catch (InterruptedException e) {
	                        System.out.println("I was interrupted");
	                }
			maxHeight--;
			redraw();
			render(g2);
		}
		*/
		/*
		for(int i=0;i<images.size();i++){
			images.get(i).setWidth(images.get(i).getMinWidth());
			images.get(i).setHeight(images.get(i).getMinHeight());
			if(i==ix){
				images.get(i).setActive(true);
				active=images.get(i);
			}
			else
				images.get(i).setActive(false);
		}*/
		// Reset dimensions of images to minimums? Or shrink back...do a cool effect
	}
	public void redraw()
    {
    	
    }

	
	
	public void setupRender(){
    	g2.drawRect(0, 0, width, height);
    	//Comparator<CanvasImage> comparator = Collections.reverseOrder();
    	// TODO - does sortedImages array work
    	// This can be made more efficient, not a big deal, but apparently a simple assignment operator doesn't work
    	
    	Collections.sort(images); // assorts in ascending order
    	/*
    	for(int i=0;i<images.size();i++){
   		if(i==images.size()-1)return;
    	}*/
    //	boolean progress = false;
    	//do{
    		//progress=false;
    	// Start with image with largest calculated (taking "active" into account) pixel value
	    	for(int i=images.size()-1;i>=0;i--){
    	//for(int i=0;i<sortedImages.size();i++){
    		//for(int i=2;i<3;i++){
    		//int i=3;
	    		rearrange(i);
	    		//progress=rearrange(i) || progress;
	    		
			}
    	//}while(progress);
	}
	
	
    public void paintComponent(Graphics g){ // @Override
    	g2 = (Graphics2D)g;
    	/*
    	if(animate){
    		System.out.println("TRANSITIONING");
    		transition(t);
    		System.out.println("TRANSITIONING");
    	}
    	else{*/
    		setupRender();
    		System.out.println("PAINGINGGGG");
    		render();
    	//}
    	
    }
    
    public void render(){
    	for(int i=0;i<images.size();i++){
    		g2.drawImage(images.get(i).getImage(), images.get(i).getX(), images.get(i).getY(),(int)images.get(i).getWidth(),(int)images.get(i).getHeight(),null); 
    		
    	}
    }
    

    
    //  ix of the current image to try to resize
	public boolean rearrange(int ix){
		//System.out.println("In rearrange");
		boolean success = true;
		boolean progress = false;
		// These parameters can be useful for more optimized incrementation later
		// TODO Assuming can be at least slightly scaled up...
		//boolean test=bookArrange(); // Assuming this can be done
		//System.out.println("Current resize height:"+images.get(ix).getImage().getHeight());
		
		if(images.get(ix).isActive()){
			while(success){
				//while(success && images.get(ix).getWidth()<width && images.get(ix).getHeight()<height){
			//		System.out.println("Active");
					
					success = bookArrange(); 
					
						images.get(ix).inc();
					
					
					progress = progress || success;
				}
				// TODO these decs could be causing unnecessary spacing, also find out why multiple decs are necessary
				images.get(ix).dec();
				images.get(ix).dec();
				//images.get(ix).decWidth();
				//images.get(ix).decHeight();
			}
			else{
			
				while(success){
			//while(success && images.get(ix).getWidth()<width){
				//System.out.println("Not active");
				// Be efficient - if 
				success = bookArrange();   
				images.get(ix).incWidth();
				progress = progress || success;
			}
				 // IS this messing up resize for other images?
				images.get(ix).decWidth();
				images.get(ix).decWidth();
				images.get(ix).decWidth();
			success=true;

			// TODO-optimize while loop conditions
			//while(success && (!(images.get(images.size()-1).isActive()) || images.get(images.size()-2).getHeight()<=images.get(images.size()-1).getHeight())){
			 // If img ix is on same row as active, make sure its height doesn't exceed..
			
			// NOTE ON FAILURE, most restore ALL MODIFED IMAGE COORDINATES!! Not just current. height
			while(success && (!activeSameRow(ix) || images.get(ix).getHeight()<active.getHeight())){
			//while(success && (ix!=0 || images.get(ix).prev.isActive() || images.get(ix).prev.getHeight()>images.get(ix).getHeight())){
				//System.out.println("1/2 arrange session");

				success = bookArrange();   
				images.get(ix).incHeight();
				progress = progress || success;
			}
			images.get(ix).decHeight();
			images.get(ix).decHeight();
			images.get(ix).decHeight();
			// While loop exiting implied failed bookArrange
			}
		//	System.out.println("Finished an arrange session");
		// Restore dimensions from last successful arrangement
		bookArrange();  
			return progress;
	}
	
	// Do we need to check all images?
	public boolean activeSameRow(int ix){
			return active.getY() == images.get(ix).getY();
}
	
	// Given current dimensions of each image, try to increase 
    public boolean pocketArrange(int ix, int inc){
		return true;
	}

    public boolean magnetArrange(int ix){
    	return true;
    }
    
    // A simplistic arrangement pattern for now...
    public boolean rightDownArrange(int ix, int dx, int dy){
    	int xmax=0;
    	int ymax=0;
    	/*
    	for(int i=0;i<images.size();i++){
    		while(collides(i)){
    			
    		}
    	}*/
    	return true;
    }
    
    // What is idx for?
    public boolean bookArrange(){
    	// We refer to original images array in order to preserve rendering order
    	int xmax=(int)head.getWidth()-1; // Right edge
    	int ymax=(int)head.getHeight()-1;
    	// Why is ymax changing after looked at?
    	//System.out.println("ymax="+ymax);
    	// TODO This is why only first image gets resized
    	
    	if(ymax>=height)return false;
		if(xmax>=width)return false;
		
    	//if(ymax>=height)images.get(0).
    	int yline=0; // y coordinate that use to place new images
    	// Redundant to do this every time
    	//images.get(ix).setX(0);
    	//images.get(ix).setY(0);
    	head.setX(0);
    	head.setY(0);
    	CanvasImage current=head.next;
    	for(int i=1;i<images.size();i++){
    		//System.out.println("Made it!");
    		
    		// TODO - optimize logic
    		// If active is at x=0 and not at bottom, put filler to right
    		//System.out.println("i="+i);
    		//if(current==active)System.out.println("Rendering active");
    		if(current.prev.isActive() && xmax==current.prev.getWidth()-1){
    			//System.out.println("Making right filler");
    			current.setY(yline);
				ymax=Math.max(ymax,yline+(int)current.getHeight());	
			current.setX(xmax+1);
			
			
			xmax+=current.getWidth();
    			
    		}
    		// If no more room in current row, start a new row.  Also if last image is active, put filler to left
    		else if(xmax+1+current.getWidth()>=width || (current.next!=null && current.next.next==null && current.next.isActive())){
    		//	System.out.println("New row");
    			current.setX(0);
    			yline=ymax+1;
    				current.setY(yline);
    				
        			ymax+=current.getHeight();
    			
    			xmax=(int)current.getWidth()-1;
    		}
    		// If last image is active, can't leave by itself; must have "filler" image to fill empty space
    		//else if(i+1<images.size() && current.isActive()){
    			
    		//}
    		else{
    			//System.out.println("In same row");
    				current.setY(yline);
    				ymax=Math.max(ymax,yline+(int)current.getHeight());	
    			current.setX(xmax+1);
    			
    			
    			xmax+=current.getWidth();
    		}
    		current=current.next;
    		if(ymax>=height)return false;
    		if(xmax>=width)return false;
    	}
    	
    	return true;
    }
    
	@Override
	public void run() {
		//g2=(Graphics2D)getGraphics();
		//g2.clearRect(0, 0, width, height);
		for(double t=0;t<=1;t+=0.01){
			//System.out.println("ANIMATINGGG");
			transition(t);
			try{
				Thread.sleep((int)(20*t));
			} catch (InterruptedException ie) {
	                       
	                }
			
		}
		render();
		
	}
}

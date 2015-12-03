package com.wilsonstheorem.qwiki;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;

public class Main implements KeyListener {
	public static JFrame frame;
	public static SmartCanvas display;
	public static int activeIx;
	public Main(){
		frame = new JFrame("Qwiki Contest - Use left/right arrow keys to toggle active image");
		int width=1024;
		int height=768;
		frame.setSize(width+20,height+50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		//RepaintManager.currentManager(frame).setDoubleBufferingEnabled(false);

		String[] fileNames = {"Chrysanthemum2.jpg","Desert2.jpg","Hydrangeas2.jpg","Jellyfish2.jpg","Koala2.jpg","Lighthouse2.jpg","Penguins2.jpg","Tulips2.jpg","Chrysanthemum2.jpg","Desert2.jpg","Hydrangeas2.jpg","Jellyfish2.jpg","Koala2.jpg","Lighthouse2.jpg","Penguins2.jpg","Tulips2.jpg"};//"Chrysanthemum2.jpg","Desert2.jpg","Hydrangeas2.jpg","Jellyfish2.jpg","Koala2.jpg","Lighthouse2.jpg","Penguins2.jpg"};//"Tulips2.jpg","Chrysanthemum2.jpg","Desert2.jpg","Hydrangeas2.jpg","Jellyfish2.jpg","Koala2.jpg","Lighthouse2.jpg","Penguins2.jpg","Tulips2.jpg","Chrysanthemum2.jpg","Desert2.jpg","Hydrangeas2.jpg","Jellyfish2.jpg","Koala2.jpg","Lighthouse2.jpg","Penguins2.jpg","Tulips2.jpg"};
		display = new SmartCanvas(fileNames,width,height);
		
		//display.parent=frame; // This is very hackish, but it's quick, I'm not that experienced with Java GUI programming, and the deadline is nearing
		activeIx=fileNames.length-1;
        //JScrollPane scroll = new JScrollPane(display);
        frame.add(display);
        
        
        //display.redraw();
		//frame.repaint();
	    frame.setVisible(true);
	    /*
	    while(true){
	    	
			display.redraw();
			frame.repaint();
			try{
				Thread.sleep(30);
			} catch (InterruptedException e) {
	                        System.out.println("I was interrupted");
	                }
		}*/
	    
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Main project = new Main();
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// Note java threaded stuff - as soon as let go of key, stuff happens
		//JOptionPane.showInputDialog("cur active"+activeIx);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			/*
			display.maxHeight=display.active.getHeight();
			while(display.maxHeight>=display.active.getMinHeight()){
				frame.repaint();
				display.maxHeight--;
				try{
					Thread.sleep(10);
				} catch (InterruptedException ie) {
		                        System.out.println("I was interrupted");
		                }
			}*/
			display.backup();
			
			display.next();
			display.setupRender();
			
			display.run();
			//display.animate=false;
			
	        /*   display.maxHeight=display.active.getMinHeight()+1;
	           int h1=0;
	           // While the image is still growing, let it grow
	           while(h1!=display.active.getHeight()){
					h1=(int)display.active.getHeight();
	        	   frame.repaint();
					display.maxHeight++;
				}*/
			//System.out.println("rightttt");
	           //display.redraw();
	   		//frame.repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
        	
display.backup();
			
			display.prev();
			display.setupRender();
			
			display.run();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER){      		
        			frame.repaint();     		
        	
        	
        }
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

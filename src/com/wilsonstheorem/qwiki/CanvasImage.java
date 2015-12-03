package com.wilsonstheorem.qwiki;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.imageio.ImageIO;

public class CanvasImage implements Comparable {
	public int getDw() {
		return dw;
	}

	public int getDh() {
		return dw;
	}
	/*
	public CanvasImage(CanvasImage o){
		System.out.println("In copy constructor!");
		width=o.width;
		height=o.height;
		x=o.x;
		y=o.y;
	}*/
	
	public CanvasImage(int x, int y, double width, double height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}

	public CanvasImage(String fileName, int minWidth, int minHeight, double pixelValue, boolean active, double n){
		image = null;
		if(fileName!=null){
		try {
		    image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("Error reading image file" +e.getMessage());
		}
		}
		this.minWidth=minWidth;
		this.minHeight=minHeight;
		this.width=minWidth;
		this.height=minHeight;
		this.pixelValue=pixelValue;
		this.active=active;
		//this.popularity=popularity;
		this.n=n;
		int gcd=gcdint(image.getWidth(),image.getHeight());
		this.dw=image.getWidth()/gcd;
		this.dh=image.getHeight()/gcd;
	}
	
    private static int gcdint(int a, int b) {
        BigInteger b1 = new BigInteger(""+a); // there's a better way to do this. I forget.
        BigInteger b2 = new BigInteger(""+b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public double getPixelValue() {
		return pixelValue;
	}
	public void setPixelValue(double pixelValue) {
		this.pixelValue = pixelValue;
	}
	public int getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}
	public int getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	public double getN() {
		return n;
	}
	public void setN(double n) {
		this.n = n;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void incWidth(){
		if(this.active){
			this.width+=dw;
		}
		else{
			this.width++;
		}
	}
	public void incHeight(){
		if(this.active){
			this.height+=dh;
		}
		else{
			this.height++;
		}
	}
	
	public void decWidth(){
		if(this.active){
			//this.width-=dw;
		}
		else{
			this.width--;
		}
	}
	public void decHeight(){
		if(this.active){
			this.height-=dh;
		}
		else{
			this.height--;
		}
	}
	
	public void dec(){
		this.width--;
		this.height-=(double)image.getHeight()/image.getWidth();
	}
	
	public void inc(){
		this.width++;
		this.height+=(double)image.getHeight()/image.getWidth();
	}
	@Override
	public int compareTo(Object o){
		if(o==null || !(o instanceof CanvasImage)){System.out.println("Compare error");return 9000;} // TODO: Error stuff...
		CanvasImage ci = (CanvasImage)o;
		return (int)((pixelValue*(active?n:1))-(ci.pixelValue*(ci.active?ci.n:1)));
	}
	
	private boolean active;
	private double pixelValue; // Value per pixel, determines weight of each pixel when maximizing the total value of visible pixels
	private int minWidth, minHeight;
	private double width,height; // Current width, height that image is resized to
	private int x,y; // Current coordinates of image on Canvas
	private double n; // Calculated value per visible pixel is multiplied by n iff active==true
	//private int popularity; // Calculated value per visible pixel is always
	private BufferedImage image;
	private int dw;
	private int dh;
	public CanvasImage next; // The next CanvasImage to be rendered, to retain original order even after sorted
	public CanvasImage prev;
}


package snakecob;
import java.awt.Graphics;
import java.awt.Color;

public class SnakeSegment {
    
    private int xPos, yPos, width, height;
    
    public SnakeSegment(int xPos, int yPos, int width, int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xPos * width, yPos * height, width, height);
    }

    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    public int getxPos(){
        return xPos;
    }

    public void setyPos(int yPos){
        this.yPos = yPos;
    }

    public int getyPos(){
        return yPos;
    }

}
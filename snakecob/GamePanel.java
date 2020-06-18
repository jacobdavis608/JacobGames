package snakecob;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static int waitTime;

    private int direction;

    private Thread gameThread;
    private boolean isRunning; 

    private SnakeSegment nose;
    private ArrayList<SnakeSegment> snake;

    private Snack snack;
    private ArrayList<Snack> snacks;
    private Random r;

    private int t = 0;

    public GamePanel(){
        setFocusable(true);

        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        waitTime = 500;
        start();
    }

    public void setDifficulty(int d){
        if (d < 1){
            d = 1;
        } else if (d > 5){
            d = 5;
        }

        switch(d){
            case 1:
                waitTime = 500;
                break;
            case 2:
                waitTime = 200;
                break;
            case 3:
                waitTime = 100;
                break;
            case 4:
                waitTime = 50;
                break;
            case 5:
                waitTime = 25;
                break;
            default:
                waitTime = 500;
                break;
        }

    }

    public void start(){
        isRunning = true;
        snake = new ArrayList<SnakeSegment>();
        
        r = new Random(); //random initial snack position
        int initialX = r.nextInt((WIDTH/10) - 1);
        int initialY = r.nextInt((HEIGHT/10) - 1);

        snacks = new ArrayList<Snack>();
        snack = new Snack(initialX, initialY, 10, 10);
        snacks.add(snack);

        gameThread = new Thread(this);
        gameThread.start();
        direction = 0;
    }

    private int checkCollision(){
        //return non zero if snake has collided with the wall or itself
        //check wall collision
        int noseX = snake.get(0).getxPos();
        int noseY = snake.get(0).getyPos();
        if (noseX < 0 || noseX > WIDTH/10){
            return 1;
        } else if (noseY < 0 || noseY > HEIGHT/10){
            return 1;
        }
        
        //check self collision
        for (int i = 1; i < snake.size(); i++){
            if (noseX == snake.get(i).getxPos() && noseY == snake.get(i).getyPos()){ //same position
                return 1;
            }
        }
        

        return 0; //if everything is fine
    }

    private int foodCheck(int prevNoseX, int prevNoseY){
        //check to see whether snake will eat snack
        nose = snake.get(0);
        snack = snacks.get(0);
        int xDif = snack.getxPos() - nose.getxPos();
        int yDif = snack.getyPos() - nose.getyPos();

        if (direction == 0 && (xDif == 0 && yDif == -1)){ //if moving up about to eat from below
            //eat the snack
            nose = new SnakeSegment(snack.getxPos(), snack.getyPos(), 10, 10);
            snake.add(0, nose);
        } else if (direction == 1 && (yDif == 0 && xDif == 1)){ //if moving right about to eat from left
            //eat the snack
            nose = new SnakeSegment(snack.getxPos(), snack.getyPos(), 10, 10);
            snake.add(0, nose);
        } else if (direction == 2 && (xDif == 0 && yDif == 1)){ //if moving down about to eat from above
            //eat the snack
            nose = new SnakeSegment(snack.getxPos(), snack.getyPos(), 10, 10);
            snake.add(0, nose);
        } else if (direction == 3 && (yDif == 0 && xDif == -1)){ //if moving left about to eat from right
            //eat the snack
            nose = new SnakeSegment(snack.getxPos(), snack.getyPos(), 10, 10);
            snake.add(0, nose);
        } else {
            return 0; //not approaching snack
        }

        //if not yet returned, regenerate snack
        snacks.remove(0);
        int nextX = r.nextInt((WIDTH/10) - 1);
        int nextY = r.nextInt((HEIGHT/10) - 1);
            
        snack = new Snack(nextX, nextY, 10, 10);
        snacks.add(snack);

        //return 1 to not advance rest of snake
        return 1;

    }

    public void tick(){
        if (snake.size() == 0){
            nose = new SnakeSegment(25, 25, 10, 10);
            snake.add(nose);
        } else { //move the snake
            if (foodCheck(snake.get(0).getxPos(), snake.get(0).getyPos()) == 1){ //if it ate
                return;
            }
            if (checkCollision() == 1){
                System.out.println("Game over");
                stop();
            }

            int upstreamX = snake.get(0).getxPos();
            int upstreamY = snake.get(0).getyPos();
            
            if (direction == 0){ // up
                snake.get(0).setyPos(upstreamY-1);
            } else if (direction == 1){ // right
                snake.get(0).setxPos(upstreamX+1);
            } else if (direction == 2){ // down
                snake.get(0).setyPos(upstreamY+1);
            } else if (direction == 3){ // left
                snake.get(0).setxPos(upstreamX-1);
            }
            int tempX, tempY;
            for (int i = 1; i < snake.size(); i++){
                tempX = upstreamX;
                tempY = upstreamY;

                upstreamX = snake.get(i).getxPos();
                upstreamY = snake.get(i).getyPos();

                snake.get(i).setxPos(tempX);
                snake.get(i).setyPos(tempY);
            }
        }
        t++;
    }

    public void stop(){
        isRunning = false;
        try{
            gameThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // paint checkerboard background
        for (int i = 0; i < WIDTH/10; i++){
            for (int j = 0; j < HEIGHT/10; j++){
                if (j % 2 == 1 ){
                    if (i % 2 == 1){
                        g.setColor(Color.lightGray);
                    }
                    else {
                        g.setColor(Color.gray);
                    }
                }
                else {
                    if (i % 2 == 1){
                        g.setColor(Color.gray);
                    }
                    else {
                        g.setColor(Color.lightGray);
                    }
                }
                g.fillRect(i*10, j*10, 10, 10);
            }
        }
        
        for (int i = 0; i < snacks.size(); i++){
            snacks.get(i).draw(g);
        }

        for (int i = 0; i < snake.size(); i++){
            snake.get(i).draw(g);
        }
        
        
    }

    @Override
    public void run(){
        while(isRunning){
            tick();
            repaint();
            try{
                gameThread.sleep(this.waitTime);
            } catch(InterruptedException e){
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_RIGHT && direction != 3){
            direction = 1;
        } else if (k == KeyEvent.VK_LEFT && direction != 1){
            direction = 3;
        } else if (k == KeyEvent.VK_UP && direction != 2){
            direction = 0;
        } else if (k == KeyEvent.VK_DOWN && direction != 0){
            direction = 2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

    @Override
    public void keyTyped(KeyEvent e){

    }
}
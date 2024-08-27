import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{ //inherit from app class
    // create a class that only SnakeGame can assess it 
    private class Tile {
        int x;
        int y;

        //create a constructor
        Tile(int x, int y) {
            this.x = x;
            this.y = y; 
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25; //tile size 25*25

    //create a Snake variable
    Tile snakeHead;
    ArrayList<Tile> snakeBody; //arrayList to store all the snake's body parts

    //create a Food variable
    Tile food;
    Random random;

    //game logic 
     Timer gameLoop; //create a var for the timer
     int velocityX; //specify the velocity(to move the snake)
     int velocityY;
     boolean gameOver = false; // set gameOver by default



    //create a constractor
    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        //for keyPressed add this keyListener
        addKeyListener(this); 
        setFocusable(true);

        snakeHead = new Tile(5, 5); // default starting place for the snake
        snakeBody = new ArrayList<Tile>(); //

        food = new Tile(10, 10);  //Tile reprecenting the Food 
        random = new Random(); //create a random object for moving the food in random places
        placeFood(); // call placeFood function

        //set up the velocity(to move the snake)
        velocityX = 0;
        velocityY = 0; 

        gameLoop = new Timer(100, this); //assign gameLop
        gameLoop.start(); //running the loop and drawing the same frame over and over again every 100 millicesonds.
    }

    //create a function 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    //define the graw function
    public void draw(Graphics g){
    // make a Grid 
    // for (int i = 0; i < boardWidth/tileSize; i++){ // 600/25 = 24 columns of squares
    //     g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); //draw a vertical line
    //     g.drawLine(0, i * tileSize, boardWidth, i * tileSize); //draw a horizontal line
    // }

        //draw the Food
        g.setColor(Color.red);
        // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize); //specify the food
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true); //draw the rectangles

        //draw a Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize); //specify the snake
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true); //specify the snake

        //draw Snake Body
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize); //draw a rectangle for that snake part
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true); //draw a rectangle for that snake part
        }

        //draw the Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Your score is: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);//tell the user what their score is
        }
    }

    //define the placeFood function. Will randomly set the X and Y coordinates of the food
    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize); //600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize); 
    }

    //define the function that will take 2 tiles
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }


    //defind the move() function
    public void move(){
        //eat food
        if (collision(snakeHead, food)){
             snakeBody.add(new Tile(food.x, food.y));
             placeFood();
        }

        //move the snake body (going backwards because we need to catch up on the body before we move the snake's head)
        for (int i = snakeBody.size()-1; i >= 0; i --){
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions for snake hitting it's own body
        for ( int i = 0; i < snakeBody.size(); i++ ) { //snake collides with its own body
            Tile snakePart = snakeBody.get(i);
            //if collide with the snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        //game over conditions for snake hitting the wall
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight){ //first condition to not pass the right side of the screen, and the second condition for the snake go down and not pass the screen
            gameOver = true;
        }

    }

     //game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //call the move function that will update the X and Y position of the snake
        repaint(); //every 100 millices call this actionPerformed, which will repaint (draw over and over again)
          
        if ( gameOver){
        gameLoop.stop();
       }
    }

    @Override
    public void keyPressed(KeyEvent e) { // defined keyPressed 
       if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
        velocityX = 0;
        velocityY = -1;
       } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
        velocityX = 0;
        velocityY = 1;
       }else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
        velocityX = -1;
        velocityY = 0;
       }else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
        velocityX = 1;
        velocityY = 0;
       }
      
    }

    //do not need
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

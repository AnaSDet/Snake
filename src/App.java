import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
    int boardWidth = 600; //600 pixels
    int boardHeigth = boardWidth;

    JFrame frame = new JFrame("Snake"); //name our frame
    frame.setVisible(true); //set visibility
    frame.setSize(boardWidth, boardHeigth); //set the size
    frame.setLocationRelativeTo(null); // this will open the window in the center of your screen
    frame.setResizable(false); //can't resize it
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the program will terminate when the user clicks on "x" button on the window


    //create an instance of the snake game
    SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeigth);
    frame.add(snakeGame);
    frame.pack(); //it will place the JPannel inside the frame with the full dimentions
    snakeGame.requestFocus(); //listening for the key presses. now snake can move

    }
}

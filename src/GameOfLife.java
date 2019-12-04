import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Board extends JPanel{
    int board[][] = new int[10][10];

    //Empty constructor - fills the board with 1 or 0 randomly
    public Board(){
        Random rand = new Random();
        for (int x = 0 ; x < 10 ; x++ ){
            for (int y = 0 ; y < 10 ; y++){
                board[x][y] = rand.nextInt(2);
            }
        }
    }

    //paint the 'board game' according to the numbers in board
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int i = 0;
        int j = 0;

        for (int x = 0 ; x < 10 ; x++){
            for (int y = 0 ; y < 10 ; y++) {
                if (board[x][y] == 1)
                    g.setColor(Color.BLACK);
                else
                    g.setColor(Color.WHITE);
                g.fillRect(i, j, (width / 10), (height / 10));
                j += ( height / 10 );
            }
            i += (width / 10 );
            j = 0;
        }
    }

    //return the sum of live neighbors
    public int cellVal(int x, int y){
        int sum = 0;
        int minX, maxX, minY, maxY;
        minX = (x > 0)? x - 1: x ;
        maxX = (x < 9)? x + 1 : x ;
        minY = (y > 0)? y - 1: y;
        maxY = (y < 9)? y + 1 : y;

        for (int i = minX; i <= maxX; i++){
            for (int j = minY; j <= maxY; j++)
                sum += board[i][j];
            }
        sum -= board[x][y] ;
        return sum;
    }

    //update board to the next generation
    public void nextGen(){
        int thisVal;
        for (int x = 0 ; x < 10 ; x++ ){
            for (int y = 0 ; y < 10 ; y++){
                thisVal = this.cellVal(x, y);
                if ( (board[x][y] == 1) && ( (thisVal > 3) || (thisVal < 2) ))
                    board[x][y] = 0;
                if ( (board[x][y] == 0) && (thisVal == 3) )
                    board[x][y] = 1;
            }
        }
    }
}

public class GameOfLife {

    public static void main(String[] args){
        JFrame window = new JFrame();
        Board myBoard = new Board();
        window.add(myBoard);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);

        int input = JOptionPane.showConfirmDialog(null, "Continue to the next generation ?");
        while ( input == 0 ){
            myBoard.nextGen();
            myBoard.repaint();

            input = JOptionPane.showConfirmDialog(null, "Continue to the next generation ?");
        }
    }
}

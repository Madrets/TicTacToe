import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game in a very
 * simple GUI window.
 *
 * @author Lynn Marshall
 * @version November 8, 2012
 *
 * @author Emmanuel Elite
 * @version June 10, 2016
 */

public class TicTacToe implements ActionListener
{
    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " ";  // empty cell
    public static final String TIE = "T"; // game ended in a tie

    private String player;   // current player (PLAYER_X or PLAYER_O)
    private String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private int numFreeSquares; // number of squares still free
    private String board[][]; // 3x3 array representing the board
    private boolean state;  // true if there is a winner
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JLabel gameStatus; // displays game progress
    private JLabel stats; // displays game statistics
    private JMenuItem quitItem;
    private JMenuItem newGame;
    private JMenuItem changePlayer;
    private ImageIcon X;
    private ImageIcon O;
    private int xStats; // stats for player x
    private int oStats; // stats for player o
    private int tieStats; //stats for ties

    /**
     * Constructs a new Tic-Tac-Toe board and sets up the basic
     * JFrame containing a JTextArea in a JScrollPane GUI.
     */
    public TicTacToe()
    {
        board = new String[3][3];
        state = false;
        xStats = 0;
        oStats = 0;
        tieStats = 0;
        player = PLAYER_X;

        JFrame frame = new JFrame("TicTacToe");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // load the image files from the project directory
        X = new ImageIcon("x.png");
        O = new ImageIcon("o.png");

        // add buttons to the grid
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3)); // 3 x 3 grid

        button1 = new JButton("");
        buttonPanel.add(button1);

        button2 = new JButton("");
        buttonPanel.add(button2);

        button3 = new JButton("");
        buttonPanel.add(button3);

        button4 = new JButton("");
        buttonPanel.add(button4);

        button5 = new JButton("");
        buttonPanel.add(button5);

        button6 = new JButton("");
        buttonPanel.add(button6);

        button7 = new JButton("");
        buttonPanel.add(button7);

        button8 = new JButton("");
        buttonPanel.add(button8);

        button9 = new JButton("");
        buttonPanel.add(button9);

        /* add the buttons to the frame */
        contentPane.add(buttonPanel);

        // displays the current status of the game
        gameStatus = new JLabel("It's " + player + " turn");
        gameStatus.setHorizontalAlignment(JLabel.LEFT); // write from left to right
        contentPane.add(gameStatus);

        // displays the stats for the previous games
        stats = new JLabel("Number of X wins = " + xStats
                + "    Number of O wins = " + oStats
                + "    Number of ties = " + tieStats);

        stats.setHorizontalAlignment(JLabel.LEFT); // write from left to right

        // add the stats to the frame
        contentPane.add(stats);

        // creating the menu items
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar); // add menu bar to our frame

        JMenu fileMenu = new JMenu("Game"); // create a menu
        menubar.add(fileMenu); // and add to our menu bar

        newGame = new JMenuItem("New"); // create a menu item called "New Game"
        fileMenu.add(newGame); // and add to our menu

        quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
        fileMenu.add(quitItem); // and add to our menu

        changePlayer = new JMenuItem("Change Starting Player"); // create a menu item
        fileMenu.add(changePlayer); // and add to our menu

        // assign shortcuts to some menu items
        //final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        //newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        //quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));

        // listen for menu selections
        newGame.addActionListener(this);
        quitItem.addActionListener(this);
        changePlayer.addActionListener(this);

        // register buttons as listeners
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
        button8.addActionListener(this);
        button9.addActionListener(this);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit when we hit the "X"
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(550, 550);
        clearBoard();
    }

    /**
     *  Stops the currents game and starts a new game.
     */
    public void initialize()
    {
        clearBoard();
        player = PLAYER_X;
        state = false;

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);

        button1.setIcon(null);
        button2.setIcon(null);
        button3.setIcon(null);
        button4.setIcon(null);
        button5.setIcon(null);
        button6.setIcon(null);
        button7.setIcon(null);
        button8.setIcon(null);
        button9.setIcon(null);

        // displays the current status of the game
        gameStatus.setText("It's " + player + "'s turn");

        // displays the stats for the previous games
        stats.setText("Number of X wins = " + xStats
                + "    Number of O wins = " + oStats
                + "    Number of ties =  " + tieStats);
    }

    /**
     * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
     * and indicates no winner yet, 9 free squares and the current player is player X.
     */
    private void clearBoard()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        winner = EMPTY;
        numFreeSquares = 9;
        player = PLAYER_X;     // Player X always has the first turn.
    }


    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * int row of square just set
     * int col of square just set
     *
     * @return true if we have a winner, false otherwise
     */
    private boolean haveWinner(int row, int col)
    {
        // unless at least 5 squares have been filled, we don't need to go any further
        // (the earliest we can have a winner is after player X's 3rd move).

        if (numFreeSquares>4) return false;

        // Note: We don't need to check all rows, columns, and diagonals, only those
        // that contain the latest filled square.  We know that we have a winner
        // if all 3 squares are the same, as they can't all be blank (as the latest
        // filled square is one of them).

        // check row "row"
        if ( board[row][0].equals(board[row][1]) &&
                board[row][0].equals(board[row][2]) ) return true;

        // check column "col"
        if ( board[0][col].equals(board[1][col]) &&
                board[0][col].equals(board[2][col]) ) return true;

        // if row=col check one diagonal
        if (row==col)
            if ( board[0][0].equals(board[1][1]) &&
                    board[0][0].equals(board[2][2]) ) return true;

        // if row=2-col check other diagonal
        if (row==2-col)
            if ( board[0][2].equals(board[1][1]) &&
                    board[0][2].equals(board[2][0]) ) return true;

        // no winner yet
        return false;
    }



    /** This action listener is called when the user clicks on
     * any of the GUI's buttons.
     */
    public void actionPerformed(ActionEvent e)
    {
        int row = 9; int col = 9; // initialized for compilation
        Object o = e.getSource(); // get the action

        // see if it's a JButton
        if (o instanceof JButton) {

            JButton button = (JButton)o;

            ImageIcon img = new ImageIcon("x.png"); // initialization necessary for compilation

            // using the correct image
            if(player == PLAYER_X){
                img = X;
            } else if (player == PLAYER_O) {
                img = O;
            }

            if (button == button1) {
                row = 0; col = 0;
                button1.setIcon(img);
                button1.setEnabled(false);
            } else if (button == button2) {
                row = 0; col = 1;
                button2.setIcon(img);
                button2.setEnabled(false);
            } else if (button == button3) {
                row = 0; col = 2;
                button3.setIcon(img);
                button3.setEnabled(false);
            } else if (button == button4) {
                row = 1; col = 0;
                button4.setIcon(img);
                button4.setEnabled(false);
            } else if (button == button5) {
                row = 1; col = 1;
                button5.setIcon(img);
                button5.setEnabled(false);
            } else if (button == button6) {
                row = 1; col = 2;
                button6.setIcon(img);
                button6.setEnabled(false);
            } else if (button == button7) {
                row = 2; col = 0;
                button7.setIcon(img);
                button7.setEnabled(false);
            } else if (button == button8) {
                row = 2; col = 1;
                button8.setIcon(img);
                button8.setEnabled(false);
            } else if (button == button9) {
                row = 2; col = 2;
                button9.setIcon(img);
                button9.setEnabled(false);
            }

            board[row][col] = player;
            numFreeSquares--;

            if (haveWinner(row,col))
            {
                state = true;
                winner = player; // must be the player who just went

                if (winner == PLAYER_X)
                {
                    xStats++;
                } else if (winner == PLAYER_O)
                {
                    oStats++;
                }

                gameStatus.setText(player + " wins");

                // disable all buttons after a win
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
                button5.setEnabled(false);
                button6.setEnabled(false);
                button7.setEnabled(false);
                button8.setEnabled(false);
                button9.setEnabled(false);

            } else if(numFreeSquares==0) {
                winner = TIE; // board is full so it's a tie
                tieStats++;
                gameStatus.setText("It's a tie");

            } else {
                if (player == PLAYER_X)
                {
                    player = PLAYER_O;
                } else {
                    player = PLAYER_X;
                }
                gameStatus.setText("It's " + player + "'s turn"); // change player
            }

        } else { // it's a JMenuItem

            JMenuItem item = (JMenuItem)o;

            if (item == newGame) { // start new game
                initialize();
            } else if (item == quitItem) { // quit
                System.exit(0);
            } else if (item == changePlayer && numFreeSquares == 9) // change starting player
            {

                if (player == PLAYER_X)
                {
                    player = PLAYER_O;
                } else {
                    player = PLAYER_X;
                }

                gameStatus.setText("It's " + player + "'s turn"); // change player
            }
        }
    }
}

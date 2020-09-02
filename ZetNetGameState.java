
import java.io.Serializable;


/**
 * This class holds all the necessary information to represent the state of a
 * game of networked TicTacToe. It includes the method
 * apply(hub,sender,message), which modifies the state to reflect a message that
 * was received from a player. The protocol is that two types of messages from
 * client are understood. One is that a TicTacToe client sends the String
 * "newgame" as a message when it wants to start a new game. The other is that
 * when the user makes a mover into one of the squares on the board, the client
 * sends an array of two ints containing the row and column where the user
 * played. Note that to keep things simple, each time a game is started, this
 * class decides at random which of the two players will play 'X' and which will
 * play 'O'. X always makes the first move.
 */
public class ZetNetGameState implements Serializable
{

    // -------------- state variables recording the state of the game
    // -------------------

    public boolean playerDisconnected; // This is true if one of the two players
                                       // has left the game
                                       // The new state, with this value set to
                                       // true, is sent to
                                       // the other player as a signal that the
                                       // game is over. That
                                       // client will respond by ending the
                                       // program.

    public ZetNetGameModel gameModel = new ZetNetGameModel();

    public boolean gameInProgress; // True while a game is being played;
                                   // false before first game and between games.

//    public HumanZetNetPlayer guest1;
//    public HumanZetNetPlayer guest2;
    // The next three variables are meant for use while a game is in progress.
    // Note that the ID numbers of the players will always be 1 and 2.

    public int playerPlaying1; // The ID of the player who is playing X.

    public int playerPlaying2; // The ID of the player who is playing O.

    public int currentPlayer; // The ID of the player who is to make the next
                              // move.
    public int[] playerScores = {0, 0};

    // The next two variables are meant for use between games.

    public boolean gameEndedInTie; // Tells whether the game ended in a tie.

    public int winner; // The name of winner of the game that just ended, if it
                       // was not a tie.


    // ----------- the method that is called by the Hub to react to messages
    // from the players -----------

    /**
     * Respond to a message that was sent by one of the players to the hub. Note
     * that illegal messages (of the wrong type or coming at an illegal time)
     * are simply ignored. The messages that are understood are the string
     * "newgame" for starting a new game and an array of two ints giving the row
     * and column of a move that the user wants to make.
     * 
     * @param sender
     *            the ID number of the player who sent the message.
     * @param message
     *            the message that was received from that player.
     */
    public void applyMessage( int sender, Object message )
    {
        if ( gameInProgress && message instanceof ZetNetGameModel /* && sender == currentPlayer */ )
        {
            gameModel = (ZetNetGameModel)message;
        }
        else if(gameInProgress && message instanceof int[])
        {
            playerScores = (int[])(message);
        }
        else if ( !gameInProgress && message.equals( "newgame" ) )
        {
            startGame();
        }
    }


    /**
     * This package private method is called by the hub when the second player
     * connects. It's purpose is to start the first game.
     */
    void startFirstGame()
    {
        startGame();
    }


    private void startGame()
    {
        int xPlr = (Math.random() < 0.5)? 1 : 2;
        playerPlaying1 = xPlr;  // Will be 1 or 2.
        playerPlaying2 = 3 - xPlr;  // The other player ( 3 - 1 = 2, and 3 - 2 = 1 )
        currentPlayer = playerPlaying1;
        gameEndedInTie = false;
        winner = -1;
        gameInProgress = true;
    }


}

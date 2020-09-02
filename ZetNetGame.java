/**
 *  Top level class for the SET game program.
 *
 *  Copyright (C) 2004 by Maria Litvin, Gary Litvin, and
 *  Skylight Publishing.  All rights reserved.
 *  Teachers may make a limited number of copies of this file
 *  for noncommercial, face-to-face teaching purposes.
 *
 *  SET® is a registered trademark of SET Enterprises, Inc.
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.*;
import netgame.common.Client;

public class ZetNetGame extends JFrame implements Serializable
{
  private ZetNetTableDisplay board;        // A panel that displays the board.  The user
                                        // makes moves by clicking on this panel.
  public  ZetNetGameState state;
  private  HumanZetNetPlayer player1;
  private  HumanZetNetPlayer player2;
  private  JPanel controlPanel;
  private  JPanel buttonPanel;
  private  JButton startButton, pauseButton, resumeButton, newGameButton;
  private boolean paused;
  private ZetPlayer pausedPlayer;
  private JLabel message;  // Displays messages to the user about the status of the game.
  
  private int myID;        // The ID number that identifies the player using this window.

  public ZetClient connection;  // The Client object for sending and receiving 
                                       // network messages.
  
  private class JFrameGraphics extends JPanel{
      public void paint(Graphics g){
          g.drawString("Waiting for another player", 10, 10);
      }
  }
  
  
  /**
   * This class defines the client object that handles communication with the Hub.
   */
  public class ZetClient extends Client {

      /**
       * Connect to the hub at a specified host name and port number.
       */
      public ZetClient(String hubHostName,int hubPort) throws IOException {
          super(hubHostName, hubPort);
      }

      /**
       * Responds to a message received from the Hub.  The only messages that
       * are supported are ZetGameState objects.  When one is received,
       * the newState() method in the ZetWindow class is called.  To avoid
       * problems with synchronization, that method is called using
       * SwingUtilities.invokeLater() so that it will run in the GUI event thread.
       */
      protected void messageReceived(final Object message) {
          if (message instanceof ZetNetGameState) {
              SwingUtilities.invokeLater(new Runnable(){
                  public void run() {  // calls a method at the end of the ZetWindow class
                      newState( (ZetNetGameState)message ); 
                  }
              });
          }
      }

      /**
       * If a shutdown message is received from the Hub, the user is notified
       * and the program ends.
       */
      protected void serverShutdown(String message) {
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  JOptionPane.showMessageDialog(ZetNetGame.this, 
                          "Your opponent has disconnected.\nThe game is ended.");
                  System.exit(0);
              }
          });
      }
      
  }
  
  
  public class ZetNetTableDisplay extends JPanel
  implements java.util.Observer
{
 private ZetNetGameModel gameModel;

 private final int X_OFF = 10;
 private final int Y_OFF = 10;
 private final int X_PITCH = 105;
 private final int Y_PITCH = 70;
 private final int X_SIZE = 98;
 private final int Y_SIZE = 63;

 private Icon deckIcon = new ImageIcon("deck.jpg");

 private final int maxOpenCards = 21;

 public ZetNetTableDisplay()
 {
   setBackground(new Color(149, 183, 247));
 }

 public void update(java.util.Observable model, Object obj)
 {
   this.gameModel = (ZetNetGameModel)model;
   repaint();
 }

 public void paintComponent(Graphics g)
 {
   super.paintComponent(g);

   if(state == null || state.gameModel == null)
   {
       return;
   }
   
   this.gameModel = state.gameModel;
   int i, x, y;

   int[] pickedCards = gameModel.getPickedCards();
   ZetNetTable table = gameModel.getTable();

   for (int k = 0; k < pickedCards.length; k++)
   {
     i = pickedCards[k];
     if (i >= 0 && i < maxOpenCards)
     {
       x = X_OFF + (i / 3) * X_PITCH;
       y = Y_OFF + (i % 3) * Y_PITCH;
       drawPicked(g, x, y);
     }
   }

   for (i = 0; i < maxOpenCards; i++)
   {
     ZetCard card = table.getOpenCard(i);
     if (card != null)
     {
       x = X_OFF + (i / 3) * X_PITCH;
       y = Y_OFF + (i % 3) * Y_PITCH;
       drawCard(g, x, y, card);
     }
   }

   if (table.cardsInDeck() > 0)
   {
     x = X_OFF + X_PITCH / 2;
     y = Y_OFF + 3 * Y_PITCH;
     drawDeck(g, x, y, table.cardsInDeck());
   }
 }

 /**
  *  Returns the index of the open card that contains x, y.
  *  called from humanZetPlayer's mouse handler.
  *  @param x x-coordinate of the point.
  *  @param y y-coordinate of the point.
  *  @return the index of the open card that contains x, y, or -1 if (x, y) is not in a card.
  */
 public int getCardLocation(int x, int y)
 {
   x -= X_OFF;
   y -= Y_OFF;
   if (x % X_PITCH > X_SIZE || y % Y_PITCH > Y_SIZE)
     return -1;
   int i = x / X_PITCH * 3 + y / Y_PITCH;
   if (i >= 0 && i < maxOpenCards)
     return i;
   return -1;
 }

 //******************* Private drawing methods ***********************

 private void drawPicked(Graphics g, int x, int y)
 {
   g.setColor(Color.yellow);
   g.fillRoundRect(x + 4, y + 4, X_SIZE, Y_SIZE, 12, 12);
 }

 private void drawCard(Graphics g, int x, int y, ZetCard card)
 {
   g.setColor(Color.white);
   g.fillRoundRect(x, y, X_SIZE, Y_SIZE, 12, 12);
   g.setColor(Color.black);
   g.drawRoundRect(x, y, X_SIZE, Y_SIZE, 12, 12);

   switch (card.getNumber())
   {
     case 1:
     drawSymbol(g, x + X_SIZE / 2, y + Y_SIZE / 2, card);
     break;

     case 2:
     drawSymbol(g, x + X_SIZE / 2 - X_SIZE / 7, y + Y_SIZE / 2, card);
     drawSymbol(g, x + X_SIZE / 2 + X_SIZE / 7, y + Y_SIZE / 2, card);
     break;

     case 3:
     drawSymbol(g, x + X_SIZE / 2 - X_SIZE / 4 - 4, y + Y_SIZE / 2, card);
     drawSymbol(g, x + X_SIZE / 2, y + Y_SIZE / 2, card);
     drawSymbol(g, x + X_SIZE / 2 + X_SIZE / 4 + 4, y + Y_SIZE / 2, card);
     break;
   }
 }

 private void drawDeck(Graphics g, int x, int y, int nCards)
 {
   deckIcon.paintIcon(this, g, x, y);
   g.setColor(Color.black);
   g.drawString(String.valueOf(nCards), x + X_SIZE - 10, y + Y_SIZE);
 }

 private void drawSymbol(Graphics g, int x, int y, ZetCard card)
 {
   g.translate(x, y);
   switch(card.getShape())
   {
     case 1: drawOval(g, card); break;
     case 2: drawSquiggle(g, card); break;
     case 3: drawDiamond(g, card); break;
   }
   g.translate(-x, -y);
 }

 //********** red, green, and blue colors *****************

 private  Color darkColor[] =
   {new Color(255, 0, 0), new Color(33, 143, 20), new Color(51, 39, 97)};
 private  Color lightColor[] =
   {new Color(255, 170, 170), new Color(125, 236, 113), new Color(137, 121, 200)};

 private static final int OUTLINED = 1;
 private static final int SOLID = 2;
 private static final int STRIPED = 3;

 //********** Oval *****************

 private  final int oWidth = 20, oHeight = 42;
 private  final int[] xoLinePoints1 = {-4,-7,-8,-8,-8,-8,-8,-8,-7,-4};
 private  final int[] xoLinePoints2 = { 4, 7, 8, 8, 8, 8, 8, 8, 7, 4};
 private  final int yoLinePoint = -18;

 private void drawOval(Graphics g, ZetCard card)
 {
   int c = card.getColor() - 1;

   switch (card.getFill())
   {
     case OUTLINED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawRoundRect(-oWidth / 2, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawRoundRect(-oWidth / 2, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     break;

     case SOLID:

     g.setColor(darkColor[c]);
     g.fillRoundRect(-oWidth / 2 + 1, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     g.fillRoundRect(-oWidth / 2, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     break;

     case STRIPED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawRoundRect(-oWidth / 2, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawRoundRect(-oWidth / 2, -oHeight / 2, oWidth, oHeight, oWidth + 2, oWidth + 2);
     drawStripes(g, xoLinePoints1, xoLinePoints2, yoLinePoint);
     break;
   }
 }

 //********** Squiggle *****************

 private  final int[] xsPoints =
     { 7, 7, 8, 8, 9, 9, 8, 8, 3, 2, 1,-6,-9,-9,-8,-8,-7,-7,-6,-6,-7,
      -7,-7,-8,-8,-9,-9,-8,-8,-3,-2,-1, 6, 9, 9, 8, 8, 7, 7, 6, 6, 7};
 private  final int[] ysPoints =
     {  0, -1, -2, -3, -4,-11,-12,-15,-20,-20,-21,-21,-18,-13,-12,-11,-10, -9, -8, -2, -1,
        0,  1,  2,  3,  4, 11, 12, 15, 20, 20, 21, 21, 18, 13, 12, 11, 10,  9,  8,  2,  1};
 private  final int[] xsLinePoints1 = {-6,-7,-5,-4,-4,-5,-7,-7,-6,-3};
 private  final int[] xsLinePoints2 = { 3, 6, 7, 7, 5, 4, 4, 5, 7, 6};
 private  final int ysLinePoint = -18;

 private void drawSquiggle(Graphics g, ZetCard card)
 {
   int c = card.getColor() - 1;

   switch (card.getFill())
   {
     case OUTLINED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     break;

     case SOLID:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     g.fillPolygon(xsPoints, ysPoints, 42);
     break;

     case STRIPED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xsPoints, ysPoints, 42);
     drawStripes(g, xsLinePoints1, xsLinePoints2, ysLinePoint);
     break;
   }
 }

 //********** Diamond *****************

 private  final int[] xdPoints = {-10, 0, 10, 0};
 private  final int[] ydPoints = {0, -21, 0, 21};
 private  final int[] xdLinePoints1 = {-3,-4,-5,-6,-6,-5,-4,-3};
 private  final int[] xdLinePoints2 = { 3, 4, 5, 6, 6, 5, 4, 3};
 private  final int ydLinePoint = -14;

 private void drawDiamond(Graphics g, ZetCard card)
 {
   int c = card.getColor() - 1;

   switch (card.getFill())
   {
     case OUTLINED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     break;

     case SOLID:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     g.fillPolygon(xdPoints, ydPoints, 4);
     break;

     case STRIPED:

     g.translate(1, 0);
     g.setColor(lightColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     g.translate(-1, 0);
     g.setColor(darkColor[c]);
     g.drawPolygon(xdPoints, ydPoints, 4);
     drawStripes(g, xdLinePoints1, xdLinePoints2, ydLinePoint);
     break;
   }
 }

 //********** Stripes *****************

 private void drawStripes(Graphics g, int[] xs1, int[] xs2, int y)
 {
   for (int k = 0; k < xs1.length; k++)
   {
     g.drawLine(xs1[k], y, xs2[k], y);
     y += 4;
   }
 }
}

  /**
   * This method is called when a new game state is received from the hub.
   * It stores the new state in the instance variable that represents the
   * game state and updates the user interface to reflect the state.
   * Note that this method is called on the GUI event thread (using
   * SwingUtilitites.invokeLater()) to avoid synchronization problems.
   * (Synchronization is an issue when a method that manipulates the
   * GUI is called from a thread other than the GUI event thread.  In this
   * problem, there is also the problem that a message can actually be
   * received before the constructor has completed, which would lead to errors
   * in this method from uninitialized variables, if SwingUtilities.invokeLater()
   * were not used.)
   */
  private void newState(ZetNetGameState state) {
      if ( state.playerDisconnected ) {
          JOptionPane.showMessageDialog(this, "Your opponent has disconnected.\nThe game is ended.");
          System.exit(0);
      }
      this.state = state;
      initZetGameModelForPlayers();
      board.addMouseListener((MouseListener)player1);
      controlPanel.addKeyListener((KeyListener)player1);
      board.addMouseListener((MouseListener)player2);
      controlPanel.addKeyListener((KeyListener)player2);
      controlPanel.requestFocus();
      player1.setScore( state.playerScores[0] );
      player2.setScore( state.playerScores[1] );
      board.repaint();
      if ( state == null ) {
          return;  // haven't started yet -- waiting for 2nd player
      }
      else if ( ! state.gameInProgress ) {
//          setTitle("Game Over");
          if ( state.gameEndedInTie )
              message.setText("Game ended in tie. Click to start again.");
          else if (myID == state.winner)
              message.setText("You won!  Click to start a new game.");
          else
              message.setText("You lost.  Waiting for new game...");
      }
//      else {
//          if (myID == state.playerPlayingX)
//              setTitle("Player #1");
//          else
//              setTitle("Player #2");
//          if (myID == state.currentPlayer)
//              message.setText("Your move");
//          else
//              message.setText("Waiting for opponent's move");
//      }
  }

  public ZetNetGame(String hostName, int serverPortNumber) throws IOException
  {
    super("Set");
    setJMenuBar(new ZetNetMenu(this));

    connection = new ZetClient(hostName, serverPortNumber);
    myID = connection.getID();
    board = new ZetNetTableDisplay();
//    if(state == null || state.gameModel == null )
//    {
//        getContentPane().add(new JFrameGraphics());
//        setSize(300, 300);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setResizable(false); 
//        return;
//    }
        
    player1 = new HumanZetNetPlayer(this, null);
    player2 = new HumanZetNetPlayer(this, null);

    controlPanel = new JPanel();
    controlPanel.setBackground(new Color(13, 66, 164));
    ((FlowLayout)controlPanel.getLayout()).setAlignment(FlowLayout.LEFT);
    Box b1 = Box.createHorizontalBox();
    b1.add(Box.createHorizontalStrut(8));
    b1.add((JPanel)player1);
    b1.add(Box.createHorizontalStrut(9));
    b1.add((JPanel)player2);
    controlPanel.add(b1);

    

    startButton = new JButton(" Start ");
    pauseButton = new JButton(" Pause ");
    pauseButton.setEnabled(false);
    resumeButton = new JButton("Resume");
    resumeButton.setEnabled(false);
    newGameButton = new JButton("New Game");
    newGameButton.setEnabled(false);
    ActionListener a = new ControlButtonListener();
    startButton.addActionListener(a);
    pauseButton.addActionListener(a);
    resumeButton.addActionListener(a);
    newGameButton.addActionListener(a);

    buttonPanel = new JPanel();
    ((FlowLayout)buttonPanel.getLayout()).setAlignment(FlowLayout.RIGHT);
    buttonPanel.setBackground(new Color(13, 66, 164));
    Box b2 = Box.createHorizontalBox();
    b2.add(startButton);
    b2.add(Box.createHorizontalStrut(15));
    b2.add(pauseButton);
    b2.add(Box.createHorizontalStrut(15));
    b2.add(resumeButton);
    b2.add(Box.createHorizontalStrut(75));
    b2.add(newGameButton);
    b2.add(Box.createHorizontalStrut(5));
    buttonPanel.add(b2);

    Container contentPane = getContentPane();
    contentPane.add(board, BorderLayout.CENTER);
    contentPane.add(controlPanel, BorderLayout.NORTH);
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
  }
  private void initZetGameModelForPlayers()
  {
      player1.setZetGameModel( state.gameModel );
      player2.setZetGameModel( state.gameModel );
      
      state.gameModel.addObserver(board);
  }
  protected void newGame()
  {
    initZetGameModelForPlayers();
    player1.stop();
    player2.stop();

    state.gameModel.newGame();

    initZetGameModelForPlayers();
    player1.setScore(0);
    player2.setScore(0);
    connection.send( state.gameModel );
    paused = false;
    startButton.setEnabled(true);
    pauseButton.setEnabled(false);
    resumeButton.setEnabled(false);
    newGameButton.setEnabled(false);
  }

  
  protected void startGame()
  {
    paused = false;
    startButton.setEnabled(false);
    pauseButton.setEnabled(true);
    resumeButton.setEnabled(false);
    newGameButton.setEnabled(true);

    initZetGameModelForPlayers();
    startPlayers();
  }

  protected void stopGame()
  {
    paused = false;
    startButton.setEnabled(false);
    pauseButton.setEnabled(false);
    resumeButton.setEnabled(false);
    newGameButton.setEnabled(true);
    initZetGameModelForPlayers();
    player2.stop();
    player1.stop();
    JOptionPane.showMessageDialog(this, "Game over\n", "Game over",
                                              JOptionPane.PLAIN_MESSAGE);
  }

  protected void pauseGame()
  {
    paused = true;
    pauseButton.setEnabled(false);
    resumeButton.setEnabled( true ); //added
    initZetGameModelForPlayers();
    player2.stop();
  }

  protected void resumeGame()
  {
    paused = false;
    pauseButton.setEnabled(true);
    resumeButton.setEnabled(false);
//    playerDone(pausedPlayer);
  }

  protected void startPlayers()
  {
    // Make sure a Set is on the table:
    while (state.gameModel.findZet() == null)
      if (!state.gameModel.open3Cards())
      {
        stopGame();
        return;
      }
//    connection.send( state.gameModel );
    player1.start();
    player2.start();    
  }

  //***************** called by players **************************

  public void setActivePlayer(ZetPlayer p)
  {
    if (p == player2)
      player1.stop();
    else if (p == player1)
      player2.stop();
  }

  public void playerDone(ZetPlayer p)
  {
    if (paused)
    {
      pausedPlayer = p;
      resumeButton.setEnabled(true);
      return;
    }

    int score = p.getScore();

    if (state.gameModel.removeZet())
      score++;
    else
      score--;
    p.setScore(score);
    int[] scores = new int[2];
    if(p == player1) 
    {
        scores[0] = score;
        scores[1] = player2.getScore();
//       state.gameModel.setPlayer1Score(p.getScore()); 
    } 
    else if (p == player2) 
    {
        scores[1] = score;
        scores[0] = player1.getScore();
//        state.gameModel.setPlayer2Score(p.getScore());
    }
    connection.send(scores);
    startPlayers();
    controlPanel.requestFocus();
  }

  private class ControlButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      JButton b = (JButton)e.getSource();
      if (b == startButton)
        startGame();
      else if (b == pauseButton)
        pauseGame();
      else if (b == resumeButton)
        resumeGame();
      else if (b == newGameButton)
        newGame();
    }
  }
}

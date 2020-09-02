import java.io.Serializable;
import java.util.*;

/**
 * Represents a generic deck of cards.
 */


/*
 * 
 * Tips:
 * 
 * - Use an ArrayList<Card> to hold the cards. - Add and remove cards at the end
 * of the list. - Use Collections.shuffle and Collections.sort to shuffle and
 * sort the deck, or write your own methods. If you write your own, use
 * selection sort to sort and a similar algorithm to shuffle. Use Math.random().
 * - In the toString method, separate strings for individual cards with "\n".
 * 
 */

/**
 * Construct a deck that holds zetCards
 *
 * @author Kelly
 * @version May 21, 2018
 * @author Period: 4
 * @author Assignment: SetGame
 *
 * @author Sources: None
 */
public class Deck implements Serializable
{
    /**
     * an arraylist of deck that holds the zetCards
     */
    ArrayList<Card> deck;


    /**
     * Constructs an empty deck of cards.
     */
    public Deck()
    {
        deck = new ArrayList<Card>();
    }


    /**
     * Constructs an empty deck of cards with a given capacity.
     * 
     * @param the
     *            number of cards this deck can hold without expanding the
     *            ArrayList that holds the cards.
     */
    public Deck( int capacity )
    {
        deck = new ArrayList<Card>( capacity );
    }


    /**
     * Adds a given card at the top of this deck.
     * 
     * @param to
     *            be added
     */
    public void add( Card card )
    {
        deck.add( card );
    }


    /**
     * Returns the number of cards in this deck.
     * 
     * @return the number of cards in this deck.
     */
    public int getNumCards()
    {
        return deck.size();
    }


    /**
     * Indicates whether this deck is empty.
     * 
     * @return true if the deck is empty; false otherwise.
     */
    public boolean isEmpty()
    {
        return deck.isEmpty();
    }


    /**
     * Shuffles this deck.
     */
    public void shuffle()
    {
        Collections.shuffle( deck );
    }


    /**
     * Sorts this deck in acsending order of IDs.
     */
    public void sort()
    {

        Collections.sort( deck );
    }


    /**
     * Removes and returns the top card from this deck.
     * 
     * @return the top card.
     * @throws java.util.NoSuchElementException
     *             - if this deck is empty.
     */
    public Card takeTop()
    {
        if ( this.isEmpty() )
        {
            throw new NoSuchElementException();
        }
        else
        {
            return deck.remove( 0 );
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString() Returns a string representation of this
     * deck.
     */
    public String toString()
    {
        String s = "";
        for ( int i = 0; i < deck.size(); i++ )
        {
            s += deck.get( i ).toString() + " ";
        }
        return s;
    }
}
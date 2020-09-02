import java.io.Serializable;

/**
 * Provides static methods for finding sets.
 */

/*
 * 
 * Tips:
 * 
 * - Given a, b, c from {1, 2, 3}, they are all equal or all different if and
 * only if (a + b + c) % 3 == 0. This property can be used for testing three
 * cards for a set easily.
 * 
 * (For more mathematically inclined, the SET deck corresponds to the
 * 4-dimensional vector space over the algebraic field {0, 1, 2} with modulo 3
 * arithmetic. Three cards form a "set" if and only if the sum of the
 * corresponding vectors is 0.)
 * 
 * - isZet and findZet are static methods.
 * 
 * - in isZet, don't forget to check that the cards are not all the same.
 * 
 * - Note that an array passed to findZet can have nulls in some of the
 * elements.
 * 
 */

/**
 * creates a ZetAnalyzer that provides static methods for finding sets.
 *
 * @author Kelly
 * @version May 21, 2018
 * @author Period: 4
 * @author Assignment: SetGame
 *
 * @author Sources: None
 */
public class ZetAnalyzer implements Serializable
{

    /**
     * constructor: creates a zetAnalyzer
     */
    public ZetAnalyzer()
    {

    }


    /**
     * Returns true if card1, card2, and card3 form a "set," false otherwise.
     * 
     * @param card1
     *            the first card.
     * @param card2
     *            the second card.
     * @param card3
     *            the third card.
     * @return true if card1, card2, and card3 form a "set"; false otherwise.
     */
    public static boolean isZet( ZetCard card1, ZetCard card2, ZetCard card3 )
    {
        if ( card1 == null || card2 == null || card3 == null )
        {
            return false;
        }
        if ( card1.equals( card2 ) || card1.equals( card3 ) || card2.equals( card3 ) )
        {
            return false;
        }
        else if ( ( card1.getNumber() + card2.getNumber() + card3.getNumber() ) % 3 != 0 )
        {
            return false;
        }
        else if ( ( card1.getColor() + card2.getColor() + card3.getColor() ) % 3 != 0 )
        {
            return false;
        }
        else if ( ( card1.getFill() + card2.getFill() + card3.getFill() ) % 3 != 0 )
        {
            return false;
        }
        else if ( ( card1.getShape() + card2.getShape() + card3.getShape() ) % 3 != 0 )
        {
            return false;
        }
        else
            return true;
    }


    /**
     * Finds the indices of three cards from a given array that form a "set".
     * cards may contain nulls.
     * 
     * @param cards
     *            an array of "set" cards.
     * @return An array that contains the indices of the three cards that form a
     *         "set," or null if no sets are found.
     */
    public static int[] findZet( ZetCard[] cards )
    {
        ZetCard c1;
        ZetCard c2;
        ZetCard c3;
        if ( cards.length == 0 )
        {
            return null;
        }
        for ( int i = 0; i < cards.length; i++ )
        {
            c1 = cards[i];
            for ( int x = i + 1; x < cards.length; x++ )
            {
                c2 = cards[x];
                for ( int y = x + 1; y < cards.length; y++ )
                {
                    c3 = cards[y];
                    if ( isZet( c1, c2, c3 ) )
                    {
                        int[] set = { i, x, y };
                        return set;
                    }
                }
            }
        }
        return null;
    }
}

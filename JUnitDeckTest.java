import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class JUnitDeckTest
{

    @Test
    public void Constructor()
    {
        Deck d = new Deck();    
    }
    
    @Test
    public void Add()
    {
        Deck d = new Deck();
        Card c = new Card(10);
        d.add( c );
        assertTrue(d.deck.contains( c ));
    }
    
    @Test 
    public void GetNumCards()
    {
        Deck d = new Deck(4);
        d.add( new Card(1) );
        d.add( new Card(2) );
        d.add( new Card(3) );
        assertEquals(3, d.getNumCards());
    }
    
    @Test
    public void IsEmpty()
    {
        Deck d = new Deck();
        assertTrue( d.isEmpty() == true);
        Deck d1 = new Deck(10);
        d1.add( new Card(1) );
        assertTrue( d1.isEmpty() == false);
    }
    
    @Test
    public void shuffle()
    {
        Deck d = new Deck(4);
        d.add( new Card(1) );
        d.add( new Card(2) );
        d.add( new Card(3) );
        d.add( new Card(4) );
        Deck d2 = new Deck(4);
        d.add( new Card(1) );
        d.add( new Card(2) );
        d.add( new Card(3) );
        d.add( new Card(4) );
        d.shuffle();
        assertFalse(d.equals( d2 ));      
    }
    
    @Test
    public void sort()
    {
        Deck d = new Deck(4);
        d.add( new Card(4) );
        d.add( new Card(2) );
        d.add( new Card(1) );
        d.add( new Card(3) );
        d.sort();
        assertEquals(1, d.takeTop().getId());       
    }
    
    
    @Test
    public void takeTop() throws NoSuchElementException
    {
        Deck d = new Deck(4);
        d.add( new Card(1) );
        d.add( new Card(2) );
        d.add( new Card(3) );
        d.add( new Card(4) );
        assertEquals(1, d.takeTop().getId());
    }
    
    @Test(expected = Exception.class)
    public void takeTopExceptionThrown() throws Exception
    {
        Deck d = new Deck(4);
        d.takeTop();
    }
    
    @Test
    public void toStringTest()
    {
        Deck d = new Deck();
        d.add( new Card(1) );
        d.add( new Card(2) );
        d.add( new Card(3) );
        d.add( new Card(4) );
        assertNotNull( d.toString() );
    }
    

}

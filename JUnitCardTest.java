import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitCardTest
{

    @Test
    public void testConstructor()
    {
        Card c = new Card(100);
        assertEquals( c.getId(), 100);
    }
    @Test
    public void testCompareToEqual()
    {
        Card c1 = new Card(100);
        Card c2 = new Card(100);
        assertTrue( c1.compareTo( c2 ) == 0);
    }
    @Test
    public void testCompareToGreater()
    {
        Card c1 = new Card(100);
        Card c2 = new Card(50);
        assertTrue( c1.compareTo( c2 ) > 0);
    }
    @Test
    public void testCompareToLess()
    {
        Card c1 = new Card(100);
        Card c2 = new Card(200);
        assertTrue( c1.compareTo( c2 ) < 0);
    }
    @Test
    public void testGetId()
    {
        Card c = new Card(100);
        assertEquals( c.getId(), 100);
    }
    @Test
    public void testToString()
    {
        Card c = new Card(100);
        assertEquals( c.toString(), "Card [id = " + c.getId() + "]");
    }
    

}

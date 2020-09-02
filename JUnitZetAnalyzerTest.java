import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class JUnitZetAnalyzerTest
{
    @Test
    public void Constructor()
    {
        new ZetAnalyzer();
    }
    
    @Test
    public void isZetTest()
    {
        //all cards are the same
        ZetCard c1 = new ZetCard(1, 2, 3, 4, 5);
        ZetCard c2 = new ZetCard(1, 2, 3, 4, 5);
        ZetCard c3 = new ZetCard(1, 2, 3, 4, 5);
        assertFalse( ZetAnalyzer.isZet(c1, c2, c3) );
        
        //different number
        ZetCard c4 = new ZetCard(1, 2, 1, 1, 1);
        ZetCard c5 = new ZetCard(2, 2, 2, 2, 2);
        ZetCard c6 = new ZetCard(3, 2, 3, 3, 3);
        assertTrue( ZetAnalyzer.isZet( c4, c5, c6 ));
        
        //different shape
        ZetCard c7 = new ZetCard(1, 1, 3, 1, 1);
        ZetCard c8 = new ZetCard(2, 2, 3, 2, 2);
        ZetCard c9 = new ZetCard(3, 3, 3, 3, 3);
        assertTrue( ZetAnalyzer.isZet( c7, c8, c9 ));
        
        //different fill
        ZetCard c10 = new ZetCard(1, 1, 1, 1, 1);
        ZetCard c11 = new ZetCard(2, 2, 2, 1, 2);
        ZetCard c12 = new ZetCard(3, 3, 3, 1, 3);
        assertTrue( ZetAnalyzer.isZet( c10, c11, c12));
        
        //different color
        ZetCard c13 = new ZetCard(1, 1, 1, 1, 3);
        ZetCard c14 = new ZetCard(2, 2, 2, 2, 3);
        ZetCard c15 = new ZetCard(3, 3, 3, 3, 3);
        assertTrue( ZetAnalyzer.isZet( c13, c14, c15));
        
        //different everything
        ZetCard c16 = new ZetCard(1, 1, 1, 1, 1);
        ZetCard c17 = new ZetCard(2, 2, 2, 2, 2);
        ZetCard c18 = new ZetCard(3, 3, 3, 3, 3);
        assertTrue( ZetAnalyzer.isZet( c16, c17, c18));        
    }
    
    @Test
    public void findZetTest()
    {
        ZetCard[] cards = new ZetCard[7];
        assertEquals( null, ZetAnalyzer.findZet( cards ) ); //when the deck is empty
        cards[0] = new ZetCard(1, 1, 1, 1, 1);
        cards[1] = null;
        cards[2] = new ZetCard(2, 2, 2, 2, 2);
        cards[3] = new ZetCard(3, 3, 3, 3, 3);
        cards[4] = new ZetCard(1, 2, 3, 1, 2);
        cards[5] = new ZetCard(3, 2, 1, 3, 2);
        cards[6] = new ZetCard(2, 1, 3, 2, 1);
        assertArrayEquals( new int[] {0, 2, 3}, ZetAnalyzer.findZet( cards )); 
                        
    }
}

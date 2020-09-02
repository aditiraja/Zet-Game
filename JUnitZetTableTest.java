import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitZetTableTest
{

    @Test
    public void ZetTableConstructor()
    {
        ZetTable z = new ZetTable();
        int x = z.toString().lastIndexOf( '\n' );
        assertNotNull(z);
    }
    
    @Test
    public void ZetTableCardsInDeck()
    {
        ZetTable z = new ZetTable();
//        assertEquals(z.getDeck().getNumCards(), z.cardsInDeck());
    }
    
    @Test 
    public void ZetTableGetOpenCard()
    {
        ZetTable z = new ZetTable();
//        assertNull(z.getOpenCard( z.getZetArray().length));
    }
    
    @Test
    public void ZetTableEnoughOpen()
    {
        ZetTable z = new ZetTable();
        assertTrue(z.enoughOpen());
    }
    
    @Test
    public void ZetTableFindZet()
    {
        ZetTable z = new ZetTable();
//        assertEquals(z.findZet(), ZetAnalyzer.findZet( z.getZetArray() ));
    }
    
    @Test
    public void ZetTableOpen3Cards()
    {
        
    }
    
    @Test
    public void ZetTableToString()
    {
        ZetTable z = new ZetTable();
        
        assertNotNull(z.toString());
    }
}

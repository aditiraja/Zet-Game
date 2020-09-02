import static org.junit.Assert.*;

import org.junit.Test;


public class JUnitZetDeckTest
{

    @Test
    public void ZetDeckConstructor()
    {
        ZetDeck d = new ZetDeck();
        int count = 0;
        while ( !d.isEmpty() )
        {
            d.takeTop();
            count++;
        }

        assertEquals( count, 81 );
    }

}

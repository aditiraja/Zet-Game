import static org.junit.Assert.*;

import org.junit.Test;


public class JUnitZetCardTest
{
    private int ID = 100;

    private int number = 1;

    private int shape = 1;

    private int fill = 1;

    private int color = 1;


    @Test
    public void testZetCardConstructor()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );
        assertTrue( zc.getId() == ID );
        assertTrue( zc.getNumber() == number );
        assertTrue( zc.getShape() == shape );
        assertTrue( zc.getFill() == fill );
        assertTrue( zc.getColor() == color );
    }


    @Test
    public void testGetColor()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );

        assertEquals( zc.getColor(), color );
    }


    @Test
    public void testGetFill()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );

        assertEquals( zc.getFill(), fill );
    }


    @Test
    public void testGetNumber()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );

        assertEquals( zc.getNumber(), number );
    }


    @Test
    public void testGetShape()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );

        assertEquals( zc.getShape(), shape );
    }


    @Test
    public void testToString()
    {
        ZetCard zc = new ZetCard( ID, number, shape, fill, color );
        String toStr = zc.toString();

        assertTrue( toStr.contains( "ZetCard [color = " + "red" + "] [fill = " + "outlined"
            + "] [number = " + number + "] [shape = " + "oval" + "]" ) );
    }
}

import java.io.Serializable;

/**
 * Represents a SET game player.
 *
 * Copyright (C) 2004 by Maria Litvin, Gary Litvin, and Skylight Publishing. All
 * rights reserved. Teachers may make a limited number of copies of this file
 * for noncommercial, face-to-face teaching purposes.
 *
 * SET® is a registered trademark of SET Enterprises, Inc.
 *
 */

public class ZetCard extends Card implements Serializable
{
    /**
     * An integer that represents the number of shapes in the card.
     */
    private int number;

    /**
     * An integer that represents the shape that shows up on the card.
     */
    private int shape;

    /**
     * An integer that represents whether the shape is completely filled,
     * striped, or has no fill.
     */
    private int fill;

    /**
     * An integer that represents the color of the shape on the card
     */
    private int color;


    /**
     * The constructor for ZetCard that initializes all of the fields.
     * 
     * @param iD
     *            an int that represents the id number of the card
     * @param n
     *            an int that represents that the number of shapes on the card
     * @param s
     *            an int that represents the shape on the card
     * @param f
     *            an int that represents the fill of the shape on the card
     * @param c
     *            an int that represents the color of the shape of the card
     */
    public ZetCard( int iD, int n, int s, int f, int c )
    {
        super( iD );
        number = n;
        shape = s;
        fill = f;
        color = c;
    }


    /**
     * The getColor method returns an integer representation of the color of the
     * shape of the card
     * 
     * @return an int that represents the color
     */
    public int getColor()
    {
        return color;
    }


    /**
     * The getFill method returns an integer representation of the fill of the
     * shape of the card
     * 
     * @return an int that represents the fill
     */
    public int getFill()
    {
        return fill;
    }


    /**
     * The getNumber method returns the number of shapes on the card
     * 
     * @return an int that represents the number of shapes
     */
    public int getNumber()
    {
        return number;
    }


    /**
     * The getShape method returns an integer representation of the shape of the
     * card
     * 
     * @return an int that represents the shape
     */
    public int getShape()
    {
        return shape;
    }


    /**
     * The toString method returns a String representation of the ZetCard
     * 
     * @return a String that represents the different attributes of the ZetCard
     */
    public String toString()
    {
        String color, shape, fill;
        if ( getColor() == 1 )
        {
            color = "red";
        }
        else if ( getColor() == 2 )
        {
            color = "green";
        }
        else
        {
            color = "blue";
        }

        if ( getFill() == 1 )
        {
            fill = "outlined";
        }
        else if ( getFill() == 2 )
        {
            fill = "solid";
        }
        else
        {
            fill = "striped";
        }

        if ( getShape() == 1 )
        {
            shape = "oval";
        }
        else if ( getShape() == 2 )
        {
            shape = "squiggle";
        }
        else
        {
            shape = "diamond";
        }
        return "ZetCard [color = " + color + "] [fill = " + fill + "] [number = " + getNumber()
            + "] [shape = " + shape + "]";

    }
}

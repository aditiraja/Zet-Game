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

public class Card implements Comparable<Card>, Serializable
{
    /**
     * A private int that represents the id number of the card.
     */
    private int iD;


    /**
     * The constructor that sets the card's id number to an integer
     * 
     * @param id
     *            an int that represents the card's id number
     */
    public Card( int id )
    {
        iD = id;
    }


    /**
     * The compareTo method compares two cards and returns the difference
     * between the id numbers.
     * 
     * @param other
     *            a Card object
     * @return an int that represents the difference between the two cards' id
     *         number
     */
    public int compareTo( Card other )
    {
        return iD - other.getId();
    }


    /**
     * The equals method compares two cards and returns whether or not they are
     * equal
     * 
     * @param other
     *            a Java object
     * @return a boolean that returns whether or not two cards are equal
     */
    public boolean equals( java.lang.Object other )
    {
        if ( other == null && !( other instanceof Card ) )
        {
            return false;
        }

        return this.compareTo( (Card)other ) == 0;
    }


    /**
     * The getId methdo returns the id number of the card
     * 
     * @return an int that represents the id of the card
     */
    public int getId()
    {
        return iD;
    }


    /**
     * The toString method returns a string representation of the card
     * 
     * @return a String that returns the card and it's id
     */
    public String toString()
    {
        return "Card [id = " + getId() + "]";
    }
}
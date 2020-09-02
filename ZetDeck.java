import java.io.Serializable;
import java.util.*;

public class ZetDeck extends Deck implements Serializable
{
  
  public ZetDeck()
  {
      super();
      int count = 1;
      for (int a = 1; a < 4; a++)
      {
          for (int b = 1; b < 4; b++)
          {
              for (int c = 1; c < 4; c++)
              {
                  for (int d = 1; d < 4; d++)
                  {
                      this.add( new ZetCard(count, a, b, c, d) );
                      count++;
                  }
              }
          }
      }

  }
  public ZetDeck(int capacity)
  {
      super(capacity);
  }
  
  
}

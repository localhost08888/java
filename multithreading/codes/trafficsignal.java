class TrafficSignal extends Thread
 {
  private String signalColor;

  public TrafficSignal(String initialColor) 
  {
      this.signalColor = initialColor;
  }

  @Override
  public void run()
   {
      while (true) 
      {
          switch (signalColor)
           {
              case "RED":
                  System.out.println("Signal is RED. Stop!");
                  sleepFor(5000); // Red for 5 seconds
                  signalColor = "GREEN";
                  break;
              case "GREEN":
                  System.out.println("Signal is GREEN. Go!");
                  sleepFor(5000); // Green for 5 seconds
                  signalColor = "YELLOW";
                  break;
              case "YELLOW":
                  System.out.println("Signal is YELLOW. Get Ready!");
                  sleepFor(2000); // Yellow for 2 seconds
                  signalColor = "RED";
                  break;
          }
      }
  }

  private void sleepFor(int milliseconds)
   {
      try 
      {
          Thread.sleep(milliseconds);
      } 
      catch (InterruptedException e)
       {
          System.err.println("Signal interrupted: " + e.getMessage());
      }
  }
}

public class trafficsignal
 {
  public static void main(String[] args) 
  {
      TrafficSignal signal = new TrafficSignal("RED");
      signal.start();
  }
} 
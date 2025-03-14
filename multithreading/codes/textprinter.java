class PrintText_Thread extends Thread 
{
  private String text;
  private int n;

  public PrintText_Thread(String text, int n) 
  {
      this.text = text;
      this.n = n;
  }

  //@Override
  public void run() 
  {
      for (int i = 0; i < n; i++)
       {
          System.out.println(text);
      }
  }
}

public class textprinter
 {
  public static void main(String args[])
   {
      // Create and start three threads
      PrintText_Thread thread1 = new PrintText_Thread("I am in FY", 10); 
      // arguments like parameterized constructor
      PrintText_Thread thread2 = new PrintText_Thread("I am in SY", 20);
      PrintText_Thread thread3 = new PrintText_Thread("I am in TY", 30);

      thread1.start();
      thread2.start();
      thread3.start();
  }
}
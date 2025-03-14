 
class MessageThread extends Thread 
{
    private String message; 
    public MessageThread(String message) 
     {
        this.message = message;  
    }

    @Override   
    public void run() 
    {
        while (true)
         {
            System.out.println(Thread.currentThread().getName() + " - " + message);
            try 
            {
                Thread.sleep(2000); // Sleep for 2 second
            } 
            catch (InterruptedException e)
             {
                e.printStackTrace();
            }
        }
    }
}

public class message
{
public static void main(String[] args) 
    {
        
        new MessageThread("Hello from Thread 1").start();
        new MessageThread("Hello from Thread 2").start();
    }
}
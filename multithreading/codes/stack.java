import java.util.Stack;

class SharedStack 
{
    private Stack<Integer> stack = new Stack<>();
    private final int CAPACITY = 5;

    public synchronized void push(int value, String threadName) 
    {
        while (stack.size() >= CAPACITY)
         {
            try 
            {
                wait(); // Wait if stack is full
            } 
            catch (InterruptedException e)
             {
                e.printStackTrace();
            }
        }
        stack.push(value);
        System.out.println(threadName + " pushed: " + value);
        notifyAll(); // Notify other threads
    }

    public synchronized int pop() 
    {
        while (stack.isEmpty())
         {
            try {
                wait(); // Wait if stack is empty
            } catch (InterruptedException e)
             {
                e.printStackTrace();
            }
        }
        int value = stack.pop();
        System.out.println("Popper popped: " + value);
        notifyAll(); // Notify other threads
        return value;
    }
}

class Pusher extends Thread 
{
    private SharedStack stack;
    private String threadName;

    public Pusher(SharedStack stack, String name) 
    {
        this.stack = stack;
        this.threadName = name;
    }

    public void run()
     {
        for (int i = 1; i <= 5; i++)
         {
            stack.push(i, threadName);
            try 
            {
                Thread.sleep(500); // Simulating delay
            } 
            catch (InterruptedException e)
             {
                e.printStackTrace();
            }
        }
    }
}


class Popper extends Thread
 {
    private SharedStack stack;

    public Popper(SharedStack stack)
     {
        this.stack = stack;
    }

    public void run() 
    {
        for (int i = 1; i <= 10; i++)
         { // More pops than pushes
            stack.pop();
            try
             {
                Thread.sleep(700); // Simulating delay
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }
}

public class stack
 {
    public static void main(String[] args) {
        SharedStack stack = new SharedStack();
        
        Pusher pusher1 = new Pusher(stack, "Pusher1");
        Pusher pusher2 = new Pusher(stack, "Pusher2");
        Popper popper = new Popper(stack);

        pusher1.start();
        pusher2.start();
        popper.start();
    }
}

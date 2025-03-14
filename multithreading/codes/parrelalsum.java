import java.util.Random;

class SumTask extends Thread
 {
    private int[] array;
    private int start, end;
    private long partialSum;

    public SumTask(int[] array, int start, int end) 
    {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public long getPartialSum() 
    {
        return partialSum;
    }

    //@Override
    public void run() 
    {
        partialSum = 0;
        for (int i = start; i < end; i++)
         {
            partialSum += array[i];
        }
    }
}

public class parrelalsum
{
    public static void main(String[] args) throws InterruptedException
     {
        int[] array = new int[1000];
        Random random = new Random();

        // Fill the array with random integers
        for (int i = 0; i < array.length; i++)
         {
            array[i] = random.nextInt(100); // Random numbers between 0 and 99
        }

        // Create and start 10 threads
        SumTask[] threads = new SumTask[10];
        for (int i = 0; i < 10; i++)
         {
            int start = i * 100;
            int end = start + 100;
            threads[i] = new SumTask(array, start, end);
            threads[i].start();
        }
 
        long totalSum = 0;
        for (SumTask thread : threads)
         {
            thread.join();
            totalSum += thread.getPartialSum();
        }
 
        double average = (double) totalSum / array.length;
 
        System.out.println("Total Sum: " + totalSum);
        System.out.println("Average: " + average);
    }
}
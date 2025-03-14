import java.io.File;

class FileWatcherThread extends Thread 
{
    private String fileName;

    public FileWatcherThread(String fileName) 
    {
        this.fileName = fileName;
    }

    @Override
    public void run()
     {
        File file = new File(fileName);
        while (true)
         {
            if (file.exists()) 
            {
                System.out.println("File " + fileName + " exists.");
                break; // Exit the thread once the file is found
            }
             else
             {
                System.out.println("File " + fileName + " does not exist. Checking again in 3 seconds...");
                try 
                {
                    Thread.sleep(3000); // Wait for 5 seconds before checking again
                }
                 catch (InterruptedException e)
                 {
                    System.err.println("Thread interrupted: " + e.getMessage());
                    break;
                }
            }
        }
    }
}

public class filewatcher
{
    public static void main(String[] args)
     {
        if (args.length == 0)
         {
            System.out.println("Usage: java FileWatcher <file1> <file2> ...");
            return;
        }

        for (String fileName : args) 
        {
            new FileWatcherThread(fileName).start();
        }
    }
}

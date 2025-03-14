import java.io.*;
import java.util.*;

class FileSearchThread extends Thread 
{
    private File file;
    private String searchString;

    public FileSearchThread(File file, String searchString) 
    {
        this.file = file;
        this.searchString = searchString;
    }

    @Override
    public void run() 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
         {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null)
            {
                if (line.contains(searchString))
                 {
                    System.out.println(file.getName() + " - Line " + lineNumber + ": " + line);
                }
                lineNumber++;
            }
        }
         catch (IOException e) 
        {
            System.err.println("Error reading file: " + file.getName());
            e.printStackTrace();
        }
    }
}

public class filesearch {
    public static void main(String[] args) 
    {
        // Accept the search string from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string to search: ");
        String searchString = scanner.nextLine();

        // Get all .txt files in the current directory
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No text files found in the current folder.");
            return;
        }

        // Create and start a separate thread for each file
        for (File file : files) {
            new FileSearchThread(file, searchString).start();
        }
    }
}

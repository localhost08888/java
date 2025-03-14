import java.io.*;
import java.util.*;

public class collection5
  {
    public static void main(String[] args) 
       {
        if (args.length < 1)
         {
            System.out.println("Please specify the file name as a command-line argument.");
            return;
        }

        String fileName = args[0];
        List<String> lines = new ArrayList<>();

        // Read the file into the list
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                lines.add(line);
            }
        } catch (IOException e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit)
         {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. Insert line");
            System.out.println("2. Delete line");
            System.out.println("3. Append line");
            System.out.println("4. Modify line");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) 
            {
                case 1: // Insert line
                    System.out.print("Enter the line to insert: ");
                    String newLine = scanner.nextLine();
                    System.out.print("Enter the position (1-based index): ");
                    int position = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    if (position > 0 && position <= lines.size() + 1) 
                    {
                        lines.add(position - 1, newLine);
                        System.out.println("Line inserted successfully.");
                    }
                    else
                   {
                        System.out.println("Invalid position.");
                    }
                    break;

                case 2:  
                    System.out.print("Enter the position (1-based index) of the line to delete: ");
                    int deletePosition = scanner.nextInt();
                    scanner.nextLine();  

                    if (deletePosition > 0 && deletePosition <= lines.size()) 
                    {
                        lines.remove(deletePosition - 1);
                        System.out.println("Line deleted successfully.");
                    } 
                    else
                     {
                        System.out.println("Invalid position.");
                    }
                    break;

                case 3:  
                    System.out.print("Enter the line to append: ");
                    String appendLine = scanner.nextLine();
                    lines.add(appendLine);
                    System.out.println("Line appended successfully.");
                    break;

                case 4: 
                    System.out.print("Enter the position (1-based index) of the line to modify: ");
                    int modifyPosition = scanner.nextInt();
                    scanner.nextLine();  

                    if (modifyPosition > 0 && modifyPosition <= lines.size()) 
                    {
                        System.out.print("Enter the new content: ");
                        String modifiedLine = scanner.nextLine();
                        lines.set(modifyPosition - 1, modifiedLine);
                        System.out.println("Line modified successfully.");
                    }
                     else 
                    {
                        System.out.println("Invalid position.");
                    }
                    break;

                case 5: 
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
                    {
                        for (String line : lines)
                        {
                            writer.write(line);
                            writer.newLine();
                        }
                        System.out.println("File saved successfully.");
                    } 
                    catch (IOException e) 
                    {
                        System.out.println("Error writing to file: " + e.getMessage());
                    }
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
} 
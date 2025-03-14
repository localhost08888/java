import java.util.Hashtable;
import java.util.Scanner;

public class collection3
 {
  public static void main(String[] args)
    {
      Hashtable<String,Double> details=new Hashtable<String,Double> ();

      details.put("nilesh",85.5);
      details.put("vishal",75.5);
      details.put("omkar",25.5);
      details.put("jaya",35.5); 
      details.put("mila",95.5);

      System.out.println("Student Details:");
        for(String key:details.keySet())
        {
          System.out.println("Name: " + key + "-- Percentage: " + details.get(key));
        }
        
     
        Scanner sc= new Scanner(System.in);
        System.out.print("\nEnter the name of the student to search: ");
        String studentName = sc.nextLine();
        
        if(details.containsKey(studentName))
          {
            double per=details.get(studentName);
            System.out.println("Percentage of "+studentName+ " is " + per);
          }
        else
          {
            System.out.println("student not found");
          }
    }
} 
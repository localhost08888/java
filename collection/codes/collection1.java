import java.util.Scanner;
import java.util.TreeSet;

public class collection1
{
  public  static void main(String arg[])
  {
      Scanner sc =new Scanner(System.in);

      TreeSet<Integer> x= new TreeSet<>();   // declaring new tree setof integers

      System.out.println("enter the number of elements");
      int n =sc.nextInt();

      System.out.println("enter "+n+" numbers-");
      for(int i=0;i<n;i++ )
        {
          int num=sc.nextInt();
          x.add(num);
        }

      System.out.println("Numbers in sorted order are"+ x);
  //-----------------------------------------------------------------------
      System.out.println("Enter element to search");
      int ele=sc.nextInt();
        if(x.contains(ele))
        {
          System.out.println("Element found");
        }
        else
        {
          System.out.println("Element not found");
        } 
      sc.close();
  }
} 
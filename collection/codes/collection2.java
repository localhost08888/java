import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class collection2 
{
    public static void main(String[] args) 
    {
        LinkedList<String> clr = new LinkedList<>();
        clr.add("red");
        clr.add("blue");
        clr.add("yellow");
        clr.add("orange");
 
        System.out.println("List elements by iterator function:");
        Iterator<String> x = clr.iterator(); 

        while (x.hasNext()) 
        {
            String color = x.next();  
            System.out.println(color);
        }
 
        System.out.println("List elements in reverse order by ListIterator:");
        ListIterator<String> y = clr.listIterator(clr.size()); 
        while (y.hasPrevious())
         {
            System.out.println(y.previous());
        }

        LinkedList<String> newColors = new LinkedList<>();
        newColors.add("pink");
        newColors.add("green");
 
        int index = clr.indexOf("blue") + 1; 
        clr.addAll(index, newColors);  

        
        System.out.println("\nFinal list of colors after insertion:");
        for (String color : clr) 
         {
            System.out.println(color);
         }
    }
} 
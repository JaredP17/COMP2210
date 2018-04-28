public class SelectorDriver {

   public static void main(String[] args) {
      int[] a = {2, 8, 7, 3, 4};
      int[] b = {5, 9, 1, 7, 3};
      int[] c = {8, 7, 6, 5, 4};
      int[] d = {2, 8, 8, 7, 3, 3, 4};
      
      // min method
      System.out.println("\nmin:");
      System.out.println(Selector.min(a));
      System.out.println(Selector.min(b));
      System.out.println(Selector.min(c));
      System.out.println(Selector.min(d));
      
      // max method
      System.out.println("\nmax:");
      System.out.println(Selector.max(a));
      System.out.println(Selector.max(b));
      System.out.println(Selector.max(c));
      System.out.println(Selector.max(d));
      
      // kmin method
      System.out.println("\nkmin:");
      System.out.println(Selector.kmin(a, 1));
      System.out.println(Selector.kmin(b, 3));
      System.out.println(Selector.kmin(c, 5));
      System.out.println(Selector.kmin(d, 3));
      
      // kmax method
      System.out.println("\nkmax:");
      System.out.println(Selector.kmax(a, 1));
      System.out.println(Selector.kmax(b, 3));
      System.out.println(Selector.kmax(c, 5));
      System.out.println(Selector.kmax(d, 3));
      
      // range method
      System.out.println("\nrange:");
      int[] a2 = Selector.range(a, 1, 5);
      for (int i : a2) {
         System.out.print(i + " ");
      }
     
      System.out.println(); 
      int[] b2 = Selector.range(b, 3, 5);
      for (int i : b2) {
         System.out.print(i + " ");
      }
      
      System.out.println(); 
      int[] c2 = Selector.range(c, 4, 8);
      for (int i : c2) {
         System.out.print(i + " ");
      }
      
      System.out.println(); 
      int[] d2 = Selector.range(d, 3, 7);
      for (int i : d2) {
         System.out.print(i + " ");
      }
      
      //ceiling method
      System.out.println("\n\nceiling:");
      System.out.println(Selector.ceiling(a, 1));
      System.out.println(Selector.ceiling(b, 7));
      System.out.println(Selector.ceiling(c, 0));
      System.out.println(Selector.ceiling(d, 5));
      
      //floor method
      System.out.println("\nfloor:");
      System.out.println(Selector.floor(a, 6));
      System.out.println(Selector.floor(b, 1));
      System.out.println(Selector.floor(c, 9));
      System.out.println(Selector.floor(d, 5));
   }

}
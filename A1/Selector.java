import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Jared Porter (jrp0036@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  2016-05-22
*
*/
public final class Selector {
   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @return  min value
    * @param   a 
    */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int min = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
      } 
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @return  max value
    * @param   a 
    */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int max = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      } 
      return max;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    * @return  kth min
    * @param   a array
    * @param   k int
    */
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int[] b = Arrays.copyOf(a, a.length);
      Arrays.sort(b);
      
      if (k == 1) {
         return b[0];
      }
      
      int count = 1;
      
      
      for (int i = 1; i < b.length; i++) {
         if (b[i] != b[i - 1]) {
            count++;
         }
         
         if (count == k) { 
            return b[i];
         }
      }
      
      throw new IllegalArgumentException("The array parameter is not valid");
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    * @return  kth max
    * @param   a array
    * @param   k int
    */
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int[] b = Arrays.copyOf(a, a.length);
      Arrays.sort(b);
      
      if (k == 1) {
         return b[b.length - 1];
      }
      
      int count = 1;
      
      
      for (int i = b.length - 2; i >= 0; i--) {
         if (b[i] != b[i + 1]) {
            count++;
         }
         
         if (count == k) { 
            return b[i];
         }
         
         
      }
      
      throw new IllegalArgumentException("The array parameter is not valid");
   
   }



   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    * @return  range values
    * @param   a array
    * @param   low int
    * @param   high int
    */
   public static int[] range(int[] a, int low, int high) {
      
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("The array parameter is not valid");
      } 
      
      int count = 0;
     
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            count++;
         }
      }
      
      int[] b = new int[count];
      
      if (count == 0) {
         return b;
      }
      
      else {
         int j = 0;
         for (int i = 0; i < a.length; i++) {
         
            if (a[i] >= low && a[i] <= high) {
               b[j] = a[i];
               j++;
            }
         }
      
         return b;
      }
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    * @return  ceiling 
    * @param   a array
    * @param   key int
    */
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int count = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key) {
            count++;
         }
      } 
      
      int[] b = new int[count]; 
      
      int j = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key) {
            b[j] = a[i];
            j++;
         }
      }
      
      return Selector.min(b);
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    * @return  floor
    * @param   a array
    * @param   key int
    */
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("The array parameter is not valid");
      }
      
      int count = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key) {
            count++;
         }
      } 
      
      int[] b = new int[count]; 
      
      int j = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key) {
            b[j] = a[i];
            j++;
         }
      }
      
      return Selector.max(b);
   }

}

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArraySet.java.
 *
 * Provides an implementation of the Set interface using an
 * array as the underlying data structure. Values in the array
 * are kept in ascending natural order and, where possible,
 * methods take advantage of this. Many of the methods with parameters
 * of type ArraySet are specifically designed to take advantage
 * of the ordered array implementation.
 *
 * @author Jared Porter (jrp0036@auburn.edu)
 * @author	Dean Hendrix (dh@auburn.edu)
 * @version	2016-06-27
 *
 */
public class ArraySet<T extends Comparable<? super T>> implements Set<T> {

   ////////////////////////////////////////////
   // DO NOT CHANGE THE FOLLOWING TWO FIELDS //
   ////////////////////////////////////////////
   T[] elements;
   int size;

   ////////////////////////////////////
   // DO NOT CHANGE THIS CONSTRUCTOR //
   ////////////////////////////////////
   /**
    * Instantiates an empty set.
    */
   @SuppressWarnings("unchecked")
   public ArraySet() {
      elements = (T[]) new Comparable[1];
      size = 0;
   }
   
   @SuppressWarnings("unchecked")
   private ArraySet(T[] e) {
    e = (
   }

   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements,
    *               false otherwise.
    */
   public boolean isEmpty() {
      return size == 0;
   }

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this ArraySet.
    *
    * @return a string representation of this ArraySet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }

    /**
     * Ensures the collection contains the specified element. Elements are
     * maintained in ascending natural order at all times. Neither duplicate nor
     * null values are allowed.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */     
   public boolean add(T element) {
      if (size() == elements.length) {
         adjust();
      }
      
      if (element == null || contains(element)) {
         return false;
      }
      
      int i = 0;
      
      while (i <= size()) {
         if (elements[i] == null || element.compareTo(elements[i]) < 0) {
            move(i);
            elements[i] = element;
            size++;
            return true;
         }
         
         i++;
      }
      
      return false;
   }

 /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. Elements are maintained in ascending natural
     * order at all times.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
   @SuppressWarnings("unchecked")
   public boolean remove(T element) {
      if (isEmpty() || !contains(element)) {
         return false;
      }
      
      int e = -1;
      
      for (int i = 0; i < size; i++) {
         if (elements[i].compareTo(element) == 0) {
            e = i;
         }
      }
      
      while (e < size()) {
         elements[e] = elements[e + 1];
         e++;
      }
      
      elements[size - 1] = null;
      size--;
      
      if (size() < elements.length / 4) {
         T[] a = (T[]) new Comparable[(elements.length / 2)];
         System.arraycopy(elements, 0, a, 0, (elements.length / 2));
         elements = a;
      }
      
      return true;
   }
   
 /**
     * Searches for specified element in this collection.
     *
     * @param   element  The element whose presence in this collection
     *                   is to be tested.
     * @return  true if this collection contains the specified element,
     *               false otherwise.
     */
   public boolean contains(T element) { 
           
      if (isEmpty()) {
         return false;
      }
      int low = 0;
      int high = size() - 1;
      
      while (high >= low) {
         int mid = low + (high - low) / 2;
         
         if (elements[mid] != null){
         
            if (elements[mid].compareTo(element) == 0) {
               return true;
            }
            else if (elements[mid].compareTo(element) < 0) {
               low = mid + 1;
            }
            else if (elements[mid].compareTo(element) > 0) {
               high = mid - 1;
            }
            else {
               high--;
            }
         }
      }
      
      return false;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   @Override
   public boolean equals(Set<T> s) {
      if (s== null || size() != s.size()) {
         return false;
      }
      
      int count = 0;
      Iterator<T> scan = s.iterator();
      
      while (scan.hasNext()) {
         T obj = scan.next();
         if (contains(obj)) {
            count++;
         }
      }
      
      if (count == size()) {
         return true;
      }
      
      return false;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   public boolean equals(ArraySet<T> s) {
      if (size() != s.size()) {
         return false;
      }
      
      int count = 0;
      Iterator<T> scan = s.iterator();
      
      while (scan.hasNext()) {
         T obj = scan.next();
         if (contains(obj)) {
            count++;
         }
      }
      
      if (count == size()) {
         return true;
      }
      
      return false;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   public Set<T> union(Set<T> s) {
      ArraySet<T> u = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         u.add(elements[i]);
      }
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         u.add(scan.next());
      }
      
      return u;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   public Set<T> union(ArraySet<T> s) {
      ArraySet<T> u = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         u.add(elements[i]);
      }
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         u.add(scan.next());
      }
      
      return u;
   }


   /**
    * Returns a set that is the intersection of this set
    * and the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      ArraySet<T> i = new ArraySet<T>();
      
      Iterator<T> scan = s.iterator();
      
      while (scan.hasNext()) {
         T o = scan.next();
         if (contains(o)) {
            i.add(o);
         }
      }
      
      return i;
   }

    /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(ArraySet<T> s) {
      ArraySet<T> i = new ArraySet<T>();
      
      Iterator<T> scan = s.iterator();
      
      while (scan.hasNext()) {
         T o = scan.next();
         if (contains(o)) {
            i.add(o);
         }
      }
      
      return i;
   }

   
   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      ArraySet<T> c = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         T o = elements[i];
         if (!s.contains(o)) {
            c.add(o);
         }
      }   
      
      return c;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(ArraySet<T> s) {
      ArraySet<T> c = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         T o = elements[i];
         if (!s.contains(o)) {
            c.add(o);
         }
      }   
      
      return c;
   }


   /**
    * Returns an iterator over the elements in this ArraySet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> iterator() {
   
      // ALMOST ALL THE TESTS DEPEND ON THIS METHOD WORKING CORRECTLY.
      // MAKE SURE YOU GET THIS ONE WORKING FIRST.
      // HINT: JUST USE THE SAME CODE/STRATEGY AS THE ARRAYBAG CLASS
      // FROM LECTURE. THE ONLY DIFFERENCE IS THAT YOU'LL NEED THE
      // ARRAYITERATOR CLASS TO BE NESTED, NOT TOP-LEVEL.
      Iterator<T> ascend = 
         new Iterator<T>() {
            private int i = 0;
            
            public boolean hasNext() {
               return i < size;
            }
            
            public T next() {
               return elements[i++];
            }
            
            public void remove() { 
            }
         };
         
      return ascend;
   }

   /**
    * Returns an iterator over the elements in this ArraySet.
    * The elements are returned in descending order.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> descendingIterator() {
      Iterator<T> descend =
         new Iterator<T>() {
            private int i = size() - 1;
            
            public boolean hasNext() {
               return i >= 0;
            }
            
            public T next() {
               return elements[i--];
            }
            
            public void remove() {
            }
         };
         
      return descend;
   }

  /**
    * Returns an iterator over the members of the power set
    * of this ArraySet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      Iterator<Set<T>> power =
         new Iterator<Set<T>>() {
            int count = 0;
            int size = (int) Math.pow(2, size());
            
            public boolean hasNext() {
               return count < size;
            }
            
            public Set<T> next() {
               int i = 0;
               String j = Integer.toBinaryString(i); 
               
               for (count = 0; count < size; count++) {
                  if (j.charAt(count) == '1') {
                     return elements[i];
                  }
                  i++;
               }
               return null; 
            }
            
            public void remove() {
            }
         };
         
      return power;
   }
   
   /** 
    * Increases size of elements[] in add().
    *
    */
   @SuppressWarnings("unchecked")
   private void adjust() {
      T[] inc = (T[]) new Comparable[elements.length * 2];
      
      for (int i = 0; i < elements.length; i++) {
         inc[i] = elements[i];
      }
      
      elements = inc;
   }
   
   /**
    * Move method.
    */
   private void move(int index) {
      for (int i = size(); i > index; i--) {
      
         elements[i] = elements[i - 1];
         
      }
   }  
}
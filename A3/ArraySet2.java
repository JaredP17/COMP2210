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
 * @param <T>
 *
 */
public class ArraySet2<T extends Comparable<? super T>> implements Set<T> {

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
   public ArraySet2() {
      elements = (T[]) new Comparable[1];
      size = 0;
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
    * Ensures the collection contains the specified element.
    * No specific order can be assumed. Neither duplicate nor null
    * values are allowed.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      if (size() == elements.length) {
         expandSize();
      }
      else if (element == null || contains(element)) {
         return false;
      }
      
      int i = 0;
      while (i <= size()) {
         if (elements[i] == null || element.compareTo(elements[i]) < 0) {
            shift(i);
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
    * from the collection.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   @SuppressWarnings("unchecked")
   public boolean remove(T element) {
      if (isEmpty()) {
         return false;
      }
      else if (!contains(element)) {
         return false;
      }
      int search = -1;
      for (int i = 0; i < size && search == -1; i++) {
         if (elements[i].compareTo(element) == 0) {
            search = i;
         }
      }
      while (search < size()) {
         elements[search] = elements[search + 1];
         search++;
      }
      elements[size - 1] = null;
      size--;
      if (size() < elements.length * .25) {
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
      //Use binary search to acheieve O(logN)      
      if (isEmpty()) {
         return false;
      }
      int low = 0;
      int high = size() - 1;
      while (high >= low) {
         int mid = low + (high - low) / 2;
         if (elements[mid] != null && elements[mid].compareTo(element) == 0) {
            return true;
         }
         else if (elements[mid] != null && elements[mid].compareTo(element) < 0) {
            low = mid + 1;
         }
         else if (elements[mid] != null && elements[mid].compareTo(element) > 0) {
            high = mid - 1;
         }
         else {
            high--;
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
    * @param s in equals
    */
   public boolean equals(Set<T> s) {
      if (s == null) {
         throw new NullPointerException("s is null");
      }
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
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    * @param s in equals
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
    * @param s in union
    */
   public Set<T> union(Set<T> s) {
      ArraySet<T> union = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         union.add(elements[i]);
      }
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         union.add(scan.next());
      }
      return union;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    * @param s in union
    */
   public Set<T> union(ArraySet<T> s) {
      ArraySet<T> union = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         union.add(elements[i]);
      }
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         union.add(scan.next());
      }
      return union;
   }


   /**
    * Returns a set that is the intersection of this set
    * and the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    * @param s in intersection
    */
  
   public Set<T> intersection(Set<T> s) {
      ArraySet<T> inters = new ArraySet<T>();
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         T obj = scan.next();
         if (contains(obj)) {
            inters.add(obj);
         }
      }
      return inters;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    * @param s in intersection
    */
   public Set<T> intersection(ArraySet<T> s) {
      ArraySet<T> inters = new ArraySet<T>();
      
      Iterator<T> scan = s.iterator();
      while (scan.hasNext()) {
         T obj = scan.next();
         if (contains(obj)) {
            inters.add(obj);
         }
      }
      return inters;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    * @param s in complement
    */
   public Set<T> complement(Set<T> s) {
      ArraySet<T> comp = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         T obj = elements[i];
         if (!s.contains(obj)) {
            comp.add(obj);
         }
      }   
      return comp;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    * @param s in complement
    */
   public Set<T> complement(ArraySet<T> s) {
      ArraySet<T> comp = new ArraySet<T>();
      
      for (int i = 0; i < size(); i++) {
         T obj = elements[i];
         if (!s.contains(obj)) {
            comp.add(obj);
         }
      }   
      return comp;
   }


   /**
    * Returns an iterator over the elements in this ArraySet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> iterator() {
      Iterator<T> step = 
         new Iterator<T>() {
            private int index = 0;
            
            @Override
            public boolean hasNext() {
               return index < size && elements[index] != null;
            }
            
            @Override
            public T next() {
               return elements[index++];
            }
            
            @Override
            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      return step;
   }

   /**
    * Returns an iterator over the elements in this ArraySet.
    * The elements are returned in descending order.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> descendingIterator() {
      Iterator<T> backStep =
         new Iterator<T>() {
            private int index = size();
            
            @Override
            public boolean hasNext() {
               return index > 0 && elements[index] != null;
            }
            
            @Override
            public T next() {
               return elements[index - 1];
            }
            
            @Override
            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      return backStep;
   }

   /**
    * Returns an iterator over the members of the power set
    * of this ArraySet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return null;
   }
   
   // Methods added for functionality
   
   //Used to expand size of elements[] in add()
   @SuppressWarnings("unchecked")
   private void expandSize() {
      T[] large = (T[]) new Comparable[elements.length * 2];
      for (int i = 0; i < elements.length; i++) {
         large[i] = elements[i];
      }
      elements = large;
   }
   
   private void shift(int place) {
      int i = size();
      while (i > place) {
         elements[i] = elements[i - 1];
         i--;
      }
   }  
}
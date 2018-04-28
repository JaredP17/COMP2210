import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Jared Porter (jrp0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2016-06-30
 *
 */
public class LinkedSet<T extends Comparable<? super T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the lisn. */
   Node front;
   Node rear;

   /** The number of nodes in the lisn. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSen.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSen.
    *
    * @return a string representation of this LinkedSet
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
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }

   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      
      if (element == null || contains(element)) { // Checks if null and for duplicates
         return false;
      }
     
     // Add element 
      Node n = new Node(element);
      
      if (isEmpty()) {
         front = n;
         rear = front;
      }
      
      else {
      
         rear.next = n;
         rear.next.prev = rear;
         rear = rear.next;
      }
      
      size++;
      
      return true;
   }

   /**  
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (element == null)
         return false;
   
      Node n = locate(element);
      
      if (n == null) {
         return false;
      }
      
      else if (n == front) {
         front = front.next;
         front.prev = null;
      }
      
      else { 
         n.prev.next = n.next;
         if (n.next != null) {
            n.next.prev = n.prev;
         }
      }
      
      size--;
      return true;
   }
   


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
     
      return locate(element) != null;
   }


   /**
    * Tests for equality between this set and the parameter sen.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
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
    * Tests for equality between this set and the parameter sen.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if (s == null || (this.size() != s.size())) {
         return false;
      }
      
      Iterator<T> a = this.iterator();
      Iterator<T> b = s.iterator();
      
      while (a.hasNext()) {
         T obj = a.next();
         T obj2 = b.next();
         if (obj != obj2) {
            return false;
         }
      }
      return true;
   }


   /**
    * Returns a set that is the union of this set and the parameter sen.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      LinkedSet<T> union = new LinkedSet<T>();
     //Create iterators
      Iterator<T> a = this.iterator();
      Iterator<T> z = s.iterator();
     //Loop through each set and add
      while (a.hasNext()) {
         T obj = a.next();
         union.add(obj);
      }
      while (z.hasNext()) {
         T obj = z.next();
         union.add(obj);
      }
      return union;
   }


   /**
    * Returns a set that is the union of this set and the parameter sen.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      if(s==null)
         throw new NullPointerException();
          
      LinkedSet<T> set = new LinkedSet<T>();
   
      Node n = front;
   
      while(n != null)
      {
         set.add(n.element);
         n = n.next;
      
      }
   
      Iterator<T> i = s.iterator();
   
      while(i.hasNext()) {
         set.add(i.next());
      }
      return set;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter sen.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      LinkedSet<T> i = new LinkedSet<T>();
      
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
    * the parameter sen.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      LinkedSet<T> i = new LinkedSet<T>();
      
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
    * Returns a set that is the complement of this set and the parameter sen.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      if ( s == null) {
         throw new NullPointerException();
      }
   
      LinkedSet<T> c = new LinkedSet<T>();
   
      Node n = front;
   
      while(n != null)
      {   
         if(!s.contains((T)n.element))
            c.add((T)n.element);
      
         n = n.next;
      
      }
   
      return c;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter sen.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      if ( s == null) {
         throw new NullPointerException();
      }
   
      LinkedSet<T> c = new LinkedSet<T>();
   
      Node n = front;
   
      while(n != null)
      {   
         if(!s.contains((T)n.element))
            c.add((T)n.element);
      
         n = n.next;
      
      }
   
      return c;
   }


   /**
    * Returns an iterator over the elements in this LinkedSen.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      Iterator<T> fwd = 
         new Iterator<T>() {
            private Node n = front;
            
            @Override
            public boolean hasNext() {
               return (n != null);
            }
            
            @Override
            public T next() {
               T loc = n.element;
               n = n.next;
               return loc;
            }
            
            @Override
            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      return fwd;
   }


   /**
    * Returns an iterator over the elements in this LinkedSen.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      Iterator<T> bkwd =
         new Iterator<T>() {
            private Node n = rear;
            
            @Override
            public boolean hasNext() {
               return (n != null);
            }
            
            @Override
            public T next() {
               T loc = n.element;
               n = n.prev;               
               return loc;
            }
            
            @Override
            public void remove() {
            
               throw new UnsupportedOperationException();
               
            }
         };
      return bkwd;
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSen. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {  
      return new PwrIterator(front, size);
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.
   
   private int length(Node n) {
      Node p = n;
      int len = 0; 
      while (p != null) {
         len++;
         p = p.next;
      }
      return len; 
   }
   
   private Node locate(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.equals(element)) {
            return n;
         }
         n = n.next;
      }
      return null;
   }

   ////////////////////
   // Nested classes //
   ////////////////////
   
   private class PwrIterator implements Iterator<Set<T>>
   {
      Node n;
      int c;
      int current;
      int bit = 0;
   
      public PwrIterator(Node front,int size) {
         n = front;
         c = size;
         current = 0;
         bit = 0;
      
      }
   
      public boolean hasNext()
      {
         return (current < (int)Math.pow(2,c));
      
      }
   
      public Set<T> next()
      {
         Set<T> s =new LinkedSet<T>();
      
         int m = 1;
      
         for(int k1 = 0; k1 < c; k1++) {
            if((bit & m)!=0)
            {
               s.add(n.element);
               n = n.next;
            }
            else
               n = n.next;
         
         }
      
         current++;
      
         bit = 0;
      
         n = front;
      
         return s;
      
      }
   
      public void remove() {
         throw new UnsupportedOperationException();
      }
   
   }

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked lisn.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after in.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}
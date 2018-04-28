import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Collections;

/**
 * Doublets.java
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a TreeSet of Strings.
 *
 * @author Jared Porter (jrp0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2016-07-18
 */
public class Doublets implements WordLadderGame {

   ////////////////////////////////////////////
   // DON'T CHANGE THE FOLLOWING TWO FIELDS. //
   ////////////////////////////////////////////

   // A word ladder with no words. Used as the return value for the ladder methods
   // below when no ladder exists.
   List<String> EMPTY_LADDER = new ArrayList<>();

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   TreeSet<String> lexicon;


   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next().toLowerCase();
            ////////////////////////////////////////////////
            // Add code here to store str in the lexicon. //
            ////////////////////////////////////////////////
            lexicon.add(str);
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   ///////////////////////////////////////////////////////////////////////////////
   // Fill in implementations of all the WordLadderGame interface methods here. //
   ///////////////////////////////////////////////////////////////////////////////
  
   private TreeSet<String> path = new TreeSet<>();
   
   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1;
      }
      
      if (str1.equals(str2)) {
         return 0;
      }
      
      int dist = 0;
      char[] s1 = str1.toCharArray();
      char[] s2 = str2.toCharArray();
      
      for (int i = 0; i < s1.length; i++) {
         if (s1[i] != s2[i]) {
            dist++;
         }
      }
      
      return dist;
   }


  /**
   * Returns a word ladder from start to end. If multiple word ladders exist,
   * no guarantee is made regarding which one is returned. If no word ladder exists,
   * this method returns an empty list.
   *
   * Depth-first search with backtracking must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a word ladder from start to end
   */
   public List<String> getLadder(String start, String end) {
      if (!isWord(start) || !isWord(end) || start.length() != end.length()) {
         return EMPTY_LADDER;
      }
      List<String> ladder = new ArrayList<>();
      
      if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      }
      
      Deque<String> q = new ArrayDeque<>();
      path.clear();
      
      // DFS
      visit(start);
      q.addFirst(start);
      
      while (!q.isEmpty()) { 
         String current = q.peekFirst();
         String neighbor = getFirstUnvisited(current);
         
         if (neighbor != null) {
            q.addFirst(neighbor);
            if (neighbor.equals(end)) {
               while (!q.isEmpty()) {
                  ladder.add(q.removeLast());
               }   
            }
            
         }
         
         else {
            q.removeFirst();
         }
         
      }
      
      return ladder;
   
      
     
      //return EMPTY_LADDER;
   }

  /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   
   public List<String> getMinLadder(String start, String end) {
      if (!isWord(start) || !isWord(end) || start.length() != end.length()) {
         return EMPTY_LADDER;
      }
      
      Deque<Node> q = new ArrayDeque<>();
      List<String> minLadder = new ArrayList<>();
      path.clear();
      
      if (start.equals(end)) {
         minLadder.add(start);
         return minLadder;
      }
      // BFS
   
      // enque start
      q.add(new Node(start, null));
      
      // visit start
      visit(start);
      
      // while queue is not empty
      while (!q.isEmpty()) {
      
          // deque current
         Node current = q.pop();
         List<String> neighbor = getNeighbors(current.element);
          // for each neighbor of current
         for (String n : neighbor) {
                // if neighbor is not visited
            if (!visited(n)) {
                   // visit neighbor
               visit(n);
                   
                // current is previous of neighbor
               Node cur = new Node(n , current);
                
                // enque neighbor
               q.add(cur);
            
                // if neighbor is end
               if (n.equals(end)) {
                      // add all previous nodes from end to start to the path
                  Node walk = cur;
                  while (walk != null) {
                     minLadder.add(walk.element);
                     walk = walk.prev;
                  } 
                  
               }       
            }
         }
        
      }
      
      Collections.reverse(minLadder);
      return minLadder;
       // return path
    
      //return EMPTY_LADDER;
   }


   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {
      List<String> neighbors = new ArrayList<>();
      
      for (int i = 0; i < word.length(); i++) {
      // letters must reset to the original word
      // at the start of this loop
         char[] letters = word.toCharArray();
         
         for (char ch = 'a'; ch <= 'z'; ch++) {
            letters[i] = ch;
            String wrd = new String(letters);
            
            if (isWord(wrd) && !wrd.equals(word)) 
               neighbors.add(wrd);
         }
      }
      
      return neighbors;
   }


   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount() {
     
      return lexicon.size();
   }


   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
      return lexicon.contains(str.toLowerCase());
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
      if (sequence == null || sequence.isEmpty()) {
         return false;
      }
      
      String text = null;
      for (String next : sequence) {
         if (text != null) {
            if (getHammingDistance(text, next) != 1)
               return false;
         }
         
         if (!isWord(next)) {
            return false;
         }
         
         text = next;
      }
      
      return true;
   }

////////////////////
// NESTED CLASSES //
////////////////////
   private class Node {
      private String element;
      private Node prev;
      
      private Node() {
         this(null, null);
      }
      
      private Node(String e, Node p) { 
         element = e;
         prev = p;
      }
   }

/////////////////////
// PRIVATE METHODS //
/////////////////////

   
   private void visit(String str) {
      path.add(str);
   }
   
   private boolean visited(String str) {
      return path.contains(str);
   }
   
   private String getFirstUnvisited(String str) {
      List<String> neighbor = getNeighbors(str);
      for (String e : neighbor) {
         if (!visited(e)) {
            visit(e);
            return e;
         }
      }
      return null;
   }
}


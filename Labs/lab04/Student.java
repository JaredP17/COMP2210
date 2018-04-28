/**
 * Student.java
 * A class to represent student data, for the
 * purpose of illustrating order by Comparable
 * and Comparator.
 *
 * @author   Dean Hendrix (dh@auburn.edu)
 * @version  2015-08-28
 *
 */
public class Student implements Comparable<Student> {
   private String fname;
   private String lname;
   private int    section;

  /** Creates a new student. */
   public Student(String fn, String ln, int sec) {
      fname   = fn;
      lname   = ln;
      section = sec;
   }

  /** Returns this student's first name. */
   public String getFirstName() {
      return fname;
   }

  /** Returns this student's last name. */
   public String getLastName() {
      return lname;
   }

  /** Returns this student's section. */
   public int getSection() {
      return section;
   }

   /**
    * Implement compareTo so that students are ordered in the
    * following way: in ascending order of last name, then in
    * ascending order of first name, and then in ascending order
    * of section.
    */
   @Override
   public int compareTo(Student s) {
      String ln = this.lname.toUpperCase();
      String fn = this.fname.toUpperCase();
      String ln2 = s.lname.toUpperCase();
      String fn2 = s.fname.toUpperCase();
      
      if (!ln.equals(ln2))
         return ln.compareTo(ln2);
         
      else if (!fn.equals(fn2))
         return fn.compareTo(fn2);
         
      else
         return this.section - s.section;
   }

  /** Returns a string representation of this student. */
   @Override
   public String toString() {
      return section + ", " + lname + ", " + fname;
   }
}

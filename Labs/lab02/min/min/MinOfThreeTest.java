import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MinOfThreeTest {

   @Test
   public void min1_test1() {
      Assert.assertEquals(1, MinOfThree.min1(3, 1, 2));
   }
   
   @Test
   public void min2_test1() {
      Assert.assertEquals(1, MinOfThree.min2(3, 1, 2));
   }
   
   @Testpublic void test_min1_2() {   
      int[] a = {0, 0, 1};   
      int expected = 0;   
      int actual = MinOfThree.min1(a[0], a[1], a[2]);   
      Assert.assertEquals(expected, actual);
   }
}

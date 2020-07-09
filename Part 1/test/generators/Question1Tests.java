package generators;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Question1Tests {

    @Test
    public void testFixedIntegerSequenceGenerator1() {
      final IntegerGenerator generator = new FixedIntegerSequenceGenerator();
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(0, generator.next().intValue());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(1, generator.next().intValue());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(2, generator.next().intValue());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(3, generator.next().intValue());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(4, generator.next().intValue());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals(5, generator.next().intValue());
    }

    @Test
    public void testFixedIntegerSequenceGenerator2() {
      final IntegerGenerator generator = new FixedIntegerSequenceGenerator();
      final int limit = 20;
      for (int i = 0; i < limit; i++) {
        Assert.assertTrue(generator.hasNext());
        Assert.assertEquals(i, generator.next().intValue());
      }
      Assert.assertFalse(generator.hasNext());
    }

    @Test
    public void testFixedIntegerSequenceGenerator3() {
      final IntegerGenerator generator = new FixedIntegerSequenceGenerator();
      final int limit = 20;
      for (int i = 0; i < limit; i++) {
        generator.next();
      }
      try {
        generator.next();
        Assert.fail("An exception was expected.");
      } catch (UnsupportedOperationException exception) {
        // Good: an exception should have been thrown.
      }
    }

    @Test
    public void testMissingPrimesGenerator() {
      final Set<Integer> expected = new HashSet<>(Arrays.asList(
          1, 2, 4, 5, 8, 10, 11, 13, 16,
          17, 19, 20, 21, 22, 23, 25, 26, 29, 31,
          32, 34, 37, 38, 40, 41, 42, 43, 44, 46,
          47, 50, 52, 53, 55, 58, 59, 61, 62, 63,
          64, 65, 67, 68, 71, 73, 74, 76, 79, 80,
          82, 83, 84, 85, 86, 88, 89, 92, 94, 95,
          97, 100, 101, 103, 104, 105, 106, 107, 109, 110,
          113, 115, 116, 118, 121, 122, 124, 125, 126, 127,
          128, 130, 131, 134, 136, 137, 139, 142, 143, 145,
          146, 147, 148, 149, 151, 152, 155, 157, 158, 160,
          163
      ));

      final IntegerGenerator generator = new MissingPrimesGenerator();
      for (int i = 0; i < 100; i++) {
        Assert.assertTrue(generator.hasNext());
        int element = generator.next();
        Assert.assertTrue("Did not expect " + element, expected.contains(element));
        expected.remove(element);
      }
      Assert.assertTrue("Expected but did not see " + expected, expected.isEmpty());
    }

}

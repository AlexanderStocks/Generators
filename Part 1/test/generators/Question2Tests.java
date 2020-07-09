package generators;

import org.junit.Assert;
import org.junit.Test;

public class Question2Tests {

    @Test
    public void testDigitCombinationsGenerator1() {
      final StringGenerator generator = new DigitCombinationsGenerator();
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("1", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("2", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("3", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("4", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("11", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("12", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("13", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("14", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("21", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("22", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("23", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("24", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("31", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("32", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("33", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("34", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("41", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("42", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("43", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("44", generator.next());
      Assert.assertTrue(generator.hasNext());
      Assert.assertEquals("111", generator.next());
    }

    @Test
    public void testDigitCombinationsGenerator2() {
      final StringGenerator generator = new DigitCombinationsGenerator();
      for (int i = 0; i < 5000; i++) {
        Assert.assertTrue(generator.hasNext());
        final String value = generator.next();
        switch (i) {
          case 4:
            Assert.assertEquals("4", value);
            break;
          case 5:
            Assert.assertEquals("11", value);
            break;
          case 6:
            Assert.assertEquals("12", value);
            break;
          case 16:
            Assert.assertEquals("34", value);
            break;
          case 17:
            Assert.assertEquals("41", value);
            break;
          case 18:
            Assert.assertEquals("42", value);
            break;
          case 64:
            Assert.assertEquals("334", value);
            break;
          case 65:
            Assert.assertEquals("341", value);
            break;
          case 66:
            Assert.assertEquals("342", value);
            break;
          case 256:
            Assert.assertEquals("3334", value);
            break;
          case 257:
            Assert.assertEquals("3341", value);
            break;
          case 258:
            Assert.assertEquals("3342", value);
            break;
          case 1024:
            Assert.assertEquals("33334", value);
            break;
          case 1025:
            Assert.assertEquals("33341", value);
            break;
          case 1026:
            Assert.assertEquals("33342", value);
            break;
          case 4096:
            Assert.assertEquals("333334", value);
            break;
          case 4097:
            Assert.assertEquals("333341", value);
            break;
          case 4098:
            Assert.assertEquals("333342", value);
            break;
          default:
            break;
        }

      }

    }

}

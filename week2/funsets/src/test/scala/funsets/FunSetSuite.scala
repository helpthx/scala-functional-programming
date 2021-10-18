package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one".ignore) {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect contains all elements of each set") {
    new TestSets:
      val s_false = intersect(s1, s2)
      val s_true = intersect(s1, s1)
      assert(!s_false(1), "Intersect 1")
      assert(!s_false(2), "Intersect 2")
      assert(!s_false(3), "Intersect 3")

      assert(s_true(1), "Intersect 4")
      assert(!s_true(2), "Intersect 5")
      assert(!s_true(3), "Intersect 6")
  }

  test("diff contains all elements of each set") {
    new TestSets:
      val s = diff(s1, s2)
      assert(s(1), "Diff 1")
      assert(!s(2), "Diff 2")
      assert(!s(3), "Diff 3")
  }

  test("filter contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      val n = filter(s, s1)
      assert(n(1), "Filter 1")
      assert(!n(2), "Filter 2")
      assert(!n(3), "Filter 3")
  }



  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds

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
   * val s1 = singletonSet(1)
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
     * This test is currently disabled (by using .ignore) because the method
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

  test("intersect contains only common elements") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    val s = intersect(s3, s2)
    assert(!contains(s, 1), "Intersect set should not contain non-intersect elements")
    assert(contains(s, 2), "Intersect set should contain common elements")
  }

  test("diff contains only elements in first set not in second set") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    val s = diff(s3, s2)
    assert(contains(s, 1), "Diff set should contain elements only in the first set")
    assert(!contains(s, 2), "Diff set should not contain elements in both sets")
  }

  test("filter contains only elements that satisfy the predicate") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    val s = filter(s3, x => x % 2 == 0)
    assert(!contains(s, 1), "Filter set should not contain elements that do not satisfy the predicate")
    assert(contains(s, 2), "Filter set should contain elements that satisfy the predicate")
  }

  test("forall checks if all elements satisfy the predicate") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    assert(forall(s3, x => x < 3), "forall should return true if all elements satisfy the predicate")
    assert(!forall(s3, x => x % 2 == 0), "forall should return false if not all elements satisfy the predicate")
  }

  test("exists checks if any element satisfies the predicate") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    assert(exists(s3, x => x == 2), "exists should return true if any element satisfies the predicate")
    assert(!exists(s3, x => x == 3), "exists should return false if no element satisfies the predicate")
  }

  test("map transforms the set according to the function") {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = union(s1, s2)
    val s = map(s3, x => x * x)
    assert(contains(s, 1), "map set should contain transformed elements")
    assert(contains(s, 4), "map set should contain transformed elements")
    assert(!contains(s, 2), "map set should not contain original elements if not part of transformation")
  }

  import scala.concurrent.duration.*

  override val munitTimeout = 10.seconds

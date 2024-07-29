package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @annotation.tailrec
    def loop(chars: List[Char], openCount: Int): Boolean = {
      if (chars.isEmpty) openCount == 0
      else if (openCount < 0) false
      else {
        val head = chars.head
        val newOpenCount = head match {
          case '(' => openCount + 1
          case ')' => openCount - 1
          case _ => openCount
        }
        loop(chars.tail, newOpenCount)
      }
    }

    loop(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1 // There is one way to make change for 0: use no coins.
    else if (money < 0 || coins.isEmpty) 0 // No way to make change if money is negative or no coins left.
    else {
      countChange(money - coins.head, coins) + // Use the first coin.
        countChange(money, coins.tail) // Do not use the first coin.
    }
  }


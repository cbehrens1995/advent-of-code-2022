# advent-of-code-2022 - 11.12.2022

https://adventofcode.com/2022/day/11

Part One

Solution approach:

* load and parse data
* declare monkeys with items, worry function, test function and monkey outcomes
* process rounds of monkey business
*
    * determine worry level using worry function and divide by 3
*
    * determine new monkey using test function

Part Two:

Solution approach:

* load and parse data like in part one but add divisor to each monkey
* determine common divisor by multiplying all divisor
* determine new worry level first but then determine remainder for common divisor
* set new worry level to common divisor plus remainder
* process like before

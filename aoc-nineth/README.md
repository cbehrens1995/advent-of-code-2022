# advent-of-code-2022 - 03.12.2022

https://adventofcode.com/2022/day/3

Part One

Solution approach:

* load and parse data
* split each line in a direction and a move count
* assign head and tail position to x-y coordinates starting in (0,0)
* determine new head position by adjusting x or y coordinate one at a time
* determine tail position based on head
*
    * distinguish between diagonal movement based on quadrant position in coordinate system and non diagonal movement

Part Two:

Solution approach:

* use solution from part 1 but with more tails where the current tail position is determined by the one before
# advent-of-code-2022 - 08.12.2022

https://adventofcode.com/2022/day/8

Part One

Solution approach:

* load and parse data
* extract sublist of heights left, right, above and under each tree
* check if for one side all trees are smaller

Part Two:

Solution approach:

* load and parse data
* filter all non visible trees
* extract sublist of heights left, right, above and under each tree
* compare elementwise the heights and count until a tree is bigger than the given height
* multiply all counters of each direction
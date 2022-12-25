# advent-of-code-2022 - 10.12.2022

https://adventofcode.com/2022/day/10

Part One

Solution approach:

* load and parse data
* split each line in a command and a value
* loop over the command lines with a separate counter
* break loop if the counter matches the signal postions

Part Two:

Solution approach:

* load and parse data like in part one
* loop over the commands with a separate counter
* write a pixel for each command based on the counter and the current register
* reset counter if it breaches 39
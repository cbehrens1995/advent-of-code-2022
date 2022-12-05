package solution;

import utils.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        long fullyContainedAssignments = data.lines()
                .filter(Solution::fullyContainsOne)
                .count();

        System.out.printf("There are %s fully contained assignments%n", fullyContainedAssignments);

        long overlappingAssignments = data.lines()
                .filter(Solution::isOverlapping)
                .count();

        System.out.printf("There are %s assignments which are overlapping %n", overlappingAssignments);
    }

    private static boolean fullyContainsOne(String row) {
        List<String> assignments = Arrays.stream(row.split(",")).toList();
        String[] firstAssignment = assignments.get(0).split("-");
        String[] secondAssignment = assignments.get(1).split("-");

        int startingPointFirstInterval = Integer.parseInt(firstAssignment[0]);
        int endPointFirstInterval = Integer.parseInt(firstAssignment[1]);

        int startingPointSecondInterval = Integer.parseInt(secondAssignment[0]);
        int endPointSecondInterval = Integer.parseInt(secondAssignment[1]);

        boolean hasOverlappingIntervals = startingPointFirstInterval <= endPointSecondInterval && endPointFirstInterval >= startingPointSecondInterval;

        if (hasOverlappingIntervals) {

            if (startingPointFirstInterval <= startingPointSecondInterval && endPointFirstInterval >= endPointSecondInterval) {
                return true;
            }

            return startingPointSecondInterval <= startingPointFirstInterval && endPointSecondInterval >= endPointFirstInterval;

        }

        return false;
    }

    private static boolean isOverlapping(String row) {
        List<String> assignments = Arrays.stream(row.split(",")).toList();
        String[] firstAssignment = assignments.get(0).split("-");
        String[] secondAssignment = assignments.get(1).split("-");

        int startingPointFirstInterval = Integer.parseInt(firstAssignment[0]);
        int endPointFirstInterval = Integer.parseInt(firstAssignment[1]);

        int startingPointSecondInterval = Integer.parseInt(secondAssignment[0]);
        int endPointSecondInterval = Integer.parseInt(secondAssignment[1]);

        return startingPointFirstInterval <= endPointSecondInterval && endPointFirstInterval >= startingPointSecondInterval;
    }
}

package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        RopePositionService ropePositionService = new RopePositionService();

        BigInteger distinctPositionsForOneKnot = ropePositionService.countDistinctPositionsForOneKnot(data);
        System.out.printf("There are %s distinct positions for one knot! %n", distinctPositionsForOneKnot);

        BigInteger distinctPositionsForNineKnots = ropePositionService.countDistinctPositionsForNineKnots(data);
        System.out.printf("There are %s distinct positions for nine knot! %n", distinctPositionsForNineKnots);
    }
}
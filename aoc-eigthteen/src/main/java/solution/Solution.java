package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;

public class Solution {

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData("input.txt");

        SideDeterminer sideDeterminer = new SideDeterminer();

        //Part ONE
        BigInteger sideCount = sideDeterminer.countNonConnectedSides(data);

        System.out.printf("There are %s sides%n", sideCount);

        //Part TWO
        BigInteger exteriorSideCount = sideDeterminer.countExteriorSides(data);

        System.out.printf("The exterior side count is %s sides%n", exteriorSideCount);
    }
}

package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        TreeVisibilityService treeVisibilityService = new TreeVisibilityService();

        BigInteger visibleTrees = treeVisibilityService.countVisibleTrees(data);

        System.out.printf("There are %s trees visible! %n", visibleTrees);

        BigInteger highScore = treeVisibilityService.determineHighestScenicScore(data);
        System.out.printf("The highest score is  %s !", highScore);
    }
}
package solution;

import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData("input.txt");
        List<BigInteger> coordinates = data.lines()
                .map(BigInteger::new)
                .toList();

        DecryptionService decryptionService = new DecryptionService();
        List<BigInteger> decryptCoordinates = decryptionService.decrypt(coordinates);

        BigInteger groveCoordinate1 = decryptionService.findGroveCoordinate(decryptCoordinates, 1000);
        BigInteger groveCoordinate2 = decryptionService.findGroveCoordinate(decryptCoordinates, 2000);
        BigInteger groveCoordinate3 = decryptionService.findGroveCoordinate(decryptCoordinates, 3000);

        BigInteger sumGroveCoordinates = groveCoordinate1.add(groveCoordinate2).add(groveCoordinate3);

        System.out.printf("The sum of the grove coordiante is %w%n", sumGroveCoordinates);
    }
}

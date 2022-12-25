package solution;

import org.apache.commons.collections4.ListUtils;
import utils.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {
        String data = FileUtil.loadData(INPUT);

        CircleService circleService = new CircleService();

        BigInteger signalStrength1 = circleService.getSignalStrength(data, BigInteger.valueOf(20));
        BigInteger signalStrength2 = circleService.getSignalStrength(data, BigInteger.valueOf(60));
        BigInteger signalStrength3 = circleService.getSignalStrength(data, BigInteger.valueOf(100));
        BigInteger signalStrength4 = circleService.getSignalStrength(data, BigInteger.valueOf(140));
        BigInteger signalStrength5 = circleService.getSignalStrength(data, BigInteger.valueOf(180));
        BigInteger signalStrength6 = circleService.getSignalStrength(data, BigInteger.valueOf(220));

        BigInteger sum = signalStrength1.add(signalStrength2)
                .add(signalStrength3)
                .add(signalStrength4)
                .add(signalStrength5)
                .add(signalStrength6);

        System.out.printf("The sum of the six signal strengths is %s ! %n", sum);

        List<String> circleServiceImage = circleService.getImage(data);
        String formattedImage = ListUtils.partition(circleServiceImage, 40).stream()
                .map(strings -> String.join("", strings))
                .collect(Collectors.joining(System.getProperty("line.separator")));

        System.out.println(formattedImage);
    }
}
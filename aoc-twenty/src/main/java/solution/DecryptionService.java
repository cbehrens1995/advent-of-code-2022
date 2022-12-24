package solution;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DecryptionService {

    public List<BigInteger> decrypt(List<BigInteger> coordinates) {
        int sizeCoordinate = coordinates.size();
        BigInteger countCoordinates = BigInteger.valueOf(sizeCoordinate);

        List<Pair<Integer, BigInteger>> indexCoordinatePairs = new ArrayList<>();
        for (int i = 0; i < sizeCoordinate; i++) {
            Pair<Integer, BigInteger> indexCoordinatePair = Pair.of(i, coordinates.get(i));
            indexCoordinatePairs.add(indexCoordinatePair);
        }

        List<Pair<Integer, BigInteger>> mixedIndexCoordinatesPairs = new ArrayList<>(indexCoordinatePairs);
        for (Pair<Integer, BigInteger> indexCoordinatePair : indexCoordinatePairs) {
            BigInteger coordinate = indexCoordinatePair.getRight();
            int currentIndex = mixedIndexCoordinatesPairs.indexOf(indexCoordinatePair);
            BigInteger currentIndex2 = BigInteger.valueOf(currentIndex);

            BigInteger newIndex = currentIndex2.add(coordinate);

            while (newIndex.signum() < 0) {
                newIndex = newIndex.add(countCoordinates).subtract(BigInteger.ONE);
            }

            while (newIndex.compareTo(countCoordinates) > 0) {
                newIndex = newIndex.subtract(countCoordinates).add(BigInteger.ONE);
            }

            if (newIndex.equals(currentIndex2)) {
                continue;
            }

            if (newIndex.equals(BigInteger.ZERO)) {
                newIndex = countCoordinates.subtract(BigInteger.ONE);
            }

            List<Pair<Integer, BigInteger>> firstHalf;
            List<Pair<Integer, BigInteger>> secondHalf;
            if (newIndex.compareTo(currentIndex2) < 0) {
                firstHalf = new ArrayList<>(mixedIndexCoordinatesPairs.subList(0, newIndex.intValue()));
                secondHalf = mixedIndexCoordinatesPairs.subList(newIndex.intValue(), sizeCoordinate);
                secondHalf.remove(indexCoordinatePair);
            } else {
                firstHalf = mixedIndexCoordinatesPairs.subList(0, newIndex.intValue() + 1);
                secondHalf = new ArrayList<>(mixedIndexCoordinatesPairs.subList(newIndex.intValue() + 1, sizeCoordinate));
                firstHalf.remove(indexCoordinatePair);
            }

            mixedIndexCoordinatesPairs = Stream.of(
                            firstHalf,
                            List.of(indexCoordinatePair),
                            secondHalf)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            if (!mixedIndexCoordinatesPairs.get(newIndex.intValue()).equals(indexCoordinatePair)) {
                throw new IllegalStateException();
            }
        }

        return mixedIndexCoordinatesPairs.stream()
                .map(Pair::getRight)
                .toList();
    }

    public BigInteger findGroveCoordinate(List<BigInteger> decryptedCoordinates, int position) {
        int countCoordinates = decryptedCoordinates.size();

        int zeroCoordinateIndex = decryptedCoordinates.indexOf(BigInteger.ZERO);

        int totalIndex = zeroCoordinateIndex + position;
        while (totalIndex > countCoordinates - 1) {
            totalIndex = totalIndex - countCoordinates;
        }
        return decryptedCoordinates.get(totalIndex);
    }
}

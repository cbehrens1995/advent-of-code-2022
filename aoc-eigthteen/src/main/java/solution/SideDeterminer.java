package solution;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SideDeterminer {

    public BigInteger countNonConnectedSides(String data) {
        List<Side> nonConnectedSides = getNonConnectedSides(data);

        return BigInteger.valueOf(nonConnectedSides.size());
    }

    private List<Side> getNonConnectedSides(String data) {
        List<Side> allSides = data.lines()
                .map(this::convertToCoordinate)
                .map(this::getSides)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Set<Side> duplicatedSide = allSides.stream()
                .filter(side -> Collections.frequency(allSides, side) > 1)
                .collect(Collectors.toSet());

        allSides.removeAll(duplicatedSide);
        return allSides;
    }


    public BigInteger countExteriorSides(String data) {
        List<Side> nonConnectedSides = getNonConnectedSides(data);

        List<List<BigInteger>> coordinates = data.lines()
                .map(this::convertToCoordinate)
                .toList();

        List<Side> exteriorSides = new ArrayList<>(nonConnectedSides);

        var xMinMaxPair = getMinMax(nonConnectedSides, Side::x1, Side::x2);
        var yMinMaxPair = getMinMax(nonConnectedSides, Side::y1, Side::y2);
        var zMinMaxPair = getMinMax(nonConnectedSides, Side::z1, Side::z2);

        for (BigInteger z = zMinMaxPair.getLeft(); z.compareTo(zMinMaxPair.getRight()) < 0; z = z.add(BigInteger.ONE)) {
            for (BigInteger y = yMinMaxPair.getLeft(); y.compareTo(yMinMaxPair.getRight()) < 0; y = y.add(BigInteger.ONE)) {
                for (BigInteger x = xMinMaxPair.getLeft(); x.compareTo(xMinMaxPair.getRight()) < 0; x = x.add(BigInteger.ONE)) {
                    List<BigInteger> coordinate = List.of(x, y, z);
                    if (new HashSet<>(coordinates).contains(coordinate)) {
                        continue;
                    }
                    List<Side> cubeSides = getSides(coordinate);
                    if (nonConnectedSides.containsAll(cubeSides)) {
                        exteriorSides.removeAll(cubeSides);
                    }
                }
            }
        }

        return BigInteger.valueOf(exteriorSides.size());
    }

    private Pair<BigInteger, BigInteger> getMinMax(List<Side> nonConnectedSides,
                                                   Function<Side, BigInteger> getterCoordinate1,
                                                   Function<Side, BigInteger> getterCoordinate2) {
        BigInteger minValue = minValue(nonConnectedSides, getterCoordinate1, getterCoordinate2);
        BigInteger maxValue = maxValue(nonConnectedSides, getterCoordinate1, getterCoordinate2);
        return Pair.of(minValue, maxValue);
    }

    private BigInteger maxValue(List<Side> nonConnectedSides,
                                Function<Side, BigInteger> getterCoordinate1,
                                Function<Side, BigInteger> getterCoordinate2) {
        Stream<BigInteger> maxCoordinateStream1 = nonConnectedSides.stream()
                .map(getterCoordinate1);
        Stream<BigInteger> maxCoordinateStream2 = nonConnectedSides.stream()
                .map(getterCoordinate2);
        return Stream.concat(maxCoordinateStream1, maxCoordinateStream2)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private BigInteger minValue(List<Side> nonConnectedSides,
                                Function<Side, BigInteger> getterCoordinate1,
                                Function<Side, BigInteger> getterCoordinate2) {
        Stream<BigInteger> minCoordinateStream1 = nonConnectedSides.stream()
                .map(getterCoordinate1);
        Stream<BigInteger> minCoordinateStream2 = nonConnectedSides.stream()
                .map(getterCoordinate2);
        return Stream.concat(minCoordinateStream1, minCoordinateStream2)
                .min(Comparator.naturalOrder())
                .orElseThrow();
    }

    private List<BigInteger> convertToCoordinate(String lineInput) {
        String[] coordinates = StringUtils.split(lineInput, ",");
        return Arrays.stream(coordinates)
                .map(BigInteger::new)
                .toList();
    }

    private List<Side> getSides(List<BigInteger> coordinates) {
        BigInteger xCoordinate = coordinates.get(0);
        BigInteger yCoordinate = coordinates.get(1);
        BigInteger zCoordinate = coordinates.get(2);

        BigInteger length = BigInteger.ONE;
        Side xySide1 = new Side(xCoordinate, xCoordinate.add(length), yCoordinate, yCoordinate.add(length), zCoordinate, zCoordinate);
        Side xySide2 = new Side(xCoordinate, xCoordinate.add(length), yCoordinate, yCoordinate.add(length), zCoordinate.add(length), zCoordinate.add(length));

        Side xzSide1 = new Side(xCoordinate, xCoordinate.add(length), yCoordinate, yCoordinate, zCoordinate, zCoordinate.add(length));
        Side xzSide2 = new Side(xCoordinate, xCoordinate.add(length), yCoordinate.add(length), yCoordinate.add(length), zCoordinate, zCoordinate.add(length));

        Side yzSide1 = new Side(xCoordinate, xCoordinate, yCoordinate, yCoordinate.add(length), zCoordinate, zCoordinate.add(length));
        Side yzSide2 = new Side(xCoordinate.add(length), xCoordinate.add(length), yCoordinate, yCoordinate.add(length), zCoordinate, zCoordinate.add(length));

        return List.of(xySide1, xySide2, xzSide1, xzSide2, yzSide1, yzSide2);
    }

    private record Side(BigInteger x1,
                        BigInteger x2,
                        BigInteger y1,
                        BigInteger y2,
                        BigInteger z1,
                        BigInteger z2) {
    }
}

package solution;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RopePositionService {

    public BigInteger countDistinctPositionsForOneKnot(String data) {
        List<Move> moves = data.lines()
                .map(this::getMove)
                .toList();

        Pair<BigInteger, BigInteger> tailPosition = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> headPosition = Pair.of(BigInteger.ZERO, BigInteger.ZERO);

        Set<Pair<BigInteger, BigInteger>> positions = new HashSet<>();
        for (Move move : moves) {
            BigInteger moveCont = move.moveCont();
            Direction direction = move.direction();
            for (int i = 0; i < moveCont.intValue(); i++) {
                headPosition = determineNewHead(headPosition, direction);
                tailPosition = determineNewTail(tailPosition, headPosition);
                positions.add(tailPosition);
            }

        }
        return BigInteger.valueOf(positions.size());
    }

    public BigInteger countDistinctPositionsForNineKnots(String data) {
        List<Move> moves = data.lines()
                .map(this::getMove)
                .toList();

        Pair<BigInteger, BigInteger> tailPosition1 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition2 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition3 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition4 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition5 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition6 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition7 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition8 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> tailPosition9 = Pair.of(BigInteger.ZERO, BigInteger.ZERO);
        Pair<BigInteger, BigInteger> headPosition = Pair.of(BigInteger.ZERO, BigInteger.ZERO);

        Set<Pair<BigInteger, BigInteger>> positions = new HashSet<>();
        for (Move move : moves) {
            BigInteger moveCont = move.moveCont();
            Direction direction = move.direction();
            for (int i = 0; i < moveCont.intValue(); i++) {
                headPosition = determineNewHead(headPosition, direction);
                tailPosition1 = determineNewTail(tailPosition1, headPosition);
                tailPosition2 = determineNewTail(tailPosition2, tailPosition1);
                tailPosition3 = determineNewTail(tailPosition3, tailPosition2);
                tailPosition4 = determineNewTail(tailPosition4, tailPosition3);
                tailPosition5 = determineNewTail(tailPosition5, tailPosition4);
                tailPosition6 = determineNewTail(tailPosition6, tailPosition5);
                tailPosition7 = determineNewTail(tailPosition7, tailPosition6);
                tailPosition8 = determineNewTail(tailPosition8, tailPosition7);
                tailPosition9 = determineNewTail(tailPosition9, tailPosition8);
                positions.add(tailPosition9);
            }

        }
        return BigInteger.valueOf(positions.size());
    }

    private Pair<BigInteger, BigInteger> determineNewHead(Pair<BigInteger, BigInteger> headPosition, Direction direction) {
        BigInteger xPosition = headPosition.getLeft();
        BigInteger yPosition = headPosition.getRight();

        BigInteger length = BigInteger.ONE;
        return switch (direction) {
            case L -> Pair.of(xPosition.subtract(length), yPosition);
            case R -> Pair.of(xPosition.add(length), yPosition);
            case U -> Pair.of(xPosition, yPosition.add(length));
            case D -> Pair.of(xPosition, yPosition.subtract(length));
        };
    }

    private Pair<BigInteger, BigInteger> determineNewTail(Pair<BigInteger, BigInteger> tailPosition, Pair<BigInteger, BigInteger> headPosition) {
        BigInteger xPosition = tailPosition.getLeft();
        BigInteger yPosition = tailPosition.getRight();

        BigInteger x1Right = xPosition.add(BigInteger.ONE);
        BigInteger x2Right = xPosition.add(BigInteger.TWO);

        BigInteger x1Left = xPosition.subtract(BigInteger.ONE);
        BigInteger x2Left = xPosition.subtract(BigInteger.TWO);

        BigInteger y1Up = yPosition.add(BigInteger.ONE);
        BigInteger y2Up = yPosition.add(BigInteger.TWO);

        BigInteger y2Down = yPosition.subtract(BigInteger.TWO);
        BigInteger y1Down = yPosition.subtract(BigInteger.ONE);

        //Handling non diagonal
        if (headPosition.equals(Pair.of(x2Right, yPosition))) {
            return Pair.of(x1Right, yPosition);
        } else if (headPosition.equals(Pair.of(x2Left, yPosition))) {
            return Pair.of(x1Left, yPosition);
        } else if (headPosition.equals(Pair.of(xPosition, y2Up))) {
            return Pair.of(xPosition, y1Up);
        } else if (headPosition.equals(Pair.of(xPosition, y2Down))) {
            return Pair.of(xPosition, y1Down);
        }

        //Handling diagonal
        Pair<BigInteger, BigInteger> positionFirstQuadrant1 = Pair.of(x1Right, y2Up);
        Pair<BigInteger, BigInteger> positionFirstQuadrant2 = Pair.of(x2Right, y1Up);
        Pair<BigInteger, BigInteger> positionFirstQuadrant3 = Pair.of(x2Right, y2Up);
        List<Pair<BigInteger, BigInteger>> positionsFirstQuadrant = List.of(positionFirstQuadrant1, positionFirstQuadrant2, positionFirstQuadrant3);
        if (positionsFirstQuadrant.contains(headPosition)) {
            return Pair.of(x1Right, y1Up);
        }

        Pair<BigInteger, BigInteger> positionSecondQuadrant1 = Pair.of(x1Left, y2Up);
        Pair<BigInteger, BigInteger> positionSecondQuadrant2 = Pair.of(x2Left, y1Up);
        Pair<BigInteger, BigInteger> positionSecondQuadrant3 = Pair.of(x2Left, y2Up);
        List<Pair<BigInteger, BigInteger>> positionsSecondQuadrant = List.of(positionSecondQuadrant1, positionSecondQuadrant2, positionSecondQuadrant3);
        if (positionsSecondQuadrant.contains(headPosition)) {
            return Pair.of(x1Left, y1Up);
        }

        Pair<BigInteger, BigInteger> positionThirdQuadrant1 = Pair.of(x1Left, y2Down);
        Pair<BigInteger, BigInteger> positionThirdQuadrant2 = Pair.of(x2Left, y1Down);
        Pair<BigInteger, BigInteger> positionThirdQuadrant3 = Pair.of(x2Left, y2Down);
        List<Pair<BigInteger, BigInteger>> positionsThirdQuadrant = List.of(positionThirdQuadrant1, positionThirdQuadrant2, positionThirdQuadrant3);
        if (positionsThirdQuadrant.contains(headPosition)) {
            return Pair.of(x1Left, y1Down);
        }

        Pair<BigInteger, BigInteger> positionForthQuadrant1 = Pair.of(x1Right, y2Down);
        Pair<BigInteger, BigInteger> positionForthQuadrant2 = Pair.of(x2Right, y1Down);
        Pair<BigInteger, BigInteger> positionForthQuadrant3 = Pair.of(x2Right, y2Down);
        List<Pair<BigInteger, BigInteger>> positionsForthQuadrant = List.of(positionForthQuadrant1, positionForthQuadrant2, positionForthQuadrant3);
        if (positionsForthQuadrant.contains(headPosition)) {
            return Pair.of(x1Right, y1Down);
        }

        return tailPosition;
    }

    private Move getMove(String column) {
        String[] columnParts = StringUtils.split(column);
        return new Move(Direction.valueOf(columnParts[0]), new BigInteger(columnParts[1]));
    }

    enum Direction {
        L, R, U, D
    }

    record Move(Direction direction,
                BigInteger moveCont) {
    }
}

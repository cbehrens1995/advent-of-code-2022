package solution;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CircleService {

    public BigInteger getSignalStrength(String data, BigInteger position) {
        List<CommandLine> commandLines = extractCommandLines(data);

        AtomicInteger circleCount = new AtomicInteger();

        BigInteger register = BigInteger.ONE;
        for (CommandLine commandLine : commandLines) {
            if (circleCount.get() == position.intValue()) {
                break;
            }

            Command command = commandLine.command();
            if (command == Command.NOOP) {
                circleCount.incrementAndGet();
            }

            if (command == Command.ADDX) {
                if (circleCount.incrementAndGet() == position.intValue()) {
                    break;
                }

                if (circleCount.incrementAndGet() == position.intValue()) {
                    break;
                }

                register = register.add(commandLine.value());
            }
        }

        return register.multiply(position);
    }

    public List<String> getImage(String data) {
        List<String> crtImagePixels = new ArrayList<>();
        List<CommandLine> commandLines = extractCommandLines(data);


        BigInteger register = BigInteger.ONE;
        AtomicInteger circleCount = new AtomicInteger();

        List<BigInteger> spritePositions = List.of(BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO);

        for (CommandLine commandLine : commandLines) {
            Command command = commandLine.command();
            if (command == Command.NOOP) {
                writeCRTPixel(crtImagePixels, circleCount, spritePositions);
            }

            if (command == Command.ADDX) {
                writeCRTPixel(crtImagePixels, circleCount, spritePositions);
                writeCRTPixel(crtImagePixels, circleCount, spritePositions);

                register = register.add(commandLine.value());
            }

            spritePositions = List.of(register, register.subtract(BigInteger.ONE), register.add(BigInteger.ONE));
        }

        return crtImagePixels;
    }

    private void writeCRTPixel(List<String> crtImagePixels, AtomicInteger circleCount, List<BigInteger> spritePositions) {
        BigInteger position = BigInteger.valueOf(circleCount.getAndIncrement());
        if (spritePositions.contains(position)) {
            crtImagePixels.add("#");
        } else {
            crtImagePixels.add(".");
        }

        if (circleCount.get() > 39) {
            circleCount.set(0);
        }
    }

    private List<CommandLine> extractCommandLines(String data) {
        return data.lines()
                .map(StringUtils::split)
                .map(this::extractCommandLine)
                .toList();
    }

    private CommandLine extractCommandLine(String[] commands) {
        Command command = Command.valueOf(commands[0].toUpperCase());
        return switch (command) {
            case NOOP -> new CommandLine(Command.NOOP, BigInteger.ZERO);
            case ADDX -> new CommandLine(Command.ADDX, new BigInteger(commands[1]));
        };
    }

    enum Command {
        NOOP, ADDX
    }

    record CommandLine(
            Command command,
            BigInteger value) {
    }
}

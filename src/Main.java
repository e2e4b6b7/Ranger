import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(System.in);
        int[] input = reader.parseInput();
        LinkedList<Range> ranges = Solver.groupInRangesSorted(input);
        System.out.println(ranges);
    }
}

class InputReader {
    private final BufferedReader reader;

    InputReader(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }

    public int[] parseInput() throws IOException {
        String line = reader.readLine().trim();
        String[] numbersString = line.substring(1, line.length() - 1).split(", ");
        System.out.println(Arrays.toString(numbersString));
        int[] numbers = new int[numbersString.length];
        for (int i = 0; i < numbersString.length; i++) {
            numbers[i] = Integer.parseInt(numbersString[i]);
        }
        return numbers;
    }
}

class Range {
    private int left, right;

    Range(int left) {
        this.left = left;
        this.right = left;
    }

    public boolean canJoin(int candidate) {
        return left - 1 <= candidate && candidate <= right + 1;
    }

    public void join(int candidate) {
        if (candidate == right + 1) {
            right++;
        } else if (candidate == left - 1) {
            left--;
        } else if (!canJoin(candidate)) {
            throw new IllegalArgumentException("Can't join");
        }
    }

    @Override
    public String toString() {
        if (left == right) {
            return "\"" + left + '"';
        } else {
            return "\"" + left + "->" + right + '"';
        }
    }
}

abstract class Solver {
    public static LinkedList<Range> groupInRangesSorted(int[] input) {
        LinkedList<Range> ranges = new LinkedList<>();
        for (int number : input) {
            if (ranges.isEmpty()) {
                ranges.addLast(new Range(number));
            } else {
                if (ranges.getLast().canJoin(number)) {
                    ranges.getLast().join(number);
                } else {
                    ranges.addLast(new Range(number));
                }
            }
        }
        return ranges;
    }
}


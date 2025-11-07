package MathResearch;

import FileManager.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MathResearch {
    private int number;
    private int limit;
    private static final int MAX_BAR_LENGTH = 50;
    private static final int ANIMATION_STEPS = 5;

    public MathResearch(int number, int limit) {
        if (number <= 0 || limit < number) {
            throw new IllegalArgumentException("Число должно быть > 0 и граница >= числа");
        }
        this.number = number;
        this.limit = limit;
    }

    public void run(boolean showProcess, FileManager fileManager) throws IOException {
        fileManager.writeHeader(number, limit);

        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();
        Map<Integer, Integer> barLengths = new HashMap<>();

        int linesInHistogram = 0;

        for (int current = number; current <= limit; current += number) {
            int sumOfDigits = sumDigits(current);

            counts.put(sumOfDigits, counts.getOrDefault(sumOfDigits, 0) + 1);
            lastOccurrence.put(sumOfDigits, current);

            fileManager.writeLine(current, sumOfDigits);

            if (showProcess) {
                int maxCount = counts.values().stream().max(Integer::compareTo).orElse(1);

                // Анимация только текущего столбца
                for (int step = 1; step <= ANIMATION_STEPS; step++) {
                    if (linesInHistogram != 0) {
                        System.out.print("\033[" + (linesInHistogram + 1) + "F");
                        System.out.print("\r");
                        System.out.println("Текущий прогресс: число " + current);
                    } else {
                        System.out.println("Текущий прогресс: число " + current);
                    }

                    linesInHistogram = printInteractiveHistogram(counts, lastOccurrence, barLengths, maxCount, sumOfDigits, step / (double) ANIMATION_STEPS);

                    try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
        }
    }

    private int sumDigits(int number) {
        number = Math.abs(number);
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    private int printInteractiveHistogram(Map<Integer, Integer> counts,
                                          Map<Integer, Integer> lastOccurrence,
                                          Map<Integer, Integer> barLengths,
                                          int maxCount,
                                          int currentSum,
                                          double ratioStep) {

        StringBuilder sb = new StringBuilder();

        counts.keySet().stream().sorted().forEach(sum -> {
            int count = counts.get(sum);
            int last = lastOccurrence.get(sum);
            int targetLength = (int)((double)count / maxCount * MAX_BAR_LENGTH);
            int currentLength = barLengths.getOrDefault(sum, 0);

            if (sum == currentSum) {
                currentLength += (int)((targetLength - currentLength) * ratioStep);
                barLengths.put(sum, currentLength);
            }

            String color = getGradientColor(count, maxCount);
            String bar = color + "#".repeat(Math.max(currentLength, 0)) + "\033[0m";

            sb.append(String.format("%5d | %-6d | %-15d | %s%n", sum, count, last, bar));
        });

        System.out.print(sb.toString());
        return counts.size();
    }

    private String getGradientColor(int count, int maxCount) {
        double ratio = (double) count / maxCount;
        int r, g, b = 0;

        if (ratio < 0.5) {
            r = (int)(ratio * 2 * 255);
            g = 255;
        } else {
            r = 255;
            g = (int)((1 - (ratio - 0.5) * 2) * 255);
        }

        return String.format("\033[38;2;%d;%d;%dm", r, g, b);
    }
}
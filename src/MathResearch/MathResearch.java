package MathResearch;

import FileManager.FileManager;
import java.io.IOException;

public class MathResearch {
    private int number;
    private int limit;

    public MathResearch(int number, int limit) {
        if (number <= 0 || limit < number) {
            throw new IllegalArgumentException("Число должно быть > 0 и граница >= числа");
        }
        this.number = number;
        this.limit = limit;
    }

    public void run(boolean showProcess, FileManager fileManager) throws IOException {
        fileManager.writeHeader(number, limit);

        if (showProcess) {
            System.out.println("\nИсследование числа " + number + " до границы " + limit);
            System.out.printf("%-10s %-10s%n", "Число", "Сумма цифр");
            System.out.println("----------------------");
        }

        for (int current = number; current <= limit; current += number) {
            int sumOfDigits = sumDigits(current);

            if (showProcess) {
                System.out.printf("%-10d %-10d%n", current, sumOfDigits);
                try { Thread.sleep(0); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }

            fileManager.writeLine(current, sumOfDigits);
        }
    }

    private int sumDigits(int number) {
        number = Math.abs(number); // на всякий случай
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
package Main;

import FileManager.FileManager;
import MathResearch.MathResearch;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите число для исследования: ");
            int number = scanner.nextInt();

            System.out.print("Введите границу: ");
            int limit = scanner.nextInt();
            scanner.nextLine(); // очищаем буфер

            System.out.print("Хотите наблюдать за процессом вычислений? (да/нет): ");
            String showProcess = scanner.nextLine().trim().toLowerCase();

            try (FileManager fileManager = new FileManager("results.txt")) {
                MathResearch research = new MathResearch(number, limit);
                research.run(showProcess.equals("да"), fileManager);
                System.out.println("\nРезультаты записаны в файл: " + fileManager.getFileName());
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }
}
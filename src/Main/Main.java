package Main;

import FileManager.FileManager;
import MathResearch.MathResearch;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите число для исследования: ");
        int number = scanner.nextInt();

        System.out.print("Введите границу: ");
        int limit = scanner.nextInt();
        scanner.nextLine(); // очищаем буфер

        System.out.print("Хотите наблюдать за процессом вычислений? (да/нет): ");
        String showProcess = scanner.nextLine().trim().toLowerCase();

        MathResearch research = new MathResearch(number, limit);
        FileManager fileManager = new FileManager("results.txt");

        try {
            research.run(showProcess.equals("да"), fileManager);
        } catch (IOException e) {
            System.out.println("Ошибка при проведении исследования: " + e.getMessage());
        }

        System.out.println("\nРезультаты записаны в файл: " + fileManager.getFileName());
        scanner.close();
    }
}
package kz.library;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== БИБЛИОТЕКА ===");
            System.out.println("1. Показать книги");
            System.out.println("2. Добавить книгу");
            System.out.println("3. Изменить книгу");
            System.out.println("4. Удалить книгу");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> showBooks();
                    case "2" -> addBook();
                    case "3" -> updateBook();
                    case "4" -> deleteBook();
                    case "0" -> {
                        System.out.println("Программа завершена.");
                        return;
                    }
                    default -> System.out.println("Неверный пункт меню.");
                }
            } catch (SQLException e) {
                System.out.println("Ошибка базы данных: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }

    // READ: просмотр всех книг вместе с авторами
    private static void showBooks() throws SQLException {
        String sql = "SELECT books.id, books.title, books.year_published, authors.full_name " +
                     "FROM books JOIN authors ON books.author_id = authors.id ORDER BY books.id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            System.out.println("\nID | Название | Год | Автор");
            while (result.next()) {
                System.out.println(result.getInt("id") + " | " +
                        result.getString("title") + " | " +
                        result.getInt("year_published") + " | " +
                        result.getString("full_name"));
            }
        }
    }

    // CREATE: добавление книги
    private static void addBook() throws SQLException {
        System.out.print("Название книги: ");
        String title = scanner.nextLine();
        System.out.print("Год издания: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("ID автора (1 или 2 для начальных данных): ");
        int authorId = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO books (title, year_published, author_id) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setInt(2, year);
            statement.setInt(3, authorId);
            int rows = statement.executeUpdate();
            System.out.println("Добавлено записей: " + rows);
        }
    }

    // UPDATE: изменение названия и года книги
    private static void updateBook() throws SQLException {
        System.out.print("ID книги: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Новое название: ");
        String title = scanner.nextLine();
        System.out.print("Новый год издания: ");
        int year = Integer.parseInt(scanner.nextLine());

        String sql = "UPDATE books SET title = ?, year_published = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setInt(2, year);
            statement.setInt(3, id);
            int rows = statement.executeUpdate();
            System.out.println("Изменено записей: " + rows);
        }
    }

    // DELETE: удаление книги
    private static void deleteBook() throws SQLException {
        System.out.print("ID книги: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            System.out.println("Удалено записей: " + rows);
        }
    }
}

package org.example;

import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BookDAO bookDAO = new BookDAO();
        LibraryDAO libraryDAO = new LibraryDAO();

        while (true) {

            System.out.println("\nБИБЛИОТЕКА");
            System.out.println("1. Показать все книги");
            System.out.println("2. Добавить книгу");
            System.out.println("3. Найти книгу");
            System.out.println("4. Изменить книгу");
            System.out.println("5. Удалить книгу");
            System.out.println("6. Добавить автора");
            System.out.println("7. Добавить читателя");
            System.out.println("8. Выдать книгу");
            System.out.println("9. Показать выдачи");
            System.out.println("10. Вернуть книгу");
            System.out.println("0. Выход");

            System.out.print("Выберите действие: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
                continue;
            }

            switch (choice) {

                case 1:
                    for (String book : bookDAO.getAllBooks()) {
                        System.out.println(book);
                    }
                    break;

                case 2:
                    System.out.print("Название: ");
                    String title = scanner.nextLine();

                    System.out.print("ID автора: ");
                    int authorId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Год: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    System.out.print("Количество: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    bookDAO.addBook(title, authorId, year, quantity);
                    break;

                case 3:
                    System.out.print("ID книги: ");
                    int searchId = Integer.parseInt(scanner.nextLine());
                    bookDAO.findBook(searchId);
                    break;

                case 4:
                    System.out.print("ID книги: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.print("Новое название: ");
                    String newTitle = scanner.nextLine();

                    System.out.print("Новый год: ");
                    int newYear = Integer.parseInt(scanner.nextLine());

                    System.out.print("Количество: ");
                    int newQuantity = Integer.parseInt(scanner.nextLine());

                    bookDAO.updateBook(id, newTitle, newYear, newQuantity);
                    break;

                case 5:
                    System.out.print("ID книги: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    bookDAO.deleteBook(deleteId);
                    break;

                case 6:
                    System.out.print("Имя автора: ");
                    String author = scanner.nextLine();

                    System.out.print("Страна: ");
                    String country = scanner.nextLine();

                    libraryDAO.addAuthor(author, country);
                    break;

                case 7:
                    System.out.print("Имя читателя: ");
                    String reader = scanner.nextLine();

                    System.out.print("Телефон: ");
                    String phone = scanner.nextLine();

                    libraryDAO.addReader(reader, phone);
                    break;

                case 8:
                    System.out.print("ID книги: ");
                    int bookId = Integer.parseInt(scanner.nextLine());

                    System.out.print("ID читателя: ");
                    int readerId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Дата выдачи (ГГГГ-ММ-ДД): ");
                    Date date = Date.valueOf(scanner.nextLine());

                    libraryDAO.addLoan(bookId, readerId, date);
                    break;

                case 9:
                    for (String loan : libraryDAO.getAllLoans()) {
                        System.out.println(loan);
                    }
                    break;

                case 10:
                    System.out.print("ID выдачи: ");
                    int loanId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Дата возврата (ГГГГ-ММ-ДД): ");
                    Date returnDate = Date.valueOf(scanner.nextLine());

                    libraryDAO.returnBook(loanId, returnDate);
                    break;

                case 0:
                    System.out.println("Программа завершена!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}

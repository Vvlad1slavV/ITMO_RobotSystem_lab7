package com.itmo.r3135;

/**
 * Основной клас программы.
 */
public class Main {

    public static void main(String[] args) {
        Picture.gamepad();
        System.out.println("Держи немного примеров для коллекции в формате json :)");
        System.out.println("-----------");
        for (int i = 0; i < 10; i++) {
            System.out.println(Generator.nextGsonProduct());
        }
        System.out.println("-----------");
        try {
            if (args.length != 0) {
                Commander commander = new Commander(new Control(args[0]));
                commander.interactiveMod();
            } else
                System.out.println("Адрес файла не был обнаружен. Для Корректной работы программы при запуске в аргументах укажите путь к файлу JSON");
        } catch (java.lang.OutOfMemoryError e) {
            Picture.bruh();
        }
    }
}

//   Версия для ввода адреса файла из консоли.
//
//        Scanner input = new Scanner(System.in);
//        while (true) {
//            System.out.println("Для начала работы с коллекцией ведите путь до файла коллекции (в формате .json) или 'exit' для завершенеия программы.");
//            String inputString = input.nextLine();
//            if (inputString.equals("exit")) break;
//            else {
//                File jsonPath = new File(inputString);
//                if (!jsonPath.exists()) {
//                    System.out.println("Файл по указанному пути (" + jsonPath.toString() + ") не существует.");
//                } else {
//                    if (!jsonPath.isFile())
//                        System.out.println("Путь " + jsonPath.toString() + " не содержит имени файла");
//                    else {
//                        System.out.println("Файл " + jsonPath.toString() + " успешно обнаружен");
//                        Commander commander = new Commander(new Control(jsonPath.toString()));
//                        commander.interactiveMod();
//                        break;
//                    }
//                }
//            }
//        }



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

class Main {
    // ===== Константы =====
    static final int SEQUENCE_LENGTH = 1000;      // размер последовательности
    static final int MAX_ELEMENT_VALUE = 10000;   // максимальное значение элемента
    static final int REQUIRED_DIVISOR = 21;       // R должно быть кратно 21
    static final int DIVISOR_THREE = 3;           // 21 = 3 * 7
    static final int DIVISOR_SEVEN = 7;
    static final int NOT_FOUND_RESULT = -1;       // результат, если числа нет
    static final int NO_ELEMENT_YET = -1;         // признак "такого элемента ещё не встречали"
    
    static final Queue<String> inputTokens = new ArrayDeque<>();
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Считывает следующее число из ввода
    static int readNextNumber() throws IOException {
        while (true) {
            while (inputTokens.isEmpty()) {
                String line = reader.readLine();
                if (line == null) {
                    System.out.println("Ввод завершён раньше времени.");
                    System.exit(1);
                }
                for (String token : line.split("[ \\t,;]+")) {
                    if (!token.isEmpty()) {
                        inputTokens.add(token);
                    }
                }
            }

            String current = inputTokens.poll();
            try {
                int value = Integer.parseInt(current);
                if (value >= 0 && value <= MAX_ELEMENT_VALUE) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // некорректный ввод обрабатывается ниже
            }

            System.out.println("Ошибка: \"" + current + "\" не подходит. Нужно целое число от 0 до "
                    + MAX_ELEMENT_VALUE + ". Повторите ввод:");
        }
    }

    // Обновляет минимум: если элемент ещё не найден или новое значение меньше
    static int updateMin(int currentMin, int value) {
        return (currentMin == NO_ELEMENT_YET || value < currentMin) ? value : currentMin;
    }

    void main() throws IOException {
        System.out.println("Введите " + SEQUENCE_LENGTH + " неотрицательных целых чисел (каждое не больше "
                + MAX_ELEMENT_VALUE + ").");
        System.out.println("Числа можно вводить по одному в строке или через пробел.");

        // Минимальные значения среди ранее прочитанных элементов:
        int minAnyElement = NO_ELEMENT_YET;       // любой элемент
        int minDivisibleBy21 = NO_ELEMENT_YET;    // кратный 21
        int minDivisibleBy3 = NO_ELEMENT_YET;     // кратный 3
        int minDivisibleBy7 = NO_ELEMENT_YET;     // кратный 7

        long minProduct = Long.MAX_VALUE;         // лучший найденный R

        // ===== Единственный проход по последовательности =====
        for (int index = 0; index < SEQUENCE_LENGTH; index++) {
            int current = readNextNumber();

            // Произведение кратно 21 <=> оно кратно и 3, и 7. Это возможно в случаях:
            // 1) текущий кратен 21 (парный любой из предыдущих);
            // 2) один из предыдущих кратен 21 (парный любой текущий);
            // 3) текущий кратен 3, а предыдущий кратен 7;
            // 4) текущий кратен 7, а предыдущий кратен 3.
            // Берём пары только с ПРЕДЫДУЩИМИ элементами, поэтому элемент
            // не может быть умножен сам на себя.

            // Случай 1
            if (current % REQUIRED_DIVISOR == 0 && minAnyElement != NO_ELEMENT_YET) {
                minProduct = Math.min(minProduct, (long) current * minAnyElement);
            }

            // Случай 2
            if (minDivisibleBy21 != NO_ELEMENT_YET) {
                minProduct = Math.min(minProduct, (long) current * minDivisibleBy21);
            }

            // Случай 3
            if (current % DIVISOR_THREE == 0 && minDivisibleBy7 != NO_ELEMENT_YET) {
                minProduct = Math.min(minProduct, (long) current * minDivisibleBy7);
            }

            // Случай 4
            if (current % DIVISOR_SEVEN == 0 && minDivisibleBy3 != NO_ELEMENT_YET) {
                minProduct = Math.min(minProduct, (long) current * minDivisibleBy3);
            }

            // Обновляем минимумы с учётом текущего элемента
            minAnyElement = updateMin(minAnyElement, current);
            if (current % REQUIRED_DIVISOR == 0) minDivisibleBy21 = updateMin(minDivisibleBy21, current);
            if (current % DIVISOR_THREE == 0)    minDivisibleBy3 = updateMin(minDivisibleBy3, current);
            if (current % DIVISOR_SEVEN == 0)    minDivisibleBy7 = updateMin(minDivisibleBy7, current);
        }

        // Если minProduct изменился, значит подходящая пара была найдена
        boolean isFound = minProduct != Long.MAX_VALUE;

        // ===== Вывод результата =====
        if (isFound) {
            System.out.println("Минимальное число R, кратное " + REQUIRED_DIVISOR
                    + " и равное произведению двух элементов: " + minProduct);
        }
        else {
            System.out.println("Подходящего числа нет. Результат: " + NOT_FOUND_RESULT);
        }
    }
}
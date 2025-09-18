/**
 * Клас для виконання операцій з матрицями згідно з варіантом завдання
 * Номер в списпку групи: 2
 * C5 = 2 (додавання матриць)
 * C7 = 2 (тип short)
 * C11 = 2 (сума найбільших елементів кожного стовпця)
 *

 * @version 1.0
 */
public class Lab1 {

    /**
     * Головний виконавчий метод програми
     * Виконує додавання матриць та обчислення суми найбільших елементів кожного стовпця
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        try {
            // Ініціалізація матриць A та B типу short
            short[][] matrixA = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };

            short[][] matrixB = {
                    {9, 8, 7},
                    {6, 5, 4},
                    {3, 2, 1}
            };

            System.out.println("Matrix A:");
            printMatrix(matrixA);
            System.out.println("\nMatrix B:");
            printMatrix(matrixB);

            // Виконання операції C = A + B (додавання матриць)
            short[][] matrixC = matrixAddition(matrixA, matrixB);
            System.out.println("\nMatrix C = A + B:");
            printMatrix(matrixC);

            // Обчислення суми найбільших елементів кожного стовпця матриці C
            int sumMaxCols = sumOfMaxInColumns(matrixC);
            System.out.println("\nSum of maximum elements in each column of matrix C: " + sumMaxCols);

        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("ArithmeticException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Виконує поелементне додавання двох матриць типу short
     *
     * @param matrixA перша матриця для додавання
     * @param matrixB друга матриця для додавання
     * @return результуюча матриця після додавання
     * @throws IllegalArgumentException якщо матриці мають неправильний формат або розміри
     * @throws ArithmeticException якщо відбувається переповнення при додаванні
     */
    private static short[][] matrixAddition(short[][] matrixA, short[][] matrixB) {
        checkForExceptions(matrixA, matrixB);

        int rows = matrixA.length;
        int cols = matrixA[0].length;
        short[][] result = new short[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Перевірка на переповнення при додаванні
                int sum = (int) matrixA[i][j] + (int) matrixB[i][j];
                if (sum > Short.MAX_VALUE || sum < Short.MIN_VALUE) {
                    throw new ArithmeticException(
                            String.format("Overflow at position [%d][%d]: %d + %d = %d (exceeds short range)",
                                    i, j, matrixA[i][j], matrixB[i][j], sum));
                }
                result[i][j] = (short) sum;
            }
        }

        return result;
    }

    /**
     * Обчислює суму найбільших елементів кожного стовпця матриці
     *
     * @param matrix вхідна матриця типу short
     * @return сума найбільших елементів всіх стовпців
     * @throws IllegalArgumentException якщо матриця є null, порожньою або має неправильний формат
     * @throws ArithmeticException якщо відбувається переповнення при підсумовуванні
     */
    private static int sumOfMaxInColumns(short[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }

        // Додаткова перевірка на коректність структури матриці
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                throw new IllegalArgumentException("Row " + i + " cannot be null");
            }
            if (i > 0 && matrix[i].length != matrix[0].length) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        long sum = 0; // Використовуємо long для запобігання переповненню

        for (int j = 0; j < cols; j++) {
            short maxVal = matrix[0][j];

            // Знаходимо максимальний елемент у стовпці j
            for (int i = 1; i < rows; i++) {
                if (matrix[i][j] > maxVal) {
                    maxVal = matrix[i][j];
                }
            }

            // Додаємо до суми з перевіркою переповнення
            sum += maxVal;
            if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                throw new ArithmeticException(
                        String.format("Overflow when calculating sum of maximum elements: sum = %d", sum));
            }
        }

        return (int) sum;
    }

    /**
     * Перевіряє матриці на коректність для виконання операції додавання
     *
     * @param matrixA перша матриця для перевірки
     * @param matrixB друга матриця для перевірки
     * @throws IllegalArgumentException якщо матриці не відповідають вимогам для додавання
     */
    private static void checkForExceptions(short[][] matrixA, short[][] matrixB) {
        // Перевірка на null
        if (matrixA == null || matrixB == null) {
            throw new IllegalArgumentException("Matrices cannot be null");
        }

        // Перевірка на порожність
        if (matrixA.length == 0 || matrixB.length == 0) {
            throw new IllegalArgumentException("Matrices cannot be empty");
        }

        // Перевірка на однакову кількість рядків
        if (matrixA.length != matrixB.length) {
            throw new IllegalArgumentException(
                    String.format("Matrices must have the same number of rows: %d vs %d",
                            matrixA.length, matrixB.length));
        }

        // Перевірка кожного рядка
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] == null || matrixB[i] == null) {
                throw new IllegalArgumentException("Row " + i + " cannot be null in either matrix");
            }

            if (matrixA[i].length != matrixB[i].length) {
                throw new IllegalArgumentException(
                        String.format("Row %d must have the same length in both matrices: %d vs %d",
                                i, matrixA[i].length, matrixB[i].length));
            }

            // Перевірка на порожні рядки
            if (matrixA[i].length == 0) {
                throw new IllegalArgumentException("Row " + i + " cannot be empty");
            }
        }
    }

    /**
     * Виводить матриці на екран у форматованому вигляді
     *
     * @param matrix матриця типу short для виводу
     * @throws IllegalArgumentException якщо матриця є null
     */
    private static void printMatrix(short[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix for printing cannot be null");
        }

        for (short[] row : matrix) {
            if (row == null) {
                System.out.println("[null row]");
                continue;
            }

            for (short val : row) {
                System.out.printf("%6d ", val);
            }
            System.out.println();
        }
    }
}

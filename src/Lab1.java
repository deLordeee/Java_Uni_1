
public class Lab1 {


    public static void main(String[] args) {
        try {
            // Init some test matrix
            double[][] matrixA = {
                    {1.5, 2.7, 3.2},
                    {4.1, 5.8, 6.3},
                    {7.9, 8.4, 9.6}
            };

            double[][] matrixB = {
                    {9.1, 8.3, 7.5},
                    {6.7, 5.2, 4.8},
                    {3.4, 2.9, 1.6}
            };

            System.out.println("Matrix A:");
            printMatrix(matrixA);
            System.out.println("\nMatrix B:");
            printMatrix(matrixB);


            double[][] matrixC = Xor(matrixA, matrixB);

            System.out.println("\nMatrix C = A⊕B:");
            printMatrix(matrixC);


            double[] rowAverages = calculateRowAverage(matrixC);

            System.out.println("\nThe average value of  row of matrix C:");
            for (int i = 0; i < rowAverages.length; i++) {
                System.out.printf("Row %d: %.2f%n", i + 1, rowAverages[i]);
            }

        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException : " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("ArithmeticException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static double[][] Xor(double[][] matrixA, double[][] matrixB) {
        checkForExceptions(matrixA, matrixB);

        int rows = matrixA.length;
        int cols = matrixA[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                long bitsA = Double.doubleToLongBits(matrixA[i][j]);
                long bitsB = Double.doubleToLongBits(matrixB[i][j]);


                long xorResult = bitsA ^ bitsB;


                result[i][j] = Double.longBitsToDouble(xorResult);
            }
        }

        return result;
    }


    private static double[] calculateRowAverage(double[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Matrix can not be null or empty");
        }

        int rows = matrix.length;
        double[] averages = new double[rows];

        for (int i = 0; i < rows; i++) {
            if (matrix[i] == null || matrix[i].length == 0) {
                throw new IllegalArgumentException("Row " + i + " can not be null or empty");
            }

            double sum = 0.0;
            int cols = matrix[i].length;

            for (int j = 0; j < cols; j++) {
                if (Double.isNaN(matrix[i][j]) || Double.isInfinite(matrix[i][j])) {
                    throw new ArithmeticException("Error value into [" + i + "][" + j + "]");
                }
                sum += matrix[i][j];
            }

            averages[i] = sum / cols;
        }

        return averages;
    }


    private static void checkForExceptions(double[][] matrixA, double[][] matrixB) {
        if (matrixA == null || matrixB == null) {
            throw new IllegalArgumentException("Matrix can not be null");
        }

        if (matrixA.length == 0 || matrixB.length == 0) {
            throw new IllegalArgumentException("Matrix can not be empty");
        }

        if (matrixA.length != matrixB.length) {
            throw new IllegalArgumentException("Matrix must have the same amount of rows");
        }

        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] == null || matrixB[i] == null) {
                throw new IllegalArgumentException("Rows can not be  null");
            }

            if (matrixA[i].length != matrixB[i].length) {
                throw new IllegalArgumentException("Matrix row must be the same length");
            }
        }
    }


    private static void printMatrix(double[][] matrix) {
        if (matrix == null) {
            System.out.println("Matrix is null");
            return;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%18.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
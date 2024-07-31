import java.util.Scanner;

public class FinancialForecast {
    private static double calculateFutureValue(double value, double growthRate, int timePeriod) {
        if (timePeriod == 0) {
            return value;
        }

        return calculateFutureValue((value * (1 + growthRate)), growthRate, timePeriod - 1);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("\n=============================================\n");
        System.out.println("Financial Forecasting ");
        System.out.println("\n=============================================\n");

        System.out.print("Enter the Present Value: ");
        double presentValue = sc.nextDouble();

        System.out.print("Enter the Grow Rate (in %): ");
        double growthRate = sc.nextDouble() / 100;

        System.out.print("Enter the Time Period (in years): ");
        int periods = sc.nextInt();

        sc.close();

        double futureValue = calculateFutureValue(presentValue, growthRate, periods);

        System.out.println();
        System.out.printf("Predicted Future Value: %.2f \n", futureValue);
    }
}

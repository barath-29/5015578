// Strategy Interface
interface PaymentStrategy {
    abstract void pay(double amount);
}

// Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String creditCardNo;

    public CreditCardPayment(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment Done for $"+ amount +" using Credit Card Number: " + creditCardNo);
    }
}

class PayPalPayment implements PaymentStrategy {
    private int payPalId;

    public PayPalPayment(int payPalId) {
        this.payPalId = payPalId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment Done for $"+ amount +" using PayPal Id: " + payPalId);
    }
}

// Context Class
class PaymentContext {
    PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy =paymentStrategy;
    }

    public void makePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}

public class Ex8StrategyPatternExample {
    public static void main(String[] args) {
        PaymentContext paymentContext = new PaymentContext();

        paymentContext.setPaymentStrategy(new CreditCardPayment("12345678910111213"));
        paymentContext.makePayment(1000);

        paymentContext.setPaymentStrategy(new PayPalPayment(10089574));
        paymentContext.makePayment(2000);
    }
}
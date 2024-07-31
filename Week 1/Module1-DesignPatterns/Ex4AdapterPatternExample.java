// Target
interface PaymentProcessor {
    abstract void processPayment();
}

// Adaptees
class NetBankingPayment {
    public void makeNBPayment() {
        System.out.println("Made payment via Net Banking");
    }
}

class UpiPayment {
    public void makeUPIPayment() {
        System.out.println("Made payment via UPI");
    }
}

class CreditCardPayment {
    public void makeCCPayment() {
        System.out.println("Made payment via Credit Card");
    }
}

// Adapters
class NetBankingAdapter implements PaymentProcessor {
    NetBankingPayment netBankingPayment; 

    public NetBankingAdapter() {
        netBankingPayment = new NetBankingPayment();
    }

    @Override
    public void processPayment() {
        this.netBankingPayment.makeNBPayment();
    }
}

class UpiAdapter implements PaymentProcessor {
    UpiPayment upiPayment;

    public UpiAdapter() {
        upiPayment = new UpiPayment();
    }

    @Override
    public void processPayment() {
        this.upiPayment.makeUPIPayment();
    }
}

class CreditCardAdapter implements PaymentProcessor {
    CreditCardPayment creditCardPayment;

    public CreditCardAdapter() {
        creditCardPayment = new CreditCardPayment();
    }

    @Override
    public void processPayment() {
        this.creditCardPayment.makeCCPayment();
    }
}

public class Ex4AdapterPatternExample {
    public static void main(String[] args) {
        NetBankingAdapter netBanking = new NetBankingAdapter();
        netBanking.processPayment();

        UpiAdapter upi = new UpiAdapter();
        upi.processPayment();

        CreditCardAdapter creditCard = new CreditCardAdapter();
        creditCard.processPayment();
    }
}

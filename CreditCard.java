import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ===================== CREDIT CARD CLASS =====================
public class CreditCard {
    private String cardNumber;
    private double creditLimit;
    private double usedCredit;
    private String holderName;
    private String expiryDate;

    public CreditCard(String cardNumber, double creditLimit, String holderName) {
        this.cardNumber  = cardNumber;
        this.creditLimit = creditLimit;
        this.usedCredit  = 0;
        this.holderName  = holderName;
        // Expiry: 3 years from now
        this.expiryDate  = LocalDate.now().plusYears(3)
                           .format(DateTimeFormatter.ofPattern("MM/yy"));
    }

    public boolean spend(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid credit card spend amount.");
            return false;
        }
        if (usedCredit + amount > creditLimit) {
            System.out.printf("Credit card limit exceeded! Available: $%.2f, Requested: $%.2f%n",
                getAvailableCredit(), amount);
            return false;
        }
        usedCredit += amount;
        System.out.printf("Credit card charged: $%.2f | Used: $%.2f / Limit: $%.2f%n",
            amount, usedCredit, creditLimit);
        return true;
    }

    public boolean payBill(double amount, double accountBalance) {
        if (amount <= 0 || amount > usedCredit) {
            System.out.printf("Invalid payment. Outstanding balance: $%.2f%n", usedCredit);
            return false;
        }
        if (amount > accountBalance) {
            System.out.println("Insufficient account balance to pay credit card bill.");
            return false;
        }
        usedCredit -= amount;
        System.out.printf("Credit card bill paid: $%.2f | Remaining due: $%.2f%n",
            amount, usedCredit);
        return true;
    }

    public void display() {
        System.out.println("  ┌─────────────────────────────────────┐");
        System.out.println("  │         CREDIT CARD DETAILS          │");
        System.out.println("  ├─────────────────────────────────────┤");
        System.out.printf ("  │  Card Number  : %-20s │%n", cardNumber);
        System.out.printf ("  │  Holder       : %-20s │%n", holderName);
        System.out.printf ("  │  Expiry       : %-20s │%n", expiryDate);
        System.out.printf ("  │  Credit Limit : $%-19.2f │%n", creditLimit);
        System.out.printf ("  │  Used Credit  : $%-19.2f │%n", usedCredit);
        System.out.printf ("  │  Available    : $%-19.2f │%n", getAvailableCredit());
        System.out.println("  └─────────────────────────────────────┘");
    }

    public double getAvailableCredit() { return creditLimit - usedCredit; }
    public double getUsedCredit()      { return usedCredit; }
    public double getCreditLimit()     { return creditLimit; }
    public String getCardNumber()      { return cardNumber; }

    public String toArchiveString() {
        return String.format("Card: %s | Limit: $%.2f | Used: $%.2f | Expiry: %s",
            cardNumber, creditLimit, usedCredit, expiryDate);
    }
}

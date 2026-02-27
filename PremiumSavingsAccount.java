// ===================== PREMIUM SAVINGS ACCOUNT =====================
public class PremiumSavingsAccount extends Account {

    private double     interestRate;
    private CreditCard creditCard;
    private static final double WITHDRAWAL_LIMIT = 5000.0;
    private static final double MIN_BALANCE      = 1000.0;

    public PremiumSavingsAccount(String accountNumber, double initialBalance,
                                  double interestRate, String holderName) {
        super(accountNumber, "Premium Savings", initialBalance);
        this.interestRate = interestRate >= 0 ? interestRate : 0;
        double cardLimit  = Math.max(initialBalance * 2, 5000.0);
        this.creditCard   = new CreditCard("PREM-" + accountNumber, cardLimit, holderName);
        System.out.println("[Premium] Credit card auto-issued for account #" + accountNumber);
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Cannot withdraw — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return false;
        }
        if (amount <= 0) { System.out.println("Invalid withdrawal amount."); return false; }
        if (amount > WITHDRAWAL_LIMIT) {
            System.out.printf("Premium withdrawal limit is $%.2f per transaction.%n", WITHDRAWAL_LIMIT);
            return false;
        }
        if ((balance - amount) < MIN_BALANCE) {
            System.out.printf("Minimum balance of $%.2f must be maintained. Available to withdraw: $%.2f%n",
                MIN_BALANCE, balance - MIN_BALANCE);
            return false;
        }
        balance -= amount;
        System.out.printf("Withdrew: $%.2f. New balance: $%.2f%n", amount, balance);
        recordTransaction(new Transaction("WITHDRAWAL", amount, balance, "Premium savings withdrawal"));
        return true;
    }

    public void addInterest() {
        if (!isActive()) {
            System.out.println("Cannot add interest — Account #" + accountNumber + " is " + status + ".");
            return;
        }
        double interestAmount = balance * (interestRate / 100);
        balance += interestAmount;
        System.out.printf("Interest added: $%.2f (Rate: %.2f%%). New balance: $%.2f%n",
            interestAmount, interestRate, balance);
        recordTransaction(new Transaction("DEPOSIT", interestAmount, balance,
            "Interest at " + interestRate + "%"));
    }

    public void spendOnCreditCard(double amount) {
        if (!isActive()) { System.out.println("Account not ACTIVE — credit card spending disabled."); return; }
        creditCard.spend(amount);
    }

    public void payCreditCardBill(double paymentAmount) {
        if (!isActive()) { System.out.println("Account not ACTIVE — cannot pay credit card bill."); return; }
        if (creditCard.payBill(paymentAmount, balance)) {
            balance -= paymentAmount;
            recordTransaction(new Transaction("WITHDRAWAL", paymentAmount, balance,
                "Credit card bill payment"));
        }
    }

    public CreditCard getCreditCard() { return creditCard; }

    @Override
    public void display() {
        System.out.println("Account Number  : " + accountNumber);
        System.out.println("Type            : Premium Savings Account");
        System.out.printf ("Balance         : $%.2f%n", balance);
        System.out.printf ("Interest Rate   : %.2f%%%n", interestRate);
        System.out.printf ("Withdrawal Limit: $%.2f%n", WITHDRAWAL_LIMIT);
        System.out.printf ("Min Balance     : $%.2f%n", MIN_BALANCE);
        printStatusLine();
        System.out.println("--- Credit Card ---");
        creditCard.display();
        System.out.println("---");
    }

    @Override
    public String toArchiveString() {
        return super.toArchiveString()
            + "  Interest Rate   : " + interestRate + "%\n"
            + "  Account Tier    : Premium Savings\n"
            + "  Credit Card     : " + creditCard.toArchiveString() + "\n";
    }

    public double getInterestRate() { return interestRate; }
}

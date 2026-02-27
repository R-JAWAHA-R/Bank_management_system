// ===================== BASIC SAVINGS ACCOUNT =====================
public class BasicSavingsAccount extends Account {

    private double interestRate;
    private static final double WITHDRAWAL_LIMIT  = 500.0;
    private static final int    FREE_TRANSACTIONS = 3;
    private static final double TRANSACTION_FEE   = 2.0;
    private int transactionsThisMonth = 0;

    public BasicSavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, "Basic Savings", initialBalance);
        this.interestRate = interestRate >= 0 ? interestRate : 0;
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Cannot withdraw — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return false;
        }
        if (amount <= 0 || amount > WITHDRAWAL_LIMIT) {
            System.out.printf("Basic Savings withdrawal limit is $%.2f. Requested: $%.2f%n",
                WITHDRAWAL_LIMIT, amount);
            return false;
        }
        double totalDeduct = amount;
        if (transactionsThisMonth >= FREE_TRANSACTIONS) {
            totalDeduct += TRANSACTION_FEE;
            System.out.printf("Transaction fee of $%.2f applied (exceeded %d free transactions).%n",
                TRANSACTION_FEE, FREE_TRANSACTIONS);
        }
        if (totalDeduct > balance) {
            System.out.println("Insufficient funds (including fees).");
            return false;
        }
        balance -= totalDeduct;
        transactionsThisMonth++;
        System.out.printf("Withdrew: $%.2f. New balance: $%.2f%n", amount, balance);
        recordTransaction(new Transaction("WITHDRAWAL", amount, balance, "Basic savings withdrawal"));
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

    @Override
    public void display() {
        System.out.println("Account Number  : " + accountNumber);
        System.out.println("Type            : Basic Savings Account");
        System.out.printf ("Balance         : $%.2f%n", balance);
        System.out.printf ("Interest Rate   : %.2f%%%n", interestRate);
        System.out.printf ("Withdrawal Limit: $%.2f%n", WITHDRAWAL_LIMIT);
        System.out.printf ("Free Txn/Month  : %d (Used: %d)%n", FREE_TRANSACTIONS, transactionsThisMonth);
        printStatusLine();
        System.out.println("---");
    }

    @Override
    public String toArchiveString() {
        return super.toArchiveString()
            + "  Interest Rate   : " + interestRate + "%\n"
            + "  Account Tier    : Basic Savings\n";
    }

    public double getInterestRate() { return interestRate; }
}

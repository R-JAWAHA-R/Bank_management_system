// ===================== CURRENT ACCOUNT =====================
public class CurrentAccount extends Account {

    private double overdraft;

    public CurrentAccount(String accountNumber, double initialBalance, double overdraft) {
        super(accountNumber, "Current", initialBalance);
        this.overdraft = overdraft >= 0 ? overdraft : 0;
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Cannot withdraw — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return false;
        }
        double totalAvailable = balance + overdraft;
        if (amount > 0 && amount <= totalAvailable) {
            balance -= amount;
            System.out.printf("Withdrew: $%.2f. New balance: $%.2f (Overdraft used: $%.2f)%n",
                amount, balance, (balance < 0 ? -balance : 0));
            recordTransaction(new Transaction("WITHDRAWAL", amount, balance, "Current withdrawal"));
            return true;
        } else {
            System.out.println("Withdrawal exceeds available balance + overdraft limit.");
            return false;
        }
    }

    @Override
    public void display() {
        System.out.println("Account Number  : " + accountNumber);
        System.out.println("Type            : Current Account");
        System.out.printf ("Balance         : $%.2f%n", balance);
        System.out.printf ("Overdraft Limit : $%.2f%n", overdraft);
        System.out.printf ("Available       : $%.2f%n", balance + overdraft);
        printStatusLine();
        System.out.println("---");
    }

    @Override
    public String toArchiveString() {
        return super.toArchiveString()
            + "  Overdraft Limit : $" + overdraft + "\n";
    }

    public double getOverdraft() { return overdraft; }
}

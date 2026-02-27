import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ===================== ABSTRACT ACCOUNT =====================
public abstract class Account implements Transactable, Displayable {

    protected String        accountNumber;
    protected String        accountType;
    protected double        balance;
    protected AccountStatus status;
    protected LocalDate     lastTransactionDate;

    private List<Transaction> transactions = new ArrayList<>();

    private static final DateTimeFormatter dateFmt =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Account(String accountNumber, String accountType, double initialBalance) {
        this.accountNumber       = accountNumber;
        this.accountType         = accountType;
        this.balance             = initialBalance >= 0 ? initialBalance : 0;
        this.status              = AccountStatus.ACTIVE;
        this.lastTransactionDate = LocalDate.now();
    }

    // ── Status helpers ──────────────────────────────────────────────

    public void setActive() {
        if (status == AccountStatus.CLOSED) {
            System.out.println("Account #" + accountNumber + " is CLOSED and cannot be reactivated.");
            return;
        }
        status = AccountStatus.ACTIVE;
        System.out.println("Account #" + accountNumber + " is now ACTIVE.");
    }

    public void setInactive() {
        if (status == AccountStatus.CLOSED) {
            System.out.println("Account #" + accountNumber + " is CLOSED.");
            return;
        }
        status = AccountStatus.INACTIVE;
        System.out.println("Account #" + accountNumber + " is now INACTIVE.");
    }

    public void checkAndAutoInactivate() {
        if (status == AccountStatus.ACTIVE &&
            lastTransactionDate != null &&
            lastTransactionDate.isBefore(LocalDate.now().minusYears(1))) {
            status = AccountStatus.INACTIVE;
            System.out.println("[AUTO] Account #" + accountNumber
                + " set to INACTIVE — no transaction in over 1 year "
                + "(last: " + lastTransactionDate.format(dateFmt) + ").");
        }
    }

    public boolean isActive() {
        checkAndAutoInactivate();
        return status == AccountStatus.ACTIVE;
    }

    public boolean isClosed()        { return status == AccountStatus.CLOSED; }
    public boolean isInactive()      { return status == AccountStatus.INACTIVE; }
    public AccountStatus getStatus() { return status; }

    protected void printStatusLine() {
        System.out.println("Status: " + status
            + (lastTransactionDate != null
               ? "  |  Last Transaction: " + lastTransactionDate.format(dateFmt)
               : ""));
    }

    // ── Transaction recording ─────────────────────────────────────

    protected void recordTransaction(Transaction t) {
        transactions.add(t);
        if (transactions.size() > 5) transactions.remove(0);
        lastTransactionDate = LocalDate.now();
    }

    @Override
    public void printLastFiveTransactions() {
        System.out.println("\n--- Last " + transactions.size()
            + " Transaction(s) for Account: " + accountNumber + " ---");
        if (transactions.isEmpty()) {
            System.out.println("  No transactions recorded yet.");
        } else {
            for (Transaction t : transactions) t.printStatement();
        }
        System.out.println("------------------------------------------------------------");
    }

    // ── Transactable implementations ─────────────────────────────

    @Override
    public void deposit(double amount) {
        if (!isActive()) {
            System.out.println("Cannot deposit — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return;
        }
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
            recordTransaction(new Transaction("DEPOSIT", amount, balance, "Cash deposit"));
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Cannot withdraw — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return false;
        }
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + ". New balance: $" + balance);
            recordTransaction(new Transaction("WITHDRAWAL", amount, balance, "Cash withdrawal"));
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }

    @Override
    public boolean withdrawForTransfer(double amount, String toAccNum) {
        if (!isActive()) {
            System.out.println("Cannot transfer — Account #" + accountNumber
                + " is " + status + ". Please activate it first.");
            return false;
        }
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recordTransaction(new Transaction("TRANSFER_OUT", amount, balance,
                "Transfer to Acc#" + toAccNum));
            return true;
        }
        return false;
    }

    @Override
    public void depositForTransfer(double amount, String fromAccNum) {
        balance += amount;
        recordTransaction(new Transaction("TRANSFER_IN", amount, balance,
            "Transfer from Acc#" + fromAccNum));
    }

    // ── Archive support ──────────────────────────────────────────

    public String toArchiveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Account Number  : ").append(accountNumber).append("\n");
        sb.append("  Account Type    : ").append(accountType).append("\n");
        sb.append("  Final Balance   : $").append(String.format("%.2f", balance)).append("\n");
        sb.append("  Status          : ").append(status).append("\n");
        sb.append("  Last Transaction: ")
          .append(lastTransactionDate != null
              ? lastTransactionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
              : "N/A")
          .append("\n");
        sb.append("  Last 5 Transactions:\n");
        if (transactions.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (Transaction t : transactions)
                sb.append("    ").append(t.toArchiveString()).append("\n");
        }
        return sb.toString();
    }

    // ── Getters ──────────────────────────────────────────────────

    public double    getBalance()       { return balance; }
    public String    getAccountNumber() { return accountNumber; }
    public String    getAccountType()   { return accountType; }
    public LocalDate getLastTransactionDate() { return lastTransactionDate; }
}

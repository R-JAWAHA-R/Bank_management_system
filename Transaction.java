import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ===================== TRANSACTION CLASS =====================
public class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private String description;
    private String timestamp;

    private static final DateTimeFormatter fmt =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Transaction(String type, double amount, double balanceAfter, String description) {
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.description  = description;
        this.timestamp    = LocalDateTime.now().format(fmt);
    }

    public void printStatement() {
        System.out.printf("  [%s] %-15s Amount: $%8.2f  |  Balance: $%8.2f  |  %s%n",
            timestamp, type, amount, balanceAfter, description);
    }

    public String toArchiveString() {
        return String.format("[%s] %-15s Amount: $%8.2f | Balance: $%8.2f | %s",
            timestamp, type, amount, balanceAfter, description);
    }
}

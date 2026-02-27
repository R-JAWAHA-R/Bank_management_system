// ===================== INTERFACE: Transactable =====================
public interface Transactable {
    void deposit(double amount);
    boolean withdraw(double amount);
    boolean withdrawForTransfer(double amount, String toAccNum);
    void depositForTransfer(double amount, String fromAccNum);
    void printLastFiveTransactions();
}

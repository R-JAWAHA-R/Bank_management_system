// ===================== PLATINUM SAVINGS ACCOUNT =====================
public class PlatinumSavingsAccount extends Account {

    private double          interestRate;
    private CreditCard      creditCard;
    private InternetBanking internetBanking;
    private static final double WITHDRAWAL_LIMIT = 50000.0;
    private static final double MIN_BALANCE      = 10000.0;

    public PlatinumSavingsAccount(String accountNumber, double initialBalance,
                                   double interestRate, String holderName,
                                   String ibUsername, String ibPassword) {
        super(accountNumber, "Platinum Savings", initialBalance);
        this.interestRate    = interestRate >= 0 ? interestRate : 0;
        double cardLimit     = Math.max(initialBalance * 3, 25000.0);
        this.creditCard      = new CreditCard("PLAT-" + accountNumber, cardLimit, holderName);
        this.internetBanking = new InternetBanking(accountNumber, ibUsername, ibPassword, this);
        System.out.println("[Platinum] Platinum Credit Card auto-issued for account #" + accountNumber);
        System.out.println("[Platinum] Internet Banking activated. Username: " + ibUsername);
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
            System.out.printf("Platinum withdrawal limit is $%.2f per transaction.%n", WITHDRAWAL_LIMIT);
            return false;
        }
        if ((balance - amount) < MIN_BALANCE) {
            System.out.printf("Minimum balance of $%.2f must be maintained. Available to withdraw: $%.2f%n",
                MIN_BALANCE, balance - MIN_BALANCE);
            return false;
        }
        balance -= amount;
        System.out.printf("Withdrew: $%.2f. New balance: $%.2f%n", amount, balance);
        recordTransaction(new Transaction("WITHDRAWAL", amount, balance, "Platinum savings withdrawal"));
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

    // ── Credit Card ─────────────────────────────────────────────────

    public void spendOnCreditCard(double amount) {
        if (!isActive()) { System.out.println("Account not ACTIVE."); return; }
        creditCard.spend(amount);
    }

    public void payCreditCardBill(double paymentAmount) {
        if (!isActive()) { System.out.println("Account not ACTIVE."); return; }
        if (creditCard.payBill(paymentAmount, balance)) {
            balance -= paymentAmount;
            recordTransaction(new Transaction("WITHDRAWAL", paymentAmount, balance,
                "Platinum credit card bill payment"));
        }
    }

    // ── Internet Banking proxy ───────────────────────────────────────

    public void loginInternetBanking(String username, String password) {
        internetBanking.loginInternetBanking(username, password);
    }

    public void viewAccountSummaryOnline() {
        internetBanking.viewAccountSummaryOnline();
    }

    public void payBillOnline(String billerName, double amount) {
        internetBanking.payBillOnline(billerName, amount);
    }

    public void changeInternetBankingPassword(String oldPass, String newPass) {
        internetBanking.changeInternetBankingPassword(oldPass, newPass);
    }

    public void logoutInternetBanking() {
        internetBanking.logout();
    }

    public boolean isInternetBankingLoggedIn() {
        return internetBanking.isLoggedIn();
    }

    public CreditCard      getCreditCard()      { return creditCard; }
    public InternetBanking getInternetBanking() { return internetBanking; }

    @Override
    public void display() {
        System.out.println("Account Number  : " + accountNumber);
        System.out.println("Type            : Platinum Savings Account");
        System.out.printf ("Balance         : $%.2f%n", balance);
        System.out.printf ("Interest Rate   : %.2f%%%n", interestRate);
        System.out.printf ("Withdrawal Limit: $%.2f%n", WITHDRAWAL_LIMIT);
        System.out.printf ("Min Balance     : $%.2f%n", MIN_BALANCE);
        System.out.println("Internet Banking: ENABLED");
        printStatusLine();
        System.out.println("--- Platinum Credit Card ---");
        creditCard.display();
        System.out.println("---");
    }

    @Override
    public String toArchiveString() {
        return super.toArchiveString()
            + "  Interest Rate   : " + interestRate + "%\n"
            + "  Account Tier    : Platinum Savings\n"
            + "  Credit Card     : " + creditCard.toArchiveString() + "\n"
            + "  Internet Banking: " + internetBanking.toArchiveString() + "\n";
    }

    public double getInterestRate() { return interestRate; }
}

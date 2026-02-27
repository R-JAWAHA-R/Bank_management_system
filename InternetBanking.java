// ===================== INTERNET BANKING CLASS =====================
public class InternetBanking implements InternetBankingEnabled {
    private String  accountNumber;
    private String  username;
    private String  password;
    private boolean loggedIn;
    private Account linkedAccount;

    public InternetBanking(String accountNumber, String username,
                            String password, Account linkedAccount) {
        this.accountNumber = accountNumber;
        this.username      = username;
        this.password      = password;
        this.loggedIn      = false;
        this.linkedAccount = linkedAccount;
    }

    @Override
    public void loginInternetBanking(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            loggedIn = true;
            System.out.println("✔ Internet Banking login successful! Welcome, " + username);
        } else {
            loggedIn = false;
            System.out.println("✘ Invalid username or password. Access denied.");
        }
    }

    @Override
    public void viewAccountSummaryOnline() {
        if (!loggedIn) { printNotLoggedIn(); return; }
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       INTERNET BANKING SUMMARY       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Account No : %-23s║%n", accountNumber);
        System.out.printf ("║  Balance    : $%-22.2f║%n", linkedAccount.getBalance());
        System.out.printf ("║  Status     : %-23s║%n", linkedAccount.getStatus());
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public void payBillOnline(String billerName, double amount) {
        if (!loggedIn) { printNotLoggedIn(); return; }
        if (!linkedAccount.isActive()) {
            System.out.println("Cannot pay bill — account is " + linkedAccount.getStatus());
            return;
        }
        if (amount <= 0 || amount > linkedAccount.getBalance()) {
            System.out.println("Invalid amount or insufficient funds for bill payment.");
            return;
        }
        linkedAccount.withdraw(amount);
        System.out.printf("✔ Bill paid online! Biller: %s | Amount: $%.2f%n", billerName, amount);
    }

    @Override
    public void changeInternetBankingPassword(String oldPass, String newPass) {
        if (!loggedIn) { printNotLoggedIn(); return; }
        if (!this.password.equals(oldPass)) {
            System.out.println("✘ Incorrect old password. Password not changed.");
            return;
        }
        if (newPass == null || newPass.length() < 6) {
            System.out.println("New password must be at least 6 characters.");
            return;
        }
        this.password = newPass;
        System.out.println("✔ Internet banking password changed successfully.");
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Logged out of Internet Banking.");
    }

    public boolean isLoggedIn() { return loggedIn; }

    public String toArchiveString() {
        return "IB Username: " + username + " | Account: " + accountNumber;
    }

    private void printNotLoggedIn() {
        System.out.println("✘ Please login to Internet Banking first.");
    }
}

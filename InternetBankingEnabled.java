// ===================== INTERFACE: InternetBankingEnabled =====================
public interface InternetBankingEnabled {
    void loginInternetBanking(String username, String password);
    void viewAccountSummaryOnline();
    void payBillOnline(String billerName, double amount);
    void changeInternetBankingPassword(String oldPass, String newPass);
}

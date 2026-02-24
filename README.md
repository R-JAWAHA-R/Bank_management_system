# Bank Management System — Project Structure

## How to Compile & Run
```
javac *.java
java BankApp
```

## File Structure

| File | Type | Description |
|------|------|-------------|
| AccountStatus.java | Enum | ACTIVE / INACTIVE / CLOSED |
| Transactable.java | Interface | deposit, withdraw, transfer methods |
| Displayable.java | Interface | display() method |
| InternetBankingEnabled.java | Interface | login, viewSummary, payBill, changePassword |
| Transaction.java | Class | Stores a single transaction record |
| CreditCard.java | Class | Credit card with spend/payBill/display |
| InternetBanking.java | Class | Online banking (login-protected) |
| Account.java | Abstract Class | Base class for all account types |
| BasicSavingsAccount.java | Class | $500 limit, 3 free txns/month, $2 fee after |
| PremiumSavingsAccount.java | Class | $5000 limit, min $1000, + Credit Card |
| PlatinumSavingsAccount.java | Class | $50000 limit, min $10000, + Credit Card + Internet Banking |
| CurrentAccount.java | Class | Overdraft support |
| TransferService.java | Class | Static transfer between accounts |
| Customer.java | Class | Holds customer info + list of accounts |
| ClosedAccountArchive.java | Class | Saves closed account records to closed_accounts.txt |
| BankApp.java | Main Class | Entry point — menu-driven application |

## Class Hierarchy

```
Account (abstract)
├── BasicSavingsAccount
├── PremiumSavingsAccount  ──→  CreditCard
├── PlatinumSavingsAccount ──→  CreditCard
│                          ──→  InternetBanking
└── CurrentAccount

Customer ──→ List<Account>
TransferService (static utility)
ClosedAccountArchive (static utility)
```

## Interfaces
- Transactable → implemented by Account
- Displayable  → implemented by Account, Customer
- InternetBankingEnabled → implemented by InternetBanking

import java.util.ArrayList;
import java.util.List;

// ===================== CUSTOMER =====================
public class Customer implements Displayable {

    private String        name;
    private long          contactNo;
    private String        mail;
    private String        gender;
    private String        dob;
    private List<Account> accounts;

    public Customer(String name, long contactNo, String mail, String gender, String dob) {
        this.name      = name;
        this.contactNo = contactNo;
        this.mail      = mail;
        this.gender    = gender;
        this.dob       = dob;
        this.accounts  = new ArrayList<>();
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
        System.out.println("Account #" + acc.getAccountNumber() + " added successfully to " + name);
    }

    @Override
    public void display() {
        System.out.println("\n=== Customer Details ===");
        System.out.println("Name   : " + name);
        System.out.println("Contact: " + contactNo);
        System.out.println("Email  : " + mail);
        System.out.println("Gender : " + gender);
        System.out.println("DOB    : " + dob);
        System.out.println("\nAccounts:");
        boolean hasVisible = false;
        for (Account acc : accounts) {
            if (!acc.isClosed()) {
                acc.display();
                hasVisible = true;
            }
        }
        if (!hasVisible) System.out.println("  No active accounts linked.");
    }

    public String toArchiveString() {
        return "Name   : " + name + "\n"
            + "Contact: " + contactNo + "\n"
            + "Email  : " + mail + "\n"
            + "Gender : " + gender + "\n"
            + "DOB    : " + dob + "\n";
    }

    public String        getName()     { return name; }
    public List<Account> getAccounts() { return accounts; }
}

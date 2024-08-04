import java.util.Scanner;

class BankAccount {

    // Class variables
    String accountHolderName;
    String accountNumber;
    double balance;

    // Constructor to initialize bank account details
    BankAccount(String holderName, String accNumber, double initialBalance) {
        this.accountHolderName = holderName;
        this.accountNumber = accNumber;
        this.balance = initialBalance;
    }

    // Method to deposit money
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money
    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    // Method to check balance
    void checkBalance() {
        System.out.println("Account Balance: $" + balance);
    }

    // Method to print account details
    void getAccountDetails() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Balance: $" + balance);
    }
}

class BankAccountTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creating and initializing BankAccount objects
        BankAccount account1 = new BankAccount("Alice Johnson", "123456789", 1000.0);
        BankAccount account2 = new BankAccount("Bob Smith", "987654321", 500.0);

        // Printing details of the first account
        account1.getAccountDetails();
        account1.deposit(500);
        account1.withdraw(200);
        account1.checkBalance();
        System.out.println();

        // Printing details of the second account
        account2.getAccountDetails();
        account2.deposit(300);
        account2.withdraw(700);
        account2.checkBalance();
        System.out.println();

        // Uncomment the following lines to allow user input for account operations

        // System.out.println("Enter account holder name:");
        // String holderName = sc.nextLine();
        // System.out.println("Enter account number:");
        // String accNumber = sc.nextLine();
        // System.out.println("Enter initial balance:");
        // double initialBalance = sc.nextDouble();
        // BankAccount userAccount = new BankAccount(holderName, accNumber, initialBalance);
        //
        // userAccount.getAccountDetails();
        //
        // System.out.println("Enter amount to deposit:");
        // double depositAmount = sc.nextDouble();
        // userAccount.deposit(depositAmount);
        //
        // System.out.println("Enter amount to withdraw:");
        // double withdrawAmount = sc.nextDouble();
        // userAccount.withdraw(withdrawAmount);
        //
        // userAccount.checkBalance();

        sc.close(); // Close the scanner when done
    }
}

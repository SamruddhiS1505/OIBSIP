import java.util.ArrayList;
import java.util.Scanner;

// Class to store transaction history
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getDetails() {
        return type + ": $" + amount;
    }
}

// ATM User class
class ATMUser {
    private double balance;
    private int userId;
    private int pin;
    private ArrayList<Transaction> transactions;

    public ATMUser(int userId, int pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public boolean verifyUser(int enteredUserId, int enteredPin) {
        return this.userId == enteredUserId && this.pin == enteredPin;
    }

    public int getUserId() {  // Getter method for userId
        return userId;
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance!");
        }
    }

    public void transfer(ATMUser recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recipient.balance += amount;
            this.transactions.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            recipient.transactions.add(new Transaction("Transfer from " + this.getUserId(), amount));
            System.out.println("Successfully transferred $" + amount + " to User ID: " + recipient.getUserId());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance!");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t.getDetails());
        }
    }
}

// Main ATM Interface
public class ATMInterface {  // Ensure file name is ATMInterface.java
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMUser user1 = new ATMUser(101, 1234, 1000.00);
        ATMUser user2 = new ATMUser(102, 5678, 2000.00); // Another user for transfer demonstration

        System.out.print("Enter User ID: ");
        int enteredUserId = scanner.nextInt();
        System.out.print("Enter PIN: ");
        int enteredPin = scanner.nextInt();

        ATMUser currentUser = (user1.verifyUser(enteredUserId, enteredPin)) ? user1 :
                             (user2.verifyUser(enteredUserId, enteredPin)) ? user2 : null;

        if (currentUser == null) {
            System.out.println("Incorrect User ID or PIN! Exiting...");
            return;
        }

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient User ID: ");
                    int recipientId = scanner.nextInt();
                    ATMUser recipient = (recipientId == user1.getUserId()) ? user1 :
                                       (recipientId == user2.getUserId()) ? user2 : null;
                    if (recipient == null || recipient == currentUser) {
                        System.out.println("Invalid recipient!");
                        break;
                    }
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    currentUser.transfer(recipient, transferAmount);
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

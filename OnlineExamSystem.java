import java.util.*;

class User {
    private String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void updateProfile(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
        System.out.println("Profile updated successfully!");
    }
}

class Question {
    private String question, optionA, optionB, optionC, correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.correctAnswer = correctAnswer;
    }

    public void display() {
        System.out.println(question);
        System.out.println("A. " + optionA);
        System.out.println("B. " + optionB);
        System.out.println("C. " + optionC);
    }

    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Exam {
    private List<Question> questions = new ArrayList<>();
    private int score = 0;

    public Exam() {
        questions.add(new Question("What is Data Science?", "Study of data", "Study of algorithms", "Study of networks", "A"));
        questions.add(new Question("Which language is widely used for Data Science?", "Java", "Python", "C++", "B"));
        questions.add(new Question("What is supervised learning?", "Learning without labels", "Learning with labeled data", "Learning with no input", "B"));
        questions.add(new Question("Which library is used for data visualization?", "NumPy", "Matplotlib", "TensorFlow", "B"));
        questions.add(new Question("What is the purpose of machine learning?", "Automate decision-making", "Store data", "Manually process data", "A"));
    }

    public void startExam(Scanner sc) {
        System.out.println("Exam Started! You have 30 seconds.");
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 30000; // 30 seconds

        for (Question q : questions) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("\nTime is up! Auto-submitting your exam.");
                break;
            }
            q.display();
            System.out.print("Enter your choice (A/B/C): ");
            String answer = sc.next();
            if (q.checkAnswer(answer)) {
                score++;
            }
        }
        System.out.println("Exam Finished. Your score: " + score + "/" + questions.size());
    }
}

public class OnlineExamSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, User> users = new HashMap<>();

        System.out.println("Register new user:");
        System.out.print("Enter username: ");
        String regUsername = sc.next();
        System.out.print("Enter password: ");
        String regPassword = sc.next();
        User user = new User(regUsername, regPassword);
        users.put(regUsername, user);

        System.out.println("\nLogin:");
        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        if (users.containsKey(username) && users.get(username).login(username, password)) {
            System.out.println("Login Successful!");
            while (true) {
                System.out.println("\n1. Start Exam\n2. Update Profile\n3. Logout");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Exam exam = new Exam();
                        exam.startExam(sc);
                        break;
                    case 2:
                        System.out.print("Enter new username: ");
                        String newUsername = sc.next();
                        System.out.print("Enter new password: ");
                        String newPassword = sc.next();
                        user.updateProfile(newUsername, newPassword);
                        users.remove(username);
                        users.put(newUsername, user);
                        username = newUsername;
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid option! Try again.");
                }
            }
        } else {
            System.out.println("Invalid credentials!");
        }
        sc.close();
    }
}

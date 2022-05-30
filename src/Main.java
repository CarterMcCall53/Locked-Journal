import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static int code = 0;
    static String verificationAnswer;
    static String verificationQuestion;
    static boolean verificationStatus = false;
    static File saveVerifyStatus = new File("./Variables/verificationStatus.txt");
    static File saveCode = new File("./Variables/code.txt");
    static File saveVerifyQuestion = new File("./Variables/verificationQuestion.txt");
    static File saveVerifyAnswer = new File("./Variables/verificationAnswer.txt");
    static LocalDate date = java.time.LocalDate.now();
    static File input = new File("./Saves/" + date + ".txt");

    //code for entering the entry code
    public static void main(String[] args) {
        fileCreate();
        loadVerifyStatus();
        loadCode();
        loadVerifyQuestion();
        loadVerifyAnswer();
        System.out.println("Please Enter the Code: ");
        Scanner scn = new Scanner(System.in);
        int input;
        input = scn.nextInt();
        if (input == code) {
            firstPage();
        } else {
            System.out.println("INCORRECT! Try Again.");
            Scanner again = new Scanner(System.in);
            int inputAgain;
            inputAgain = again.nextInt();
            while (inputAgain != code) {
                System.out.println("INCORRECT! Try Again.");
                inputAgain = again.nextInt();
            }
            firstPage();
        }
    }

    private static void fileCreate() {
        try {
            saveVerifyStatus.getParentFile().mkdir();
            saveVerifyStatus.createNewFile();
            saveCode.createNewFile();
            saveVerifyAnswer.createNewFile();
            saveVerifyQuestion.createNewFile();
            input.getParentFile().mkdir();
            input.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed to create files!");
        }
    }

    //loads verification status
    public static void loadVerifyStatus() {
        try {
            Scanner scn = new Scanner(saveVerifyStatus);
            if (scn.hasNext()) {
                verificationStatus = scn.next().equalsIgnoreCase("true");
            }
        } catch (IOException e) {
        }
    }

    //loads entry code
    public static void loadCode() {
        try {
            Scanner scn = new Scanner(saveCode);
            if (scn.hasNextInt()) {
                code = scn.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Code file not found!");
        }
    }

    //loads verification question
    public static void loadVerifyQuestion() {
        try {
            Scanner scn = new Scanner(saveVerifyQuestion);
            if (scn.hasNextLine()) {
                verificationQuestion = scn.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Verification question file not found!");
        }
    }

    //loads verification answer
    public static void loadVerifyAnswer() {
        try {
            Scanner scn = new Scanner(saveVerifyAnswer);
            if (scn.hasNextLine()) {
                verificationAnswer = scn.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Verification answer file not found!");
        }
    }

    //code for first page
    public static void firstPage() {
        System.out.println("What section would you like to choose? 1 (Journal Writing), 2 (Journal Saves), 3 (Settings), 4 (Exit Journal)");
        Scanner firstPage = new Scanner(System.in);
        int input;
        input = firstPage.nextInt();
        if (input == 1) {
            writeJournal();
        }
        if (input == 2) {
            viewSaves();
        }
        if (input == 3) {
            settings();
        }
        if (input == 4) {
            System.exit(1);
        }
    }

    //code for settings page
    public static void settings() {
        System.out.println("Welcome to settings, what would you like to change? 1 (Code), 2 (Verification Question/Answer), 3 (Verification Status), 4 (Return to Previous Page)");
        Scanner settings = new Scanner(System.in);
        int input;
        input = settings.nextInt();
        if (input == 1) {
            changeCode();
        }
        if (input == 2) {
            changeVerification();
        }
        if (input == 3) {
            verificationStatus();
        }
        if (input == 4) {
            firstPage();
        }
    }

    //code for writing a journal entry
    public static void writeJournal() {
        System.out.println("Welcome to your journal! What would you like to enter for today?");
        Scanner string = new Scanner(System.in);
        String content;
        content = string.nextLine();
        System.out.println("Is that all you would like to enter today? 1 (yes), 2 (no)");
        Scanner confirmation = new Scanner(System.in);
        int confirmationInput;
        confirmationInput = confirmation.nextInt();
        while (confirmationInput != 1) {
            System.out.println("What else would you like to add?");
            Scanner addition = new Scanner(System.in);
            String additionalInput;
            additionalInput = addition.nextLine();
            String additionalContent = content + additionalInput;
            System.out.println("Is this all you would like to enter today? 1 (yes), 2 (no)");
            System.out.println(additionalContent);
            confirmationInput = confirmation.nextInt();
            content = additionalContent;
        }
        System.out.println("Content Saving to date " + date + "...");
        try {
            var write = new PrintWriter(input);
            write.println(content);
            write.close();
        } catch (IOException e) {
        }
        firstPage();
    }

    //code for viewing all previous journal entry saves
    public static void viewSaves() {
        File saves = new File("./Saves");
        for (File file : saves.listFiles()) {
            System.out.println(file.getName());
        }
        Scanner input = new Scanner(System.in);
        var fileName = input.nextLine();
        File potentialFileName = new File("./Saves/" + fileName);
        try {
            Scanner scn = new Scanner(potentialFileName);
            while (scn.hasNextLine()) {
                System.out.println(scn.nextLine());
            }
            input.nextLine();
            firstPage();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            firstPage();
        }
    }

    //changes entry code
    public static void changeCode() {
        if (verificationStatus) {
            System.out.println(verificationQuestion);
            Scanner verifyAnswer = new Scanner(System.in);
            String input;
            input = verifyAnswer.nextLine();
            if (!input.equalsIgnoreCase(verificationAnswer)) {
                incorrectLoop2();
            }
            createCode();
            saveCode();
            settings();
        }
        createCode();
        saveCode();
        settings();
    }

    //first part to changing verification question/answer
    public static void changeVerification() {
        if (verificationStatus) {
            System.out.println(verificationQuestion);
            Scanner verifyAnswer = new Scanner(System.in);
            String input;
            input = verifyAnswer.nextLine();
            if (input.equalsIgnoreCase(verificationAnswer)) {
                createVerify();
            } else {
                incorrectLoop2();
                createVerify();
            }
            settings();
        }
        createVerify();
        settings();
    }

    //changes verification status
    public static void verificationStatus() {
        if (verificationQuestion != null) {
            System.out.println("Would you like to turn verification questions off or on?");
            Scanner verificationStatusScn = new Scanner(System.in);
            String input;
            input = verificationStatusScn.nextLine();
            if (input.equalsIgnoreCase("on")) {
                verificationStatus = true;
                saveVerificationStatus();
                System.out.println("Verification questions/answers are now turned on!");
                settings();
            }
            if (input.equalsIgnoreCase("off")) {
                verificationStatus = false;
                saveVerificationStatus();
                System.out.println("Verification questions/answers are now turned off!");
                settings();
            }
            System.out.println("Verification questions/answers are now turned on!");
        } else {
            System.out.println("You have yet to create a verification question/answer! Please do this before turning on verification.");
            settings();
        }
    }

    //saves new entry code
    public static void saveCode() {
        try {
            var write = new PrintWriter(saveCode);
            write.println(code);
            write.close();
        } catch (IOException e) {
        }
    }

    //saves new verification status
    public static void saveVerificationStatus() {
        try {
            var write = new PrintWriter(saveVerifyStatus);
            write.println(verificationStatus);
            write.close();
        } catch (IOException e) {
        }
    }

    //second part to changing verification question/answer
    public static void createVerify() {
        System.out.println("What would you like to change your verification question to?");
        Scanner newVerifyQuestion = new Scanner(System.in);
        verificationQuestion = newVerifyQuestion.nextLine();
        File saveVerifyQuestion = new File("./Variables/verificationQuestion.txt");
        try {
            var write = new PrintWriter(saveVerifyQuestion);
            write.println(verificationQuestion);
            write.close();
        } catch (IOException e) {
        }
        System.out.println("What would you like to change your verification Answer to?");
        Scanner newVerifyAnswer = new Scanner(System.in);
        verificationAnswer = newVerifyAnswer.nextLine();
        File saveVerifyAnswer = new File("./Variables/verificationAnswer.txt");
        try {
            var write = new PrintWriter(saveVerifyAnswer);
            write.println(verificationAnswer);
            write.close();
        } catch (IOException e) {
        }
        System.out.println("New verification question/answer created!");
    }

    //creates new entry code
    public static void createCode() {
        System.out.println("What would you like to change your code to?");
        Scanner newCode = new Scanner(System.in);
        code = newCode.nextInt();
        System.out.println("New code created!");
    }

    //loop for incorrect entries
    public static void incorrectLoop2() {
        System.out.println("INCORRECT! Try Again.");
        System.out.println(verificationQuestion);
        Scanner again = new Scanner(System.in);
        String inputAgain;
        inputAgain = again.nextLine();
        while (!inputAgain.equalsIgnoreCase(verificationAnswer)) {
            System.out.println("INCORRECT! Try Again.");
            System.out.println(verificationQuestion);
            inputAgain = again.nextLine();
        }
    }
}
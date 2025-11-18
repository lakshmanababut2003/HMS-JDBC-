package util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import models.Gender;

public class Validator {

    private static final int MAX_ATTEMPT = 3;
    private static final String exit = " (or if you want exists press 0)";

    public static int getValidChoice(Scanner sc) {

        int choice;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            try {
                System.out.println("Enter the Choice : ");
                choice = sc.nextInt();
                sc.nextLine();

                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Only Accepts the Numeric Values...");
                sc.nextLine();
            }

            attempt++;

        }

        isMaxAttempt(attempt);

        return -1;

    }

    public static String getValidEmail(Scanner sc) {

        String email = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("Enter the Email " + exit + " : ");
            email = sc.nextLine();

            if (wantBack(email)) {
                email = null;
                break;
            }

            if (Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
                break;
            }

            System.out.println("Enter the Valid Email...");

            attempt++;
        }

        return isMaxAttempt(attempt) ? null : email;
    }

    public static String getValidPassword(Scanner sc) {

        String password = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("Enter the Password " + exit + " :)");
            password = sc.nextLine();

            if (wantBack(password)) {
                password = null;
                break;
            }

            if (!password.trim().isEmpty() && password.length() >= 5) {
                break;
            }

            System.out.println("The Password doesnot empty or Minimum Length is above 5...");
            attempt++;
        }

        return isMaxAttempt(attempt) ? null : password;

    }

    public static String getNonEmptyString(Scanner sc, String fieldName) {

        String input = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("Enter The " + fieldName + exit + " : ");
            input = sc.nextLine();

            if (wantBack(input)) {
                input = null;
                break;
            }

            if (!input.trim().isEmpty()) {
                break;
            }

            System.out.println("The " + fieldName + " doesnot empty...");
            attempt++;

        }

        return isMaxAttempt(attempt) ? null : input;
    }

    public static int getValidInt(Scanner sc, String filedName) {

        int id;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            try {
                System.out.println("Enter the " + filedName + exit + ": ");
                id = sc.nextInt();
                sc.nextLine();

                if (id == 0) {
                    break;
                }

                return id;
            } catch (InputMismatchException e) {
                System.out.println("Only Accepts the Numeric Values...");
                sc.nextLine();
            }

            attempt++;

        }

        isMaxAttempt(attempt);

        return -1;

    }

    public static String getValidPhone(Scanner sc, String filedName) {

        String phone = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("Enter the " + filedName + exit + ": ");
            phone = sc.nextLine();

            if (wantBack(phone)) {
                phone = null;
                break;
            }

            if (Pattern.matches("^[0-9]{10}$", phone)) {
                break;
            }

            System.out.println("Invalid Phone Number Format...(Only allowed 10digits)");
            attempt++;
        }

        return isMaxAttempt(attempt) ? null : phone;
    }

    public static Gender getValidGender(Scanner sc) {

        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("1.Male");
            System.out.println("2.Female");
            System.out.println("3.Others");
            System.out.println("4.Exit");

            int choice = getValidChoice(sc);

            switch (choice) {
                case 1: {
                    return Gender.MALE;
                }
                case 2: {
                    return Gender.FEMALE;
                }
                case 3: {
                    return Gender.OTHERS;
                }
                case 4: {
                    return null;
                }
                default: {
                    LogUtil.print("Wrong Choice...");
                    break;
                }
            }

        }

        return null;
    }

    public static LocalDate getValidDateFormat(Scanner sc) {

        String date = null;
        int attempt = 0;
        LocalDate validDate = null;

        while (attempt < MAX_ATTEMPT) {

            System.out.println("Enter the Date (yyyy-mm-dd)" + exit + " : ");
            date = sc.nextLine();

            if(wantBack(date)){
                return null;
            }

            try {
                validDate = LocalDate.parse(date);
                LocalDate today = LocalDate.now();
                if (validDate.isBefore(today)) {
                    System.out.println(" Date cannot be in the past. Please choose today or a future date.");
                } else {
                    break;
                }

            } catch (DateTimeException e) {
                System.out.println("Please Entry Date Format like (yyyy-mm-dd)");
            }
        }

        return isMaxAttempt(attempt) ? null : validDate;
    }

    public static String getValidTime(Scanner sc) {

        final int MAX_ATTEMPT = 3;
        String time = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            System.out.print("Enter time (8 AM - 8 PM) " + exit + " : ");
            String input = sc.nextLine().trim().toUpperCase();

            if(wantBack(input)){
                return null;
            }

            try {
                String hourPart = input.replaceAll("[^0-9]", "");
                String periodPart = input.replaceAll("[^A-Z]", "");

                if (hourPart.isEmpty() || periodPart.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                int hour = Integer.parseInt(hourPart);

                if (!periodPart.equals("AM") && !periodPart.equals("PM")) {
                    throw new IllegalArgumentException();
                }

                int hour24 = (periodPart.equals("PM") && hour != 12) ? hour + 12 : hour;
                if (periodPart.equals("AM") && hour == 12)
                    hour24 = 0;

                if (hour24 >= 8 && hour24 <= 20) {
                    time = String.format("%02d %s", hour, periodPart);
                    break;
                } else {
                    System.out.println("Invalid time. Please enter between 8 AM and 8 PM only.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Example: 8 AM or 07PM");
            }

            attempt++;
        }

        return isMaxAttempt(attempt) ? null : time;
    }


    
   public static LocalTime convertToLocalTime(String time) {
    try {
        
        String[] parts = time.split(" ");
        if (parts.length != 2) {
            LogUtil.print("Invalid time format. Expected format: HH AM/PM");
            return null;
        }
        
        int hour = Integer.parseInt(parts[0]);
        String period = parts[1].toUpperCase();
        
       
        if (period.equals("PM") && hour != 12) {
            hour += 12;
        } else if (period.equals("AM") && hour == 12) {
            hour = 0;
        }
        
        return LocalTime.of(hour, 0); 
        
    } catch (Exception e) {
        LogUtil.print("Invalid time format. Expected format: HH AM/PM");
        return null;
    }
}


    public static double getValidDouble(Scanner sc, String filedName) {

        double id;
        int attempt = 0;

        while (attempt < MAX_ATTEMPT) {

            try {
                System.out.println("Enter the " + filedName + exit + ": (greater than 0)");
                id = sc.nextDouble();
                sc.nextLine();

                if (id == 0) {
                    break;
                }

                return id;
            } catch (InputMismatchException e) {
                System.out.println("Only Accepts the Numeric Values...");
                sc.nextLine();
            }

            attempt++;

        }

        isMaxAttempt(attempt);

        return -1;

    }




    private static boolean isMaxAttempt(int attempt) {

        if (attempt == MAX_ATTEMPT) {
            System.out.println("You Try Multiple Attempts...");
            return true;
        }

        return false;
    }

    private static boolean wantBack(String back) {

        return back.equals("0") ? true : false;

    }

}

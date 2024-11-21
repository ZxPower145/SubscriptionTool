package org.tn.subscriptiontool.core.security.services;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;

public class VerificationService {

    // Twilio Credentials from Environment Variables
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String TWILIO_SERVICE_SID = "VA298091b37b76d78693722f973fb7839d";  // Twilio Service SID

    // SendGrid API Key from Environment Variables
    public static final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");

    // User contact details for verification
    public static final String USER_PHONE_NUMBER = "+40756825516";  // Provided phone number
    public static final String USER_EMAIL = "catalindnb@gmail.com";  // Provided email address

    // Sender email (should be a verified email or domain in SendGrid)
    public static final String SENDER_EMAIL = "csemeniu93@gmail.com";  // Replace with SendGrid verified sender email

    public static void main(String[] args) throws IOException {
        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for verification method
        System.out.println("Choose verification method: (1) SMS (2) Email");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        if (choice == 1) {
            // Send SMS verification
            sendSMSVerification(USER_PHONE_NUMBER);

            // Loop until valid SMS code is entered
            boolean isVerified = false;
            while (!isVerified) {
                System.out.print("Enter the SMS verification code: ");
                String smsCode = scanner.nextLine();

                if (checkSMSVerification(USER_PHONE_NUMBER, smsCode)) {
                    System.out.println("SMS verification approved!");
                    isVerified = true;
                } else {
                    System.out.println("Invalid SMS code. Please try again.");
                }
            }
        } else if (choice == 2) {
            // Generate a random code for email verification
            String emailCode = generateRandomCode();

            // Send email verification with the generated code
            sendEmailVerification(USER_EMAIL, "Email Verification Code", "Your email verification code is: " + emailCode);

            // Loop until valid Email code is entered
            boolean isVerified = false;
            while (!isVerified) {
                System.out.print("Enter the email verification code: ");
                String enteredCode = scanner.nextLine();

                if (enteredCode.equals(emailCode)) {
                    System.out.println("Email verification approved!");
                    isVerified = true;
                } else {
                    System.out.println("Invalid email code. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid choice. Please restart the application and choose 1 or 2.");
        }

        scanner.close();
    }

    // Method to send SMS verification using Twilio
    public static void sendSMSVerification(String phoneNumber) {
        try {
            // Initiates the SMS verification
            Verification verification = Verification.creator(
                            TWILIO_SERVICE_SID,
                            phoneNumber,
                            "sms")
                    .create();

            System.out.println("SMS verification sent to: " + phoneNumber);
            System.out.println("Verification SID: " + verification.getSid());
            System.out.println("Current Status: " + verification.getStatus());  // Expected to be "pending" initially
        } catch (Exception e) {
            System.err.println("Error sending SMS verification: " + e.getMessage());
        }
    }

    // Method to check the SMS verification code
    public static boolean checkSMSVerification(String phoneNumber, String code) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            TWILIO_SERVICE_SID,
                            code)
                    .setTo(phoneNumber)
                    .create();

            // Check if the returned status is "approved"
            if ("approved".equals(verificationCheck.getStatus())) {
                return true;
            } else {
                System.out.println("Verification status: " + verificationCheck.getStatus());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error checking SMS verification: " + e.getMessage());
            return false;
        }
    }

    // Method to send email verification using SendGrid
    public static void sendEmailVerification(String toEmail, String subject, String body) throws IOException {
        Email from = new Email(SENDER_EMAIL);  // Sender email address (must be verified in SendGrid)
        Email to = new Email(toEmail);  // Recipient email address
        Content content = new Content("text/plain", body);  // Email content

        // Create the email object
        Mail mail = new Mail(from, subject, to, content);

        // Initialize SendGrid with the API key
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        try {
            // Set up the request to SendGrid API
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // Send the email through SendGrid
            Response response = sg.api(request);

            // Log the response status, body, and headers for debugging
            System.out.println("Email sent to: " + toEmail);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());

            // Check if the response indicates success
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                System.out.println("Email verification sent successfully.");
            } else {
                System.out.println("Failed to send email. Status Code: " + response.getStatusCode());
            }
        } catch (IOException ex) {
            System.err.println("Error sending email verification: " + ex.getMessage());
            throw ex;
        }
    }

    // Method to generate a random 6-digit code for verification
    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);  // Generates a 6-digit number
        return String.valueOf(code);
    }
}

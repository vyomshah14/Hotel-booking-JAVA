/*
===============================
File: BookingFileManager.java

What is used:
- Java File I/O (File, FileWriter, FileReader, Scanner)
- Exception Handling (try-catch, try-with-resources)
- Permanent File Persistence for bookings.txt

Purpose:
- Ye file booking ki details permanently store karti hai.
- Java File I/O ka use karke file handling implement ki gayi hai (without BufferedReader/BufferedWriter).

Hinglish Explanation:
- FileWriter: File me text write karne ke liye use hota hai.
- append mode: FileWriter(FILE_NAME, true) - isse purani bookings delete nahi hoti, naya record file ke end me append hota hai.
- Scanner / FileReader: File se text line-by-line read karne ke liye utility.
- file existence: File exist karti hai ya nahi check karke, agar na ho toh createNewFile() se automatic create ki jati hai.
- writing booking details: Nayi booking record ko formatted text format me write karna.
- reading booking details: bookings.txt se saare records read karke GUI me return karna.
- removing a cancelled booking: Cancelled booking hatane ke baad active bookings ke saath file ko rewrite karna.
===============================
*/

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Helper class for handling all persistent file operations on bookings.txt
public class BookingFileManager {

    // Dynamic file path resolver: App root ya subfolder se run karne par bhi sahi bookings.txt locate karta hai.
    public static File getBookingFile() {
        File f1 = new File("Mini-Project/HotelBookingSystem/bookings.txt");
        if (f1.exists()) return f1;

        File f2 = new File("HotelBookingSystem/bookings.txt");
        if (f2.exists()) return f2;

        File f3 = new File("bookings.txt");
        if (f3.exists()) return f3;

        return f3;
    }

    // File existence check aur automatic creation method
    public static void ensureFileExists() throws IOException {
        File file = getBookingFile();
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // Save/Append a single booking details to bookings.txt
    public static void appendBooking(Booking booking) throws IOException {
        ensureFileExists();

        try (FileWriter writer = new FileWriter(getBookingFile(), true)) {
            writer.write("Booking ID: " + booking.getBookingId() + "\n");
            writer.write("Customer Name: " + booking.getCustomer().getName() + "\n");
            writer.write("Room Number: " + booking.getRoom().getRoomNumber() + "\n");
            writer.write("Check In: " + booking.getCheckIn() + "\n");
            writer.write("Check Out: " + booking.getCheckOut() + "\n");
            writer.write("----------------------------------------\n\n");
        }
    }

    // Read all raw content from bookings.txt for "View Bookings"
    public static String readAllBookings() throws IOException {
        File file = getBookingFile();

        if (!file.exists() || file.length() == 0) {
            return "No bookings found.";
        }

        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }

        String result = content.toString().trim();
        if (result.isEmpty()) {
            return "No bookings found.";
        }

        return result;
    }

    // Rewrite the bookings.txt file with current list of active bookings (used during cancellation)
    public static void saveAllBookings(List<Booking> bookings) throws IOException {
        try (FileWriter writer = new FileWriter(getBookingFile(), false)) {
            for (Booking booking : bookings) {
                writer.write("Booking ID: " + booking.getBookingId() + "\n");
                writer.write("Customer Name: " + booking.getCustomer().getName() + "\n");
                writer.write("Room Number: " + booking.getRoom().getRoomNumber() + "\n");
                writer.write("Check In: " + booking.getCheckIn() + "\n");
                writer.write("Check Out: " + booking.getCheckOut() + "\n");
                writer.write("----------------------------------------\n\n");
            }
        }
    }

    // Application startup par bookings.txt se existing bookings load aur sync karne ke liye method
    public static void loadBookingsFromFile(Hotel hotel) {
        try {
            ensureFileExists();
        } catch (IOException e) {
            System.err.println("Error creating bookings file: " + e.getMessage());
        }

        File file = getBookingFile();
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            int bookingId = 0;
            String customerName = null;
            int roomNumber = 0;
            String checkIn = null;
            String checkOut = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("Booking ID:")) {
                    bookingId = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else if (line.startsWith("Customer Name:")) {
                    customerName = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Room Number:")) {
                    roomNumber = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else if (line.startsWith("Check In:")) {
                    checkIn = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Check Out:")) {
                    checkOut = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("----------------------------------------")) {
                    if (bookingId > 0 && customerName != null && roomNumber > 0 && checkIn != null && checkOut != null) {
                        hotel.loadExistingBooking(bookingId, customerName, roomNumber, checkIn, checkOut);
                    }
                    bookingId = 0;
                    customerName = null;
                    roomNumber = 0;
                    checkIn = null;
                    checkOut = null;
                }
            }

            if (bookingId > 0 && customerName != null && roomNumber > 0 && checkIn != null && checkOut != null) {
                hotel.loadExistingBooking(bookingId, customerName, roomNumber, checkIn, checkOut);
            }
        } catch (Exception e) {
            System.err.println("Error loading bookings from file: " + e.getMessage());
        }
    }
}

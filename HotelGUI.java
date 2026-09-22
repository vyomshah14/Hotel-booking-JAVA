
import java.awt.*;
import javax.swing.*;

public class HotelGUI extends JFrame {

    private Hotel hotel;

    public HotelGUI(Hotel hotel) {

        this.hotel = hotel;

        setTitle("Hotel Room Booking System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(
                new BorderLayout(20, 20)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // Title
        JLabel titleLabel = new JLabel(
                "HOTEL ROOM BOOKING SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        // Button panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
                new GridLayout(3, 2, 15, 15)
        );

        JButton viewRoomsButton
                = new JButton("View Rooms");

        JButton searchRoomButton
                = new JButton("Search Room");

        JButton bookRoomButton
                = new JButton("Book Room");

        JButton cancelBookingButton
                = new JButton("Cancel Booking");

        JButton viewBookingsButton
                = new JButton("View Bookings");

        JButton exitButton
                = new JButton("Exit");

        buttonPanel.add(viewRoomsButton);
        buttonPanel.add(searchRoomButton);
        buttonPanel.add(bookRoomButton);
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(viewBookingsButton);
        buttonPanel.add(exitButton);

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // VIEW ROOMS
        // =========================
        viewRoomsButton.addActionListener(e -> {

            String rooms = hotel.getAllRooms();

            JOptionPane.showMessageDialog(
                    this,
                    rooms,
                    "All Hotel Rooms",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        // =========================
        // SEARCH ROOM
        // =========================

        searchRoomButton.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter Room Number:"
            );

            if (input == null) {
                return;
            }

            try {

                int roomNumber = Integer.parseInt(input);

                // Search using HashMap
                Room room = hotel.searchRoom(roomNumber);

                if (room != null) {

                    JOptionPane.showMessageDialog(
                            this,
                            room.toString(),
                            "Room Found",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Room " + roomNumber + " does not exist.",
                            "Room Not Found",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid room number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        // =========================
        // BOOK ROOM
        // =========================

        bookRoomButton.addActionListener(e -> {

            // Customer name
            String name = JOptionPane.showInputDialog(
                    this,
                    "Enter Customer Name:"
            );

            if (name == null || name.trim().isEmpty()) {
                return;
            }

            // Phone
            String phone = JOptionPane.showInputDialog(
                    this,
                    "Enter Phone Number:"
            );

            if (phone == null || phone.trim().isEmpty()) {
                return;
            }

            // Email
            String email = JOptionPane.showInputDialog(
                    this,
                    "Enter Email:"
            );

            if (email == null || email.trim().isEmpty()) {
                return;
            }

            // Room number
            String roomInput = JOptionPane.showInputDialog(
                    this,
                    "Enter Room Number:"
            );

            if (roomInput == null) {
                return;
            }

            // Check-in
            String checkIn = JOptionPane.showInputDialog(
                    this,
                    "Enter Check-in Date (DD-MM-YYYY):"
            );

            if (checkIn == null || checkIn.trim().isEmpty()) {
                return;
            }

            // Check-out
            String checkOut = JOptionPane.showInputDialog(
                    this,
                    "Enter Check-out Date (DD-MM-YYYY):"
            );

            if (checkOut == null || checkOut.trim().isEmpty()) {
                return;
            }

            // Number of nights
            String nightsInput = JOptionPane.showInputDialog(
                    this,
                    "Enter Number of Nights:"
            );

            if (nightsInput == null) {
                return;
            }

            try {

                int roomNumber = Integer.parseInt(roomInput);

                int numberOfNights
                        = Integer.parseInt(nightsInput);

                // Validate nights
                if (numberOfNights <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Number of nights must be greater than 0.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                // Check room
                Room room = hotel.searchRoom(roomNumber);

                if (room == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Room " + roomNumber + " does not exist.",
                            "Room Not Found",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                // Check availability
                if (!room.isAvailable()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Room " + roomNumber + " is already booked.",
                            "Room Unavailable",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                // Create customer
                int customerId = hotel.getNextCustomerId();

                Customer customer = new Customer(
                        customerId,
                        name,
                        phone,
                        email
                );

                int bookingId = hotel.getNextBookingId();

                // Book room
                boolean success = hotel.bookRoom(
                        bookingId,
                        customer,
                        roomNumber,
                        checkIn,
                        checkOut,
                        numberOfNights
                );

                if (success) {

                    double totalAmount
                            = room.getPricePerNight() * numberOfNights;

                    JOptionPane.showMessageDialog(
                            this,
                            "Room booked successfully!\n\n"
                            
                            + "Booking ID: " + bookingId + "\n"
                            + "Customer ID: " + customerId + "\n"
                            + "Customer: " + name + "\n"
                            + "Room: " + roomNumber + "\n"
                            + "Check-in: " + checkIn + "\n"
                            + "Check-out: " + checkOut + "\n"
                            + "Nights: " + numberOfNights + "\n"
                            + "Total Bill: ₹" + totalAmount,
                            "Booking Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room number and nights must be numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        // =========================
        // CANCEL BOOKING
        // =========================

        cancelBookingButton.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter Booking ID:"
            );

            if (input == null) {
                return;
            }

            try {

                int bookingId = Integer.parseInt(input);

                boolean cancelled
                        = hotel.cancelBooking(bookingId);

                if (cancelled) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Booking " + bookingId
                            + " cancelled successfully.\n\n"
                            + "The room is now available again.",
                            "Booking Cancelled",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Booking ID " + bookingId
                            + " was not found.",
                            "Booking Not Found",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid numeric Booking ID.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        // =========================
        // VIEW BOOKINGS
        // =========================

        viewBookingsButton.addActionListener(e -> {

            String bookings = hotel.getAllBookings();

            JOptionPane.showMessageDialog(
                    this,
                    bookings,
                    "All Bookings",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // =========================
        // EXIT
        // =========================
        exitButton.addActionListener(e -> {

            System.exit(0);
        });
    }
}

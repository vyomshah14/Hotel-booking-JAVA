
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Hotel {

    private int nextCustomerId = 1;
    private int nextBookingId = 1001;
    private Room[] rooms;
    private LinkedList<Booking> bookings;
    private HashMap<Integer, Room> roomMap;
    private TreeMap<Integer, Room> sortedRooms;

    public Hotel(Room[] rooms) {

        this.rooms = rooms;

        bookings = new LinkedList<>();
        roomMap = new HashMap<>();
        sortedRooms = new TreeMap<>();

        for (Room room : rooms) {
            roomMap.put(room.getRoomNumber(), room);
            sortedRooms.put(room.getRoomNumber(), room);
        }
    }

    // Display all rooms in console
    public void displayRooms() {

        System.out.println("\n--- All Hotel Rooms ---");

        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // Return all rooms as String for GUI
    public String getAllRooms() {

        StringBuilder result = new StringBuilder();

        for (Room room : rooms) {
            result.append(room).append("\n");
        }

        return result.toString();
    }

    // Search room using HashMap
    public Room searchRoom(int roomNumber) {

        return roomMap.get(roomNumber);
    }

    // Display sorted rooms using TreeMap
    public void displaySortedRooms() {

        System.out.println("\n--- Rooms in Sorted Order ---");

        for (Room room : sortedRooms.values()) {
            System.out.println(room);
        }
    }

    // Old method - useful for testing
    public void addBooking(Booking booking) {

        bookings.add(booking);

        booking.getRoom().setAvailable(false);
    }

    // Book a room
    public boolean bookRoom(
            int bookingId,
            Customer customer,
            int roomNumber,
            String checkIn,
            String checkOut,
            int numberOfNights) {

        Room room = roomMap.get(roomNumber);

        if (room == null) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return false;
        }

        if (!room.isAvailable()) {
            System.out.println("Room " + roomNumber + " is already booked.");
            return false;
        }

        Booking booking = new Booking(
                bookingId,
                customer,
                room,
                checkIn,
                checkOut,
                numberOfNights
        );

        bookings.add(booking);

        room.setAvailable(false);

        System.out.println("Room " + roomNumber + " booked successfully!");
        System.out.println(booking);

        return true;
    }

    // Display bookings
    public void displayBookings() {

        System.out.println("\n--- All Bookings ---");

        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    // Return bookings as String for GUI
    public String getAllBookings() {

        if (bookings.isEmpty()) {
            return "No bookings available.";
        }

        StringBuilder result = new StringBuilder();

        for (Booking booking : bookings) {
            result.append(booking).append("\n\n");
        }

        return result.toString();
    }

    // Cancel booking
    public boolean cancelBooking(int bookingId) {

        for (int i = 0; i < bookings.size(); i++) {

            Booking booking = bookings.get(i);

            if (booking.getBookingId() == bookingId) {

                booking.getRoom().setAvailable(true);

                bookings.remove(i);

                System.out.println(
                        "Booking " + bookingId
                        + " cancelled successfully."
                );

                return true;
            }
        }

        System.out.println(
                "Booking " + bookingId + " not found."
        );

        return false;
    }

    public int getNextCustomerId() {

        return nextCustomerId++;
    }

    public int getNextBookingId() {

        return nextBookingId++;
    }
}

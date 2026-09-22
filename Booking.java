public class Booking {

    private int bookingId;
    private Customer customer;
    private Room room;
    private String checkIn;
    private String checkOut;
    private int numberOfNights;
    private double totalAmount;

    public Booking(int bookingId, Customer customer, Room room,
                   String checkIn, String checkOut, int numberOfNights) {

        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfNights = numberOfNights;

        // Automatically calculate total bill
        this.totalAmount = room.getPricePerNight() * numberOfNights;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {

        return "Booking ID: " + bookingId +
                " | Customer: " + customer.getName() +
                " | Room: " + room.getRoomNumber() +
                " | Check-in: " + checkIn +
                " | Check-out: " + checkOut +
                " | Nights: " + numberOfNights +
                " | Total: ₹" + totalAmount;
    }
}
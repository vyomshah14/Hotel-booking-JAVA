import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // Create rooms
        Room room1 = new Room(
                101,
                "Single",
                1500
        );

        Room room2 = new Room(
                102,
                "Double",
                2500
        );

        Room room3 = new Room(
                103,
                "Deluxe",
                3500
        );


        // Store rooms in array
        Room[] rooms = {
                room1,
                room2,
                room3
        };


        // Create hotel
        Hotel hotel = new Hotel(rooms);


        // Start Swing GUI
        SwingUtilities.invokeLater(() -> {

            HotelGUI gui = new HotelGUI(hotel);

            gui.setVisible(true);
        });
    }
}
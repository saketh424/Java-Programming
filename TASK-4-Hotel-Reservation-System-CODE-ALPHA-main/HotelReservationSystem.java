import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;
    private double pricePerNight;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true; // Initially all rooms are available
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Category: " + category + ", Price per night: $" + pricePerNight;
    }
}

class Reservation {
    private int reservationId;
    private String guestName;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalAmount;

    public Reservation(int reservationId, String guestName, Room room, Date checkInDate, Date checkOutDate) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = calculateTotalAmount();
    }

    private double calculateTotalAmount() {
        long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        long diff = diffInMillies / (1000 * 60 * 60 * 24);
        return diff * room.getPricePerNight();
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest Name: " + guestName + ", Room: " + room.getRoomNumber() +
                ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate + ", Total Amount: $" + totalAmount;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        nextReservationId = 1;
        initializeRooms();
    }

    private void initializeRooms() {
        // Sample room data
        rooms.add(new Room(101, "Standard", 100.0));
        rooms.add(new Room(102, "Standard", 100.0));
        rooms.add(new Room(201, "Deluxe", 150.0));
        rooms.add(new Room(202, "Deluxe", 150.0));
        rooms.add(new Room(301, "Suite", 200.0));
        rooms.add(new Room(302, "Suite", 200.0));
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Reservation makeReservation(String guestName, String category, Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = searchAvailableRooms(category);
        if (!availableRooms.isEmpty()) {
            Room room = availableRooms.get(0);
            room.setAvailable(false);
            Reservation reservation = new Reservation(nextReservationId++, guestName, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            return reservation;
        } else {
            return null; // No available room found
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System Menu:");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View All Reservations");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Standard, Deluxe, Suite): ");
                    String category = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(category);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms in the " + category + " category.");
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room category (Standard, Deluxe, Suite): ");
                    category = scanner.nextLine();
                    System.out.print("Enter check-in date (yyyy-mm-dd): ");
                    String checkInDateString = scanner.nextLine();
                    System.out.print("Enter check-out date (yyyy-mm-dd): ");
                    String checkOutDateString = scanner.nextLine();

                    try {
                        Date checkInDate = new Date(checkInDateString);
                        Date checkOutDate = new Date(checkOutDateString);

                        Reservation reservation = hotel.makeReservation(guestName, category, checkInDate, checkOutDate);
                        if (reservation != null) {
                            System.out.println("Reservation successful!");
                            System.out.println(reservation);
                        } else {
                            System.out.println("No available rooms in the " + category + " category for the specified dates.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format.");
                    }
                    break;
                case 3:
                    List<Reservation> reservations = hotel.getReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        System.out.println("All reservations:");
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}

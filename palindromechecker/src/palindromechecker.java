import java.util.*;

class ParkingSpot {

    String licensePlate;
    long entryTime;
    boolean occupied;

    ParkingSpot() {
        this.occupied = false;
    }
}

class ParkingLot {

    private int SIZE = 500;
    private ParkingSpot[] table = new ParkingSpot[SIZE];

    private int totalProbes = 0;
    private int totalParks = 0;

    public ParkingLot() {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new ParkingSpot();
        }
    }

    // Hash function
    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    // Park vehicle
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index].occupied) {

            index = (index + 1) % SIZE;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].occupied = true;

        totalProbes += probes;
        totalParks++;

        System.out.println("parkVehicle(\"" + plate + "\") → Assigned spot #" +
                index + " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        int index = hash(plate);

        int probes = 0;

        while (table[index].occupied) {

            if (table[index].licensePlate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - table[index].entryTime) / 1000;

                double hours = duration / 3600.0;

                double fee = hours * 5; // $5 per hour

                table[index].occupied = false;

                System.out.println("exitVehicle(\"" + plate + "\") → Spot #" +
                        index + " freed, Duration: " +
                        hours + "h, Fee: $" + fee);

                return;
            }

            index = (index + 1) % SIZE;
            probes++;

            if (probes > SIZE)
                break;
        }

        System.out.println("Vehicle not found");
    }

    // Find nearest available spot
    public void nearestSpot() {

        for (int i = 0; i < SIZE; i++) {

            if (!table[i].occupied) {
                System.out.println("Nearest available spot: #" + i);
                return;
            }
        }

        System.out.println("Parking lot full");
    }

    // Statistics
    public void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : table) {
            if (spot.occupied)
                occupied++;
        }

        double occupancy = (occupied * 100.0) / SIZE;

        double avgProbes = totalParks == 0 ? 0 : (double) totalProbes / totalParks;

        System.out.println("\nParking Statistics:");
        System.out.println("Occupancy: " + occupancy + "%");
        System.out.println("Avg Probes: " + avgProbes);
    }
}

public class ParkingLotApp {

    public static void main(String[] args) throws Exception {

        ParkingLot lot = new ParkingLot();

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        Thread.sleep(2000);

        lot.exitVehicle("ABC-1234");

        lot.nearestSpot();

        lot.getStatistics();
    }
}
package QubeCinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MovieDistribution {
    // Map to store regions for quick lookup by their code (city-province-country)
    private static Map<String, Region> regionMap = new HashMap<>();
    private static Map<String, Distributor> distributorMap = new HashMap<>();

    public static void main(String[] args) {
        // Load region data from the CSV file
        loadCSVData();

        // Set up default distributors (optional)
        Distributor distributor1 = new Distributor("DISTRIBUTOR1");
        distributor1.addIncludeRegion(new Region(null, null, "IN"));  // Include India
        distributor1.addIncludeRegion(new Region(null, null, "US"));  // Include United States
        distributor1.addExcludeRegion(new Region(null, "KA", "IN"));  // Exclude Karnataka, India
        distributor1.addExcludeRegion(new Region("CHENNAI", "TN", "IN"));  // Exclude Chennai, Tamil Nadu, India
        distributorMap.put("DISTRIBUTOR1", distributor1);

        // Scanner for dynamic input
        Scanner scanner = new Scanner(System.in);

        // Show distributors at the beginning
        showDistributors();

        while (true) {
            System.out.println("\nEnter a command (1: Query Specific Distributor, 2: Assign Permissions, 3: Add Distributor, 4: Show Distributors, 5: List Distributors, 6: Exit):");
            int command = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            if (command == 1) {
                querySpecificDistributor(scanner);
            } else if (command == 2) {
                assignPermissions(scanner);
            } else if (command == 3) {
                addDistributor(scanner);
            } else if (command == 4) {
                showDistributors();
            } else if (command == 5) {
                listDistributors();
            } else if (command == 6) {
                break;
            } else {
                System.out.println("Invalid command.");
            }
        }

        scanner.close();
    }

    // Function to query for a specific distributor
    private static void querySpecificDistributor(Scanner scanner) {
        System.out.println("Enter the distributor's name:");
        String distributorName = scanner.nextLine().trim().toUpperCase();

        Distributor distributor = distributorMap.get(distributorName);
        if (distributor == null) {
            System.out.println("Distributor not found.");
            return;
        }

        System.out.println("Enter the region code to check (format: CITY-PROVINCE-COUNTRY):");
        String regionCode = scanner.nextLine().trim().toUpperCase();
        Region queryRegion = regionMap.get(regionCode);
        if (queryRegion == null) {
            System.out.println("Invalid region code!");
            return;
        }

        boolean canDistribute = distributor.canDistribute(queryRegion);
        System.out.println("Can " + distributorName + " distribute in " + queryRegion + "? " + (canDistribute ? "YES" : "NO"));
    }

    // Function to add a new distributor dynamically
    private static void addDistributor(Scanner scanner) {
        System.out.println("Enter the new distributor's name:");
        String distributorName = scanner.nextLine().trim().toUpperCase();

        if (distributorMap.containsKey(distributorName)) {
            System.out.println("Distributor already exists!");
            return;
        }

        Distributor newDistributor = new Distributor(distributorName);

        System.out.println("Add regions to INCLUDE for " + distributorName + " (Enter regions in format CITY-PROVINCE-COUNTRY, type DONE when finished):");
        while (true) {
            String regionCode = scanner.nextLine().trim().toUpperCase();
            if (regionCode.equals("DONE")) break;

            Region region = regionMap.get(regionCode);
            if (region == null) {
                System.out.println("Invalid region code! Try again.");
            } else {
                newDistributor.addIncludeRegion(region);
            }
        }

        System.out.println("Add regions to EXCLUDE for " + distributorName + "Enter regions in format CITY-PROVINCE-COUNTRY, type DONE when finished):");
        while (true) {
            String regionCode = scanner.nextLine().trim().toUpperCase();
            if (regionCode.equals("DONE")) break;

            Region region = regionMap.get(regionCode);
            if (region == null) {
                System.out.println("Invalid region code! Try again.");
            } else {
                newDistributor.addExcludeRegion(region);
            }
        }

        distributorMap.put(distributorName, newDistributor);
        System.out.println("Distributor " + distributorName + " added successfully.");
    }

    // Function to assign permissions from one distributor to another
    private static void assignPermissions(Scanner scanner) {
        System.out.println("Enter the assigning distributor's name:");
        String assigningDistributorName = scanner.nextLine().trim().toUpperCase();
        Distributor assigningDistributor = distributorMap.get(assigningDistributorName);

        if (assigningDistributor == null) {
            System.out.println("Distributor not found!");
            return;
        }

        System.out.println("Enter the receiving distributor's name:");
        String receivingDistributorName = scanner.nextLine().trim().toUpperCase();
        Distributor receivingDistributor = distributorMap.get(receivingDistributorName);

        if (receivingDistributor == null) {
            System.out.println("Distributor not found!");
            return;
        }

        System.out.println("Enter the region to include (format: CITY-PROVINCE-COUNTRY):");
        String includeRegionCode = scanner.nextLine().trim().toUpperCase();
        Region includeRegion = regionMap.get(includeRegionCode);

        if (includeRegion == null) {
            System.out.println("Invalid include region!");
            return;
        }

        System.out.println("Enter the region to exclude (optional, format: CITY-PROVINCE-COUNTRY, or press ENTER to skip):");
        String excludeRegionCode = scanner.nextLine().trim().toUpperCase();
        Region excludeRegion = excludeRegionCode.isEmpty() ? null : regionMap.get(excludeRegionCode);

        assigningDistributor.assignPermissions(receivingDistributor, includeRegion, excludeRegion);
    }

    // Function to display all distributors and their regions
    private static void showDistributors() {
        for (Distributor distributor : distributorMap.values()) {
            distributor.printDistributorDetails();
        }
    }

    // Function to display the list of distributor names only
    private static void listDistributors() {
        System.out.println("List of Distributors:");
        for (String distributorName : distributorMap.keySet()) {
            System.out.println(" - " + distributorName);
        }
    }

    // Load CSV data and map regions
    private static void loadCSVData() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:/cities.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String cityCode = values[0].toUpperCase();
                String provinceCode = values[1].toUpperCase();
                String countryCode = values[2].toUpperCase();
                Region region = new Region(cityCode, provinceCode, countryCode);
                regionMap.put(region.toString(), region);
            }
            System.out.println("Region data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}

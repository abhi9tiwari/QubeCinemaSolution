package QubeCinema;

import java.util.ArrayList;
import java.util.List;

// Class representing a distributor with included and excluded regions
class Distributor {
    String name;
    List<Region> includedRegions;
    List<Region> excludedRegions;

    public Distributor(String name) {
        this.name = name;
        this.includedRegions = new ArrayList<>();
        this.excludedRegions = new ArrayList<>();
    }

    // For adding regions we wnt to include in list.
    public void addIncludeRegion(Region region) {
        this.includedRegions.add(region);
    }

    // For adding regions we want to exclude in list.
    public void addExcludeRegion(Region region) {
        this.excludedRegions.add(region);
    }

    // Check if the distributor can distribute in the given region
    public boolean canDistribute(Region queryRegion) {
        // First checking if the region is excludded
        for (Region excluded : excludedRegions) {
            if (queryRegion.isSubsetOf(excluded)) return false;
        }
        // Then check if the region is included
        for (Region included : includedRegions) {
            if (queryRegion.isSubsetOf(included)) return true;
        }
        return false;
    }

    // Assign part of this distributors permissions to another distributor
    public void assignPermissions(Distributor otherDistributor, Region includeRegion, Region excludeRegion) {
        // Check that the assigned regions are present in the parent disttributor.
        boolean isValidInclude = false;
        for (Region included : includedRegions) {
            if (includeRegion.isSubsetOf(included)) {
                isValidInclude = true;
                break;
            }
        }
        if (!isValidInclude) {
            System.out.println("ERROR: Invalid inclusion. Cannot assign a region that is not part of " + this.name + "'s permissions.");
            return;
        }

        //same for excluding regions
        boolean isValidExclude = false;
        if (excludeRegion != null) {
            for (Region excluded : excludedRegions) {
                if (excludeRegion.isSubsetOf(excluded)) {
                    isValidExclude = true;
                    break;
                }
            }
            if (!isValidExclude) {
                System.out.println("ERROR: Invalid exclusion. Cannot assign an excluded region that is not part of " + this.name + "'s permissions.");
                return;
            }
        }

        // If valid, assign the regions to the child distributor
        otherDistributor.addIncludeRegion(includeRegion);
        if (excludeRegion != null) {
            otherDistributor.addExcludeRegion(excludeRegion);
        }
        System.out.println("Successfully assigned permissions from " + this.name + " to " + otherDistributor.name);
    }

    // Print the distributor's regions for display purposes
    public void printDistributorDetails() {
        System.out.println("Distributor: " + name);
        System.out.println("Included Regions:");
        for (Region included : includedRegions) {
            System.out.println("  - " + included);
        }
        System.out.println("Excluded Regions:");
        for (Region excluded : excludedRegions) {
            System.out.println("  - " + excluded);
        }
        System.out.println();
    }
}
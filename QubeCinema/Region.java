package QubeCinema;

// Class representing a region (city, state, country)
class Region {
    String cityCode;
    String provinceCode;
    String countryCode;

    // Checks if the current region is a subset of another region
    // When parent assigns child disttributor a region we will use to check if region comes under parent country.
    public boolean isSubsetOf(Region other) {
        if (!this.countryCode.equals(other.countryCode)) return false;
        if (other.provinceCode != null && !this.provinceCode.equals(other.provinceCode)) return false;
        return other.cityCode == null || this.cityCode.equals(other.cityCode);
    }

    @Override
    public String toString() {
        return cityCode + "-" + provinceCode + "-" + countryCode;
    }

    public Region(String cityCode, String provinceCode, String countryCode) {
        this.cityCode = cityCode;
        this.provinceCode = provinceCode;
        this.countryCode = countryCode;
    }
}

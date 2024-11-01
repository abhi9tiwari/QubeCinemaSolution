// repository/csv_loader.go
package repository

import (
	"encoding/csv"
	"errors"
	"os"
	"strings"
)

var ValidLocations = make(map[string]bool)

func LoadCSV(filePath string) error {
	file, err := os.Open(filePath)
	if err != nil {
		return errors.New("unable to open CSV file")
	}
	defer file.Close()

	reader := csv.NewReader(file)
	records, err := reader.ReadAll()
	if err != nil {
		return errors.New("failed to read CSV data")
	}

	for _, record := range records {
		location := strings.Join(record, "-")
		ValidLocations[location] = true
	}
	return nil
}

func ValidateLocation(location string) bool {
	return ValidLocations[location]
}

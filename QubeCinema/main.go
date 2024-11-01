// main.go
package main

import (
	"QubeCinema/repository"
	"QubeCinema/utils"
	"fmt"
)

func main() {
	if err := repository.LoadCSV("path/to/your/cities.csv"); err != nil {
		fmt.Println("Error loading locations:", err)
		return
	}

	utils.InitDistributors()
	utils.DisplayMenu()
}

// main.go
package main

import (
	"QUBECINEMA/repository"
	"QUBECINEMA/utils"
	"fmt"
)

func main() {
	//loading csv file
	if err := repository.LoadCSV("D:/cities.csv"); err != nil {
		fmt.Println("Error loading locations:", err)
		return
	}

	utils.InitDistributors()
	utils.DisplayMenu()
}

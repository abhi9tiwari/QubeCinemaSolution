package utils

import (
	"QUBECINEMA/models"
	"QUBECINEMA/services"
	"fmt"
	"strings"
)

func InitDistributors() {
	// adding pre loaded distributors
	distributor1 := &models.Distributor{Name: "DISTRIBUTOR1"}
	distributor2 := &models.Distributor{Name: "DISTRIBUTOR2", Parent: distributor1}

	distributor1.Includes = []string{"INDIA", "UNITEDSTATES"}
	distributor1.Excludes = []string{"KARNATAKA-INDIA", "CHENNAI-TAMILNADU-INDIA"}
	distributor2.Includes = []string{"INDIA"}
	distributor2.Excludes = []string{"TAMILNADU-INDIA"}

	services.Distributors["DISTRIBUTOR1"] = distributor1
	services.Distributors["DISTRIBUTOR2"] = distributor2
}

func DisplayMenu() {
	var choice int
	for {
		//cli
		fmt.Println("\n1: Query Distributor")
		fmt.Println("2: Assign Permissions")
		fmt.Println("3: List Distributors")
		fmt.Println("4: Exit")
		fmt.Print("Choose an option: ")
		fmt.Scan(&choice)

		switch choice {
		case 1:
			var name, location string
			fmt.Print("Enter distributor name: ")
			fmt.Scan(&name)
			fmt.Print("Enter location: ")
			fmt.Scan(&location)
			services.QueryDistributor(name, location)
		case 2:
			var name, includes, excludes string
			fmt.Print("Enter distributor name: ")
			fmt.Scan(&name)
			fmt.Print("Enter includes (comma-separated): ")
			fmt.Scan(&includes)
			fmt.Print("Enter excludes (comma-separated): ")
			fmt.Scan(&excludes)

			if dist, ok := services.Distributors[name]; ok {
				services.AssignPermissions(dist, strings.Split(includes, ","), strings.Split(excludes, ","))
			} else {
				fmt.Println("Distributor not found.")
			}
		case 3:
			for name := range services.Distributors {
				fmt.Println(name)
			}
		case 4:
			fmt.Println("Exiting.")
			return
		default:
			fmt.Println("Invalid choice.")
		}
	}
}

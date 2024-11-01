// services/permissions.go
package services

import (
	"QubeCinema/models"
	"fmt"
)

var Distributors = make(map[string]*models.Distributor)

func AssignPermissions(distributor *models.Distributor, includes []string, excludes []string) {
	for _, loc := range includes {
		distributor.AddInclude(loc)
	}
	for _, loc := range excludes {
		distributor.AddExclude(loc)
	}
	fmt.Printf("Permissions updated for %s\n", distributor.Name)
}

func QueryDistributor(name, location string) {
	dist, exists := Distributors[name]
	if !exists {
		fmt.Println("Distributor not found.")
		return
	}
	if dist.HasPermission(location) {
		fmt.Println("YES")
	} else {
		fmt.Println("NO")
	}
}

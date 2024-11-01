// services/permissions.go
package services

import (
	"QUBECINEMA/models"
	"fmt"
)

var Distributors = make(map[string]*models.Distributor)

// to assign permision from parent to child distributor
func AssignPermissions(distributor *models.Distributor, includes []string, excludes []string) {
	for _, loc := range includes {
		distributor.AddInclude(loc)
	}
	for _, loc := range excludes {
		distributor.AddExclude(loc)
	}
	fmt.Printf("Permissions updated for %s\n", distributor.Name)
}

// query for distributor
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

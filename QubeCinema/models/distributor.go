// models/distributor.go
package models

type Distributor struct {
	Name     string
	Parent   *Distributor
	Includes []string
	Excludes []string
	Children []*Distributor
}

func (d *Distributor) AddInclude(location string) {
	d.Includes = append(d.Includes, location)
}

func (d *Distributor) AddExclude(location string) {
	d.Excludes = append(d.Excludes, location)
}

func (d *Distributor) HasPermission(location string) bool {
	for _, excl := range d.Excludes {
		if location == excl {
			return false
		}
	}
	for _, incl := range d.Includes {
		if location == incl {
			return true
		}
	}
	if d.Parent != nil {
		return d.Parent.HasPermission(location)
	}
	return false
}

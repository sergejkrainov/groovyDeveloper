package sql.datamodel

class Employees {

    String first_name
    String last_name
    String division
    String salary

    public void setFirstName(String first_name) {
        this.first_name = first_name
    }
    String getFirstName() {
        return this.first_name
    }

    public void setLastName(String last_name) {
        this.last_name = last_name
    }
    String getLastName() {
        return this.last_name
    }

    public void setDivision(String division) {
        this.division = division
    }
    String getDivision() {
        return this.division
    }

    public void setSalary(String salary) {
        this.salary = salary
    }
    String getSalary() {
        return this.salary
    }
}

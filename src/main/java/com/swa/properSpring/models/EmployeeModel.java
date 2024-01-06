package com.swa.properSpring.models;

import com.swa.properSpring.user.Employee;

import java.util.Objects;

public class EmployeeModel {

    private String username;
    private String password;
    private String email;
    private String phone1;
    private String phone2;

    public EmployeeModel(String username, String password, String email, String phone1, String phone2, String firstName, String lastName, String company) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
    }

    public static EmployeeModel fromEmployee(Employee employee) {
        return new EmployeeModel(
                employee.getUsername(),
                null,
                employee.getEmail(),
                employee.getPhone1(),
                employee.getPhone2(),
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getCustomer().getCustomerName());
    }
    public Employee toEmployee(){
        return new Employee.Builder(username,password).addEmail(email).addName(firstName,lastName).addPhone(phone1).addPhone(phone2).build();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeModel that = (EmployeeModel) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhone1(), that.getPhone1()) && Objects.equals(getPhone2(), that.getPhone2()) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getCompany(), that.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail(), getPhone1(), getPhone2(), getFirstName(), getLastName(), getCompany());
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    private String company;

}

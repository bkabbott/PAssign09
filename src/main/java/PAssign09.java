import java.io.*;

/**
 * File: PAssign09.java
 * Class: CSCI 1302
 * Author: Brian Abbott
 * Created on: 4/29/26
 * Last modified: 4/29/26
 * Description: Reads employee data from a binary file, processes salary raises
 *              based on provided table (current salary and years of service). Writes the
 *              updated Employee objects to a new binary file.
 */

public class PAssign09 {

    public static void main(String[] args) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("src/employeeData.dat"));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/employeeDataProcessed.dat"))) {

            while (true) {
                try {
                    String name = dis.readUTF();
                    double salary = dis.readDouble();
                    double yearsOfService = dis.readDouble();

                    Employee employee = new Employee(name, salary, yearsOfService);

                    double raisePercentage = getRaisePercentage(salary, yearsOfService);
                    employee.processRaise(raisePercentage);

                    oos.writeObject(employee);
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    public static double getRaisePercentage(double salary, double yearsOfService) {
        if (salary <= 30000.00) {
            return yearsOfService <= 2 ? 3.00 : 2.50;
        } else if (salary <= 60000.00) {
            return yearsOfService <= 5 ? 2.25 : 2.00;
        } else if (salary <= 80000.00) {
            return yearsOfService <= 5 ? 1.75 : 1.50;
        } else {
            return yearsOfService <= 5 ? 1.25 : 1.00;
        }
    }
}

class Employee implements Serializable {
    private String name;
    private double salary;
    private double yearsOfService;

    public Employee(String name, double salary, double yearsOfService) {
        this.name = name;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
    }

    public void processRaise(double raisePercentage) {
        salary += salary * (raisePercentage / 100.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(double yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    @Override
    public String toString() {
        return String.format("Name: %s%nSalary: $%,.2f%nYears of Service: %.1f%n", getName(), getSalary(), getYearsOfService());
    }
}

package com.fdmgroup.ElevatorSystem;

import java.util.Scanner;

import com.fdmgroup.ElevatorSystem.Controller.ElevatorController;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args)
    {

        String totalFloor;
        String numElevators;
        String elevatorLimit;
    	
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Welcome to Smart Building, please initailise building first");
        System.out.print("Number of floors are : ");
        totalFloor = scanner.nextLine();
        System.out.print("Number of elevators are : ");
        numElevators = scanner.nextLine();
        System.out.print("Max number of elevator are : ");
        elevatorLimit = scanner.nextLine();
        
        ElevatorController elevatorController = 
        		new ElevatorController(Integer.parseInt(totalFloor), Integer.parseInt(numElevators), Integer.parseInt(elevatorLimit));
        

    }
}

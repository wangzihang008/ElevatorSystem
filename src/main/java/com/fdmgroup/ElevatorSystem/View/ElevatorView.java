package com.fdmgroup.ElevatorSystem.View;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fdmgroup.ElevatorSystem.Controller.ElevatorController;
import com.fdmgroup.ElevatorSystem.Model.Person;

public class ElevatorView {
	
	private ElevatorController elevatorController;
	
	public ElevatorView(ElevatorController elevatorController) {
		this.elevatorController = elevatorController;
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20));
		panel.setSize(100, 150);
		
		JLabel labelCurrentFloor = new JLabel("Current Floor");
		JLabel labelDestinationFloor = new JLabel("Destination Floor");
		JTextField textCurrentFloor = new JTextField();
		JTextField textDestinationFloor = new JTextField();
		JButton buttonSubmit = new JButton("Create Passenger");
		
		panel.add(labelCurrentFloor);
		panel.add(textCurrentFloor);
		panel.add(labelDestinationFloor);
		panel.add(textDestinationFloor);
		panel.add(buttonSubmit);
		
		buttonSubmit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public Person createNewPassenger(String currentFloor, String destinationFloor) {
		return new Person(Integer.parseInt(currentFloor), Integer.parseInt(destinationFloor));
	}
}

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
import javax.swing.WindowConstants;

import com.fdmgroup.ElevatorSystem.Controller.ElevatorController;
import com.fdmgroup.ElevatorSystem.Model.Person;

public class ElevatorView {
	
	private ElevatorController elevatorController;
	private JButton buttonSubmit;
	private JTextField textCurrentFloor;
	private JTextField textDestinationFloor;
	
	public ElevatorView(ElevatorController elevatorController) {
		this.elevatorController = elevatorController;
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20));
		panel.setSize(100, 150);
		
		JLabel labelCurrentFloor = new JLabel("Current Floor");
		JLabel labelDestinationFloor = new JLabel("Destination Floor");
		textCurrentFloor = new JTextField();
		textDestinationFloor = new JTextField();
		buttonSubmit = new JButton("Create Passenger");
		
		panel.add(labelCurrentFloor);
		panel.add(textCurrentFloor);
		panel.add(labelDestinationFloor);
		panel.add(textDestinationFloor);
		panel.add(buttonSubmit);
		
		setActionListener();
		
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(100, 150));
        frame.pack();
		frame.setVisible(true);
	}
	
	private void setActionListener() {
		buttonSubmit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
//				elevatorController.addNewPassengerIntoWaitingQueue(createNewPassenger(textCurrentFloor.getText(), textDestinationFloor.getText()));
			}
		});
	}
	
	public Person createNewPassenger(String currentFloor, String destinationFloor) {
		return new Person(Integer.parseInt(currentFloor), Integer.parseInt(destinationFloor));
	}
}

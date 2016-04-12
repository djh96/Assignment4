import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;	
import java.util.Scanner;
import java.io.*;  

public class Voter
{
	//non-static variables that make up a Voter object
	private int voterID;
	private String name;
	private boolean voted;

	//Constructor that accepts 3 paramaters, the id, name, and true or false
	public Voter(int id, String voterName, String b)
	{
		voterID = id;
		name = voterName;
		if(b.equals("true")) 
		{
			voted = true;
		}
		else
		{
			voted = false;
		}
	}
	
	//Method that will check if the voter id is valid and return true if they are
	public boolean checkIfVoter(int id)
	{
		if(id == voterID) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}	

	//Method that will check if the voter has voted before and return true if they have not
	public boolean checkIfVoted(int id)
	{
		if(voted == false) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Will set the status of the voter to true
	public void setVoted()
	{
		voted = true;
	}

	//Method will return if the voter has voted already
	public String getVoted()
	{
		if(voted) 
		{
			return "true";	
		}
		else
		{
			return "false";
		}
	}
	//Method will return the name of the voter
	public String getName()
	{
		return name;
	}
	//Method will return the ID of the voter
	public int getID()
	{
		return voterID;
	}
}


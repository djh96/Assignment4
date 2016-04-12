import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;	
import java.util.Scanner;
import java.io.*;  

public class Ballot extends JPanel
{
	//Global non-static variables that will be associated with each ballot object
	private int ballot_id;
	private String category;
	private String[] nominees;
	private int[] numVotes;
	public ArrayList<JButton> buttonArray = new ArrayList<JButton>();

	//Constructor that takes in the number of nominees to create the numVotes and numNominees arrays
	public Ballot(int numNominees)
	{
		nominees = new String[numNominees];
		numVotes = new int[numNominees];
	}

	//Mutator that sets the ballot id
	public void setBallotID(int id)
	{
		ballot_id = id;
	}

	//Accessor that gets the ballot id
	public int getBallotID()
	{
		return ballot_id;
	}

	//Mutator that sets the category
	public void setCategory(String cat)
	{
		category = cat;
	}

	//Accessor that gets the category
	public String getCategory()
	{
		return category;
	}

	//Mutator that sets the nominee at the inputted element
	public void setNominees(String nominee, int element)
	{
		nominees[element] = nominee;
	}

	//Accessor that gets the size of the nominees array, or the number of nominees
	public int getNomineesSize()
	{
		return nominees.length;
	}

	//Accessor that gets the nominee at a certain element
	public String getNominee(int element)
	{
		return nominees[element];
	}

	//Mutator that sets the number of votes into the numVotes array
	public void setNumVotes() throws IOException
	{
		File myFile = new File(getBallotID()+".txt");
		Scanner inputFile = new Scanner(myFile);
		for(int i = 0; i < nominees.length; i++) 
		{
			Scanner inputFile2 = new Scanner(inputFile.nextLine()).useDelimiter(":");
			String place = inputFile2.next();
			numVotes[i] = Integer.parseInt(inputFile2.next());
		}
		inputFile.close();
	}

	//Mutator that adds one to the number of votes at a specific element
	public void addVote(int element)
	{
		numVotes[element] += 1;
	}

	//Accessor that gets the number of votes at a specific element
	public int getNumVotes(int element) throws IOException
	{
		return numVotes[element];
	}

	//Mutator that adds a button to the button array
	public void setButtonArray(JButton btn)
	{
		buttonArray.add(btn);
	}

	//Accessor that gets the button array
	public ArrayList<JButton> getButtonArray()
	{
		return buttonArray;
	}

	//Method that will create the file for each ballot/category
	public void createBallotFile() throws IOException
	{
		PrintWriter writer = new PrintWriter(getBallotID() + ".txt");
		for(int i = 0; i < nominees.length; i++) 
		{
			writer.print(nominees[i] + ":" + numVotes[i]);
			writer.println();
		}
		writer.close();
	}
}
//Actionlister class associated with the buttons for the nominees
class ButtonListener implements ActionListener 
{
    public void actionPerformed(ActionEvent e) 
    {
    	//Get the source of the button
    	//Set the text of the button to red
       	JButton btn = (JButton) e.getSource();
       	btn.setForeground(Color.RED);

       	//For every button of every ballot, if the button is already red within
       	// that ballot, make it black and change the button pressed to red.
        for(int i = 0; i < Assignment4.ballotArray.size(); i++)
        {
        	Ballot ballot = Assignment4.ballotArray.get(i);
	        for(int j = 0; j < ballot.getNomineesSize(); j++)
	        {
	        	if(ballot.getButtonArray().get(j).getForeground().equals(Color.RED)) 
	        	{
	        		if(ballot.getButtonArray().get(j) != btn && ballot.getButtonArray().contains(btn)) 
	        		{
	        			ballot.getButtonArray().get(j).setForeground(Color.BLACK);
	        		}
	        	}
	        }
        }
    }	
}
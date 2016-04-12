import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;	
import java.util.Scanner;
import java.io.*;  

public class Ballot extends JPanel
{
	private int ballot_id;
	private String category;
	private String[] nominees;
	private int[] numVotes;
	public ArrayList<JButton> buttonArray = new ArrayList<JButton>();

	public Ballot(int numNominees)
	{
		nominees = new String[numNominees];
		numVotes = new int[numNominees];
	}

	public void setBallotID(int id)
	{
		ballot_id = id;
	}

	public int getBallotID()
	{
		return ballot_id;
	}

	public void setCategory(String cat)
	{
		category = cat;
	}

	public String getCategory()
	{
		return category;
	}
	public void setNominees(String nominee, int element)
	{
		nominees[element] = nominee;
	}
	public int getNomineesSize()
	{
		return nominees.length;
	}

	public String getNominee(int element)
	{
		return nominees[element];
	}
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
	public void addVote(int element)
	{
		numVotes[element] += 1;
	}
	public int getNumVotes(int element) throws IOException
	{
		return numVotes[element];
	}
	public void setButtonArray(JButton btn)
	{
		buttonArray.add(btn);
	}
	public ArrayList<JButton> getButtonArray()
	{
		return buttonArray;
	}
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
class ButtonListener implements ActionListener 
{
    public void actionPerformed(ActionEvent e) 
    {
       	JButton btn = (JButton) e.getSource();
       	btn.setForeground(Color.RED);
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
/*Daniel Hodgson
CS 0401
This code will represent voting.
It will have features such as login, buttons, panels, voting, and allowing another user to vote
*/

//import special packages
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;	
import java.util.Scanner;
import java.io.*;  

public class Assig4
{
	//Global variables for the window width and height
	private static final int HEIGHT = 200;
    private static final int WIDTH = 1000;
    
    //Global variables that include the cast button, login button, and ArrayLists of Ballots and Voters
    //Also includes a window variable, a textfield variable, and a Voter variable to hold any voter
    private static JButton cast = new JButton("Cast Your Vote");
    private static JButton login = new JButton("Login to Vote");    
	public static ArrayList<Ballot> ballotArray = new ArrayList<Ballot>();
	public static ArrayList<Voter> voterArray = new ArrayList<Voter>();
    private static JTextField _msg = new JTextField(20);
    private static Voter voter2;
    private static JFrame _window = new JFrame("Window");

    //Mutator method to set the current voter to the reference variable
    //@param int element- set the element of the voterArray to the voter variable
    public static void setVoterNum(int element)
    {
    	voter2 = voterArray.get(element);
    }

    //Accessor method to get the message of the textfield
    //returns a JTextField
    public static Voter getVoter2()
    {
    	return voter2;
    }

    //Accessor method to get the login JButton
    //returns a JButton
    public static JButton getLogin()
    {
    	return login;
    }

    //addBallots method will accept a file name and go through the specific format
    // of the file to create the correct number of ballot objects with the correct
    // ballot fields
    public static void addBallots(String file) throws IOException
    {	
    	//new file object named of the file sent to the method
    	//new scanner object to scan the file
    	File myFile = new File(file);
    	Scanner myFile2 = new Scanner(myFile);
    	
    	//Set variables to hold the number of ballots and number of nominees
    	//Number of ballots (numQ) = the first line of the file
    	int numQ = myFile2.nextInt();
    	int numNominees = 0;

    	//Create a new variable to scan the file and move it forward a line to skip 
    	// past the first line
		Scanner inputFile = new Scanner(myFile);
		inputFile.nextLine();

		//for the number of ballots
		//A new scanner object equals only the next line of the first scanner object
		// using the delimiter of a comma. 
		//For the number of times there is a next line, add 1 to numNominees and create
		// a Ballot object constructed with the number of nominees and add to the ballot
		// array
		//Close the second scanner

		for(int i = 0; i < numQ; i++)
		{
			Scanner inputFile2 = new Scanner(inputFile.nextLine()).useDelimiter(",");
			while(inputFile2.hasNext())
			{
				numNominees++;
				inputFile2.next();
			}
			Ballot ballot = new Ballot(numNominees);
			ballotArray.add(ballot);
			numNominees = 0;
			inputFile2.close();
		}
		//Close the scanner
		//Open a scanner with the same file, but start it at the beginning of the file
		//Move the cursor past the first line
		inputFile.close();
		inputFile = new Scanner(myFile);
		inputFile.nextLine();

		//For every ballot
		//Open a third scanner that will scan only an entire line from the ballot file
		// using the delimiters of ":" and ","
		//Get the corresponding ballot from the ballot array
		//Set the first element of the scanner to the ballot ID
		//Set the second element to the category
		//Create a counter to hold what number nominee the loop is on
		//Create a button variable to temporarily hold a button and add the button to the buttonArray
		//Set the nominee to the corresponding button in the buttonArray
		//Create a new text file for the ballot
		//Close the third scanner
    	for(int i = 0; i < numQ; i++) 
    	{   
    		Scanner inputFile3 = new Scanner(inputFile.nextLine()).useDelimiter(":|,");
    		Ballot ballot = ballotArray.get(i);
			ballot.setBallotID(Integer.parseInt(inputFile3.next()));
			ballot.setCategory(inputFile3.next());
			int counter = 0;
			while(inputFile3.hasNext())
	    	{
	    		JButton btn = new JButton(inputFile3.next());
		   		ballot.setButtonArray(btn);
		   		ballot.setNominees(btn.getText(), counter);
		   		ballot.setNumVotes();
		   		ballot.createBallotFile();
		   		counter++;
	    	}
	    	inputFile3.close();
	    }
	    //For every ballot in the ballot array
	    //Set the ballot variable to the corresponding ballot
	    //Set the ballot to a BoxLayout, set the category, set the width, height, allignment of the ballot
	    //For every button in the ballot, add the button to the panel, with space bewteen
	    // each, disabled, and centered
	    //Add the ballot to the window
	    	for(int j = 0; j < ballotArray.size(); j++) 
	    	{
    			Ballot ballot = ballotArray.get(j);
	    		ballot.setLayout(new BoxLayout(ballot, BoxLayout.Y_AXIS));

	    		JLabel text = new JLabel(ballot.getCategory());
				ballot.setSize((WIDTH/(numQ+2)), HEIGHT);
				ballot.add(text);
				text.setAlignmentX(Component.CENTER_ALIGNMENT);
	    		for(int k = 0; k < ballot.getButtonArray().size(); k++) 
	    		{
		    		JButton btn = ballot.getButtonArray().get(k);
	    			ActionListener buttonListener = new ButtonListener();
		   			btn.addActionListener(buttonListener);
			   		ballot.add(btn);
			    	Dimension size = new Dimension(WIDTH/(numQ+2), (HEIGHT-(ballot.getNomineesSize()*40))/ballot.getNomineesSize());
		    		ballot.add(Box.createRigidArea(size));
			   		btn.setAlignmentX(Component.CENTER_ALIGNMENT);	
			   		btn.setEnabled(false);
	    		}
			   	_window.add(ballot);  
	    	}
	    //Close the scanner
	    inputFile.close();	
    }

    //Method addLogin will add the login button to the window
    public static void addLogin()
    {
    	//Set the actionlistener for the button to buttonlistener2
    	//Add the actionlistener to the login button
    	ActionListener buttonListener2 = new ButtonListener2();
    	login.addActionListener(buttonListener2);
    	
    	//Create a panel to put the button on
    	//Set the panel's layout, size, and allignment
    	//Add the panel to the window
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	Dimension size = new Dimension(WIDTH, (HEIGHT-75)/2);
    	panel.add(Box.createRigidArea(size));
    	panel.add(login);
    	panel.add(Box.createRigidArea(size));
    	_window.add(panel);
    }

    //Method addCastVote will add the cast vote button to the window
    public static void addCastVote()
    {
    	//Set the actionlistener for the button to buttonlistener3
    	//Add the actionlistener to the cast button
    	ActionListener buttonListener3 = new ButtonListener3();
    	cast.addActionListener(buttonListener3);
 
    	//Create a panel to put the button on
    	//Set the panel's layout, size, and allignment
    	//Disable the button
    	//Add the panel to the window   
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	Dimension size = new Dimension(WIDTH, (HEIGHT-75)/2);
    	panel.add(Box.createRigidArea(size));
    	panel.add(cast);
    	panel.add(Box.createRigidArea(size));
    	cast.setEnabled(false);
    	_window.add(panel);    	
    }

    //Method setCastTrue will enable the cast button
    public static void setCastTrue()
    {
    	//Enable the cast button
    	cast.setEnabled(true);
    }

    //Method uploadVotes will create new voter objects and add them to the voter array
    public static void uploadVoters(String file) throws IOException
    {
    	//New File object referencing the voter file
    	//Create a Scanner object referencing the file
    	File myFile = new File(file);
    	Scanner inputFile = new Scanner(myFile);
    	
    	//Variables to temporarily hold the voter id, name, and status
    	int id;
    	String name;
    	String voted;
    	
    	//While the file has another section
    	//Create a new scanner that is only one line of the file with the ":" delimiter
    	//Set the id, name, and status to the next part the scanner gets, and create
    	// a new voter object with these variables
    	//Add the voter to the voterArray
    	while(inputFile.hasNext())
    	{
   			Scanner inputFile2 = new Scanner(inputFile.nextLine()).useDelimiter(":");

    		id = Integer.parseInt(inputFile2.next());
    		name = inputFile2.next();
    		voted = inputFile2.next();
    		Voter voter = new Voter(id, name, voted);
    		voterArray.add(voter);
    	}
    	//Close the input file
    	inputFile.close();
    }

    //Method addVote will rewrite the specific ballot file to add a vote to the nominee
    // that was voted for.  It will use a safe save style by saving to a temp file
    // and renaming the temp file if it successfully writes. 
    public static void addVote(Ballot ballot, int num) throws IOException
    {
    	//Add 1 to the nominee that was voted for
    	ballot.addVote(num);

    	//Create a PrintWriter object that will write a new temp file
    	PrintWriter writer = new PrintWriter(ballot.getBallotID() + "tempballot.txt");
    	
    	//For each nominee, rewrite the name and the number of times its been voter for
    	for(int i = 0; i < ballot.getNomineesSize(); i++) 
    	{
    		writer.print(ballot.getNominee(i) + ":" + ballot.getNumVotes(i));
    		writer.println();
    	}
    	//Close the writer
    	writer.close();

    	//Create 2 File objects, a temp file and the real file
    	File ballotFile = new File(ballot.getBallotID()+".txt");
    	File tempballot = new File(ballot.getBallotID() + "tempballot.txt");

    	//Delete what ever is in the real file and rename the temp file to the real file
		ballotFile.delete();
    	tempballot.renameTo(ballotFile);
    }

    //Method updateVoterFile will rewrite the voter file to update if a person has voter
    //It will use a safe save style by saving to a temp file and renaming the temp 
    // file if it successfully writes.
    public static void updateVoterFile() throws IOException
    {
    	//Write all of the current voter info to a temp file
    	PrintWriter writer = new PrintWriter("tempvoter.txt");
    	for(int i = 0; i < voterArray.size(); i++) 
    	{
    		Voter voter = voterArray.get(i);
	    	writer.print(voter.getID() + ":" + voter.getName() + ":" + voter.getVoted());
	    	writer.println();   		
    	}
    	//Close the writer
    	writer.close();
    	
    	//Create 2 File objects, a temp file and the real file
    	File temp = new File("tempvoter.txt");
    	File voterFile = new File("voters.txt");

    	//Delete what ever is in the real file and rename the temp file to the real file
		voterFile.delete(); 
		temp.renameTo(voterFile);
    }

	public static void main(String[] args) throws IOException
	{
		_window.setSize(WIDTH, HEIGHT);

		// Close program when window is closed
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the layout of the window
		_window.setLayout(new GridLayout());

		//Call the necessary methods
		addBallots(args[0]);
		uploadVoters("voters.txt");
		addLogin();
		addCastVote();
		_window.setVisible(true);
	}
}
//ActionListener class corresponding to the login button
class ButtonListener2 implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		//Create a frame that will ask for the user's ID when the button is pressed
		JFrame frame = new JFrame("Voter Login");
    	String message = "Enter Your Voter ID";
    	String text = JOptionPane.showInputDialog(frame, message);
	    
    	//For every voterin the voter array
    	//Get the corresponding voter
    	//If the ok button is pressed and text was entered and the voter exists and the voter has not voted
    	//	then give the welcome message, and enable all of the appropriate buttons
    	//	as well as disable the login button
    	//Else
    	//If the reason the login failed was a non existant voter ID, tell the user
   	    for(int k = 0; k < Assignment4.voterArray.size(); k++)
	    {
	    	Voter voter = Assignment4.voterArray.get(k);
	    	if(text != null && voter.checkIfVoter(Integer.parseInt(text)) && voter.checkIfVoted(Integer.parseInt(text))) 
	    	{
	    		Assignment4.setVoterNum(k);
	    		JOptionPane.showMessageDialog(frame, "Welcome, " + voter.getName() + ", please make your selections.");
		    	for(int i = 0; i < Assignment4.ballotArray.size(); i++)
		        {
		        	Ballot ballot = Assignment4.ballotArray.get(i);
		        	for(int j = 0; j < ballot.getNomineesSize(); j++) 
		        	{
		        		ballot.getButtonArray().get(j).setEnabled(true);
		        	}
		        }
		        JButton btn = (JButton) e.getSource();
		        btn.setEnabled(false);
		        Assignment4.setCastTrue();
		    }
		    else
		    {
		    	if(voter.getID() == Integer.parseInt(text)) 
		    	{
	    			JOptionPane.showMessageDialog(frame, voter.getName() + ", you have already voted!");
		    	}
		    }
		}

		//Set a counter that will need to match the number of voters
		//For every voter, if any id matches the text, the counter will not increment
		// and thus will be different than the size of the array
		int counter = 0;
	   	for(int i = 0; i < Assignment4.voterArray.size(); i++) 
	   	{
	   		Voter voter2 = Assignment4.voterArray.get(i);
	   		if(voter2.getID() != Integer.parseInt(text)) 
    		{
    			counter++;
    		}
     	}

     	//If the counter is equal to the size of the voter array, then the ID didn't match
     	// any ID and is not a valid ID
    	if(counter == Assignment4.voterArray.size())
    	{
	    	JOptionPane.showMessageDialog(frame, "That is not a valid ID, please try again!");
    	}
	}
}
//ActionListener class corresponding to the cast button
class ButtonListener3 implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		//Ask the user if they are sure they want to cast their vote
		int result = JOptionPane.showConfirmDialog((Component) null, "Confirm Vote?","Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
		
		//If the yes button is pressed, for every button in every ballot, if the button
		// is red, set the button to black and send the button element to the addVote method
		// so the method updates that it was voted for
		//Disable all of the buttons and enable the login button
		//Update the voter file
		if(result == 0)
		{
			for(int i = 0; i < Assignment4.ballotArray.size(); i++)
			{
				Ballot ballot = Assignment4.ballotArray.get(i);
				for(int j = 0; j < ballot.getNomineesSize(); j++) 
				{
					JButton button = ballot.getButtonArray().get(j);
					if(button.getForeground().equals(Color.RED)) 
					{
						button.setForeground(Color.BLACK);
						try
						{
							Assignment4.addVote(ballot, j);
						} 
						catch(IOException er)
						{
							System.err.println(er);
						}
					}
				}
			}
			for(int i = 0; i < Assignment4.ballotArray.size(); i++)
			{
				Ballot ballot = Assignment4.ballotArray.get(i);
				for(int j = 0; j < ballot.getNomineesSize(); j++) 
				{
					ballot.getButtonArray().get(j).setEnabled(false);
			  	}
			}		
			Assignment4.getLogin().setEnabled(true);
			JButton btn = (JButton) e.getSource();
			btn.setEnabled(false);
			Assignment4.getVoter2().setVoted();
			try
			{
				Assignment4.updateVoterFile();
			} 
			catch(IOException er)
			{
				System.err.println(er);
			}
		}
	}	
}
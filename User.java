package atm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class User {
	/*
	 * Set a name for user
	 * set password
	 * initial cash 0
	 * deposit cash
	 * take cash
	 * check status
	 * set new password
	 */
	static Scanner scan = new Scanner(System.in);
	static int numOfUsers = 0;
	private String name;
	private String pass;
	private double cash = 0;
	private byte tryCounter = 0;
	protected byte passCounter = 0;
	protected int userNum = numOfUsers;
	protected boolean accessible = true;
	
	public User() throws FileNotFoundException {
		System.out.println("Enter a name for account");
		this.name = scan.nextLine();
		System.out.println("Enter a password");
		this.pass = scan.nextLine();
		writeData();
		Read_user_data.users.add(new User(name, pass));
	}
	
	//Constructor for creating by admin
		public User(String name, String pass) {
			this.name = name;
			this.pass = pass;
			numOfUsers++;
		}
		
	//Constructor for reading data
	public User(String name, String pass, double money, byte passCounter) {
		this.name = name;
		this.pass = pass;
		this.cash = money;
		this.passCounter = passCounter;
		if(passCounter >= 3)
			accessible = false;
		numOfUsers++;
	}
	
	//Return password
	protected String getPass() {
		return pass;
	}
	
	//Return name
	protected String getName() {
		return name;
	}
	
	public void setName() throws FileNotFoundException {
		for(User change : Read_user_data.users)
		{
			if(change.getName().equals(this.name))
			{
				System.out.println("Now enter new name");
				name = scan.nextLine();
				change.name = name;
			}
		}
		writeData();
	}
	
	protected void newPasswordAsAdmin() throws FileNotFoundException {
		System.out.println("Set a new password for this user");
		pass = scan.nextLine();
		writeData();
	}
	
	//reset password count (only for admins)
	protected void resetPassCounter() throws FileNotFoundException {
		accessible = true;
		passCounter = 0;
		writeData();
	}
	
	protected byte getPassCounter() {
		return passCounter;
	}
	
	//Set new password as user
	public void setNewPassword() throws FileNotFoundException {
		if(tryCounter > 3)
			System.out.println("You have tried too many times, contact the bank");
		else
		{
			System.out.println("Please enter old password");
			String old = scan.nextLine();
			if(old.equals(pass))
			{
				for(User change : Read_user_data.users)
				{
					if(change.getPass().equals(this.pass))
					{
						System.out.println("Now enter new password");
						pass = scan.nextLine();
						change.pass = pass;
					}
				}
				writeData();
			}
			else
			{
				System.out.println("Wrong password, try again with this option");
				tryCounter++;
			}
		}
	}
	
	//Deposit cash
	public void deposit(double deposit) throws FileNotFoundException {
		if(deposit < 0)
			System.out.println("Invalid value, please try again");
		else
		{
			this.cash += deposit;
			System.out.println(toString());
			writeData();
		} 
	}		
	
	//Withdraw cash
	public void withdraw(double withdraw) throws FileNotFoundException {
		//500 units is the most you can withdraw
		if(withdraw > 500 || withdraw > this.cash)
			System.out.println("Withdrawal is not possible, please try again");
		else
		{
			this.cash -= withdraw;
			System.out.println(toString());
			writeData();
		}
	}
	
	public void setBalance() throws FileNotFoundException {
		for(User change : Read_user_data.users)
		{
			if(change.getName().equals(this.name))
			{
				System.out.println("Enter new balance amount");
				cash = scan.nextDouble();
				change.cash = cash;
				break;
			}
		}
		writeData();
	}
	
	
	
	//Delete account - only for admins, file.txt gets deleted from directory
	public void deleteAccount() {
		/*for(User user : Read_user_data.users)
		{
			if(user.getName().equals(this.getName()))
			{
				File file = new File("UserData\\user" + userNum + ".txt");
				file.delete();
				break;
			}
		}*/
		for(int i = 0; i < Read_user_data.users.size(); i++)
		{
			if(Read_user_data.users.get(i).getName().equals(this.getName()))
			{
				File file = new File("UserData\\user" + i + ".txt");
				file.delete();
				for(int k = i; k < Read_user_data.users.size() - 1; k++)
				{
					File fileOld = new File("UserData\\user" + k + ".txt");
					File fileNew = new File("UserData\\user" + (k + 1) + ".txt");
					fileNew.renameTo(fileOld);
				}
				break;
			}
		}
	}
	
	//Check balance
	@Override
	public String toString() {
		Date date = new Date();
		return "Name: " + name + "\nMoney balance: " + cash + "\n" + date.toString();
	}
	
	//Write user data
	protected void writeData() throws FileNotFoundException {
		
		File file = new File("UserData\\user" + userNum + ".txt");
		try(PrintWriter output = new PrintWriter(file))
		{
			//Write name
			output.println(this.name);
			//Write password
			output.println(this.pass);
			//Write balance
			output.println(this.cash);
			//Write num of tries
			output.println(this.passCounter);
			output.close();
		}
		
		
	}
	
}

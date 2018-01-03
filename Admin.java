package atm;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	/*
	 * has a name
	 * has a password
	 * can create a new user
	 * can create a new admin
	 * can delete a user
	 * can access other users and change anything
	 * static array of user objects so other admins share the info
	 */
	//Number of admins - used for file name
	static short numOfAdmins = 0;
	static Scanner scan = new Scanner(System.in);
	//Name
	//Password
	protected String name = "";
	protected String pass = "";
	protected short adminNum = numOfAdmins;
	
	public Admin() throws FileNotFoundException{
		System.out.println("Enter a name for account");
		this.name = scan.nextLine();
		System.out.println("Enter a password");
		this.pass = scan.nextLine();
		writeData();
		Read_user_data.admins.add(new Admin(name, pass));
	}
	
	//constructor for reading data from files
	public Admin(String name, String pass) {
		this.name = name;
		this.pass = pass;
		numOfAdmins++;
	}
	
	protected String getName() {
		return name;
	}
	
	protected String getPass() {
		return pass;
	}
	
	protected void setPass() throws FileNotFoundException {
		System.out.println("Enter new password");
		pass = scan.nextLine();
		writeData();
	}
	
	protected void deleteUser(String userName) {
		for(User user : Read_user_data.users)
		{
			if(user.getName().equals(userName))
			{
				Read_user_data.users.remove(user);
				break;
			}
		}
	}
	
	protected void createUser() throws FileNotFoundException {
		Read_user_data.users.add(new User());
	}
	
	protected void createAdmin() throws FileNotFoundException {
		Read_user_data.admins.add(new Admin());
	}
	
	//Change someone's account
	protected void changeData() throws FileNotFoundException {
		System.out.println("What user do you want to change");
		String user_name = scan.nextLine();
		boolean found = false;
		for(User user : Read_user_data.users)
		{
			if(user.getName().equals(user_name))
			{
				Options(user);
				found = true;
				break;
			}
		}
		if(!found)
			System.out.println("User not found");
	}
	
	private void Options(User user) throws FileNotFoundException {
		System.out.println("What do you want to change on user " + user.getName() + "?");
		System.out.println("Press num\n1.Name\n2.Password\n3.Set balance"
				+ "\n4.Enable account\n5.Delete account\n6.Check info\n7.Exit");
		int choice = scan.nextInt();
		switch(choice)
		{
		case 1:
			user.setName();
			break;
		case 2:
			user.newPasswordAsAdmin();
			break;
		case 3: 
			user.setBalance(); 
			break;
		case 4: 
			user.resetPassCounter(); 
			break;
		case 5:
			user.deleteAccount();
			deleteUser(user.getName());
			System.exit(1);
		case 6:
			System.out.println(user.toString());
		case 7: break;
		default: break;
		}
	}
	
	protected void writeData() throws FileNotFoundException {
		File file = new File("UserData\\admin" + adminNum + ".txt");
		try(PrintWriter output = new PrintWriter(file))
		{
			//Write name
			output.println(this.name);
			//Write password
			output.println(this.pass);
			
			output.close();
		}
		
		
	}
	
	
}

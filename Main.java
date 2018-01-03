package atm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Main {
	static Scanner scan = new Scanner(System.in);

	
	//Read all data from a txt file
	public static void main(String[] args) throws IOException {
		File file = new File("UserData\\user0.txt");
		file.delete();
		Read_user_data.readData();
		
		boolean access = true;
		
		System.out.println("Enter your name");
		String name = scan.nextLine();
		
		boolean foundUser = false;
		//Find the user by searching the lists
		for(User user : Read_user_data.users)
		{
			if(user.getName().equals(name))
			{
				if(!user.accessible)
				{
					System.out.println("User is unaccessible, contact the admin");
					access = false;
					break;
				}
				foundUser = true;
				System.out.println("Enter password");
				String pass = scan.nextLine();
				if(user.getPass().equals(pass))
				{
					int choice = -1;
					do {
						System.out.println("What do you want to do with the account?");
						System.out.println("Press num\n1.Deposit\n2.Withdraw\n3.Balance\n4.Set new password\n5.Exit");
						choice = scan.nextInt();
						switch(choice)
						{
						case 1: 
							System.out.println("Enter amount for deposition");
							double amount = scan.nextDouble();
							user.deposit(amount);
							break;
						case 2:
							System.out.println("Enter amount for withdrawal");
							double withdraw = scan.nextDouble();
							user.withdraw(withdraw);
							break;
						case 3: 
							System.out.println(user.toString());
							break;
						case 4:
							user.setNewPassword();
							break;
						case 5: break;
						default: break;
						}
					} while(choice >= 1 && choice <= 4);
				}
				else
				{
					
					if(user.passCounter == 1)
						System.out.println("Wrong password, one more failure and the account will be blocked");
					else
						System.out.println("Wrond password, please try again");
					
					user.passCounter++;
					user.writeData();
				}
			}
		}
		if(!foundUser) //If input string is not a user, search admin
		{
			for(Admin admin : Read_user_data.admins)
			{
				if(admin.getName().equals(name))
				{
					foundUser = true;
					System.out.println("Enter password");
					String pass = scan.nextLine();
					if(admin.getPass().equals(pass))
					{
						
						int choice = -1;
						do {
							System.out.println("After each choice, choose the option again");
							System.out.println("What do you want to do with the account?");
							System.out.println("Press num\n1.Create new user\n2.Create new admin"
									+ "\n3.Check number of users\n4.Set new password\n5.Change user's data"
									+ "\n6.Exit");
							choice = scan.nextInt();
							switch(choice)
							{
							case 1: 
								User user = new User();
								break;
							case 2:
								Admin administrator = new Admin();
								break;
							case 3: 
								System.out.println("Number of users is: " + User.numOfUsers);
								break;
							case 4:
								admin.setPass();
								break;
							case 5: 
								admin.changeData();
								break;
							case 6: break;
							default: break;
							}
							
						} while(choice >= 1 && choice <= 5);
					}
					else
					{
						System.out.println("Wrong password, try again");
					}
				}
			}
		}
		if(!foundUser && access)
			System.out.println("User is not found");
	}
}

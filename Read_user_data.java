package atm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class Read_user_data {
	static ArrayList<User> users = new ArrayList<>();
	static ArrayList<Admin> admins = new ArrayList<>();
	
	protected static void readData() throws IOException{
		//Map to set user name/pass
		File directory = new File("UserData");
		if(!directory.exists())
		{
			directory.mkdir();
			//create an admin first
			Admin admin = new Admin();
		}
		else
		{
			//Array List with all the file names in the directory UserData
			ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(directory.list()));
			
			for(String s : fileNames)
			{
				String name = "";
				String pass = "";
				double money = 0;
				byte passCounter = 0;
				File file = new File("UserData\\" + s);
				Scanner input = new Scanner(file);
				if(s.contains("admin"))
				{
					name = input.nextLine();
					pass = input.nextLine();
					admins.add(new Admin(name, pass));
				}
				if(s.contains("user"))
				{
					name = input.nextLine();
					pass = input.nextLine();
					money = input.nextDouble();
					passCounter = input.nextByte();
					
					users.add(new User(name, pass, money, passCounter));
					
				}
				input.close();
			}
			
		}
		
	}
}

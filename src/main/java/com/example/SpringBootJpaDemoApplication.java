package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.dao.UserRepository;
import com.example.entity.User;

import ch.qos.logback.core.net.SyslogOutputStream;

@SpringBootApplication
public class SpringBootJpaDemoApplication {

	public static void main(String[] args) {
		try {
			ApplicationContext context = SpringApplication.run(SpringBootJpaDemoApplication.class, args);
			
			UserRepository userRepository = context.getBean(UserRepository.class);
			Scanner sc = new Scanner(System.in);
			boolean next = true;
			while(next) {
				System.out.println("*****Welcome to SpringBoot CRUD App Demo*****");
				System.out.println("1.Create");
				System.out.println("2.Fetch one Record");
				System.out.println("3.Show All Records");
				System.out.println("4.Update Record");
				System.out.println("5.Delete Record");
				System.out.println("6.Quit");
				System.out.println("");
				System.out.println("Please, enter your choice between 1 to  6: ");
				int choice = sc.nextInt();
				sc.nextLine();
				List<User>listUsers = new ArrayList<>() ;
				listUsers.clear();
				User user;
				switch(choice) {
				case 1:
					//System.out.println("Option1 : ");
					System.out.println("Enter the user name : ");
					String name = sc.nextLine();
					System.out.println("Enter the city : ");
					String city = sc.nextLine();
					System.out.println("Enter the user status  : ");
					String status = sc.nextLine();
					user = new User();
					user.setName(name);
					user.setCity(city);
					user.setStatus(status);
					
					User user2 = userRepository.save(user);
					System.out.println("Saved to database: " + user2.toString());
		
					break;
				case 2:
					System.out.println("Enter the user-Id : ");
					Long id = sc.nextLong();
					sc.nextLine();
					
					Optional<User> optional = userRepository.findById(id);
					if(optional.isPresent()) {
						User user3 = optional.get();
						System.out.println("User Details : " + user3);
					}else {
						System.out.println("User Not Found for the Id: " + id );
					}
					
					break;
				case 3:
					//Option for Displaying All Records
					Iterable<User> iterable = userRepository.findAll();
					
					System.out.println("All Users Info: ");
					iterable.forEach(u ->{
						System.out.println(u);
					});
//					for(User u: iterable) {
//						System.out.println(u);
//						//listUsers.add(u);
//					}
					//System.out.println(listUsers);
					break;
				case 4:
					//Option for Update
					System.out.println("Enter the user-Id : ");
					id = sc.nextLong();
					sc.nextLine();
					
					optional = userRepository.findById(id);
					if(optional.isPresent()) {
						User user3 = optional.get();
						System.out.println("Current User Details : " + user3);
						System.out.println("Enter the User name : ");
						String newName = sc.nextLine();
						System.out.println("Enter the City : ");
						String newCity = sc.nextLine();
						System.out.println("Enter the new User status : ");
						String newStatus = sc.nextLine();
						
						user3.setName(newName);
						user3.setCity(newCity);
						user3.setStatus(newStatus);
						User userResult = userRepository.save(user3);
						System.out.println("Updated User Detials : " + userResult);
						
						
					}else {
						System.out.println("User Not Found for the Id: " + id );
					}
					break;
				case 5:
					//Option for Delete
					System.out.println("Enter the user-Id : ");
					id = sc.nextLong();
					sc.nextLine();
					
					optional = userRepository.findById(id);
					if(optional.isPresent()) {
						User user3 = optional.get();
						userRepository.delete(user3);
						System.out.println("The user has been deleted !!! : " + user3);						
					}else {
						System.out.println("User Not Found for the Id: " + id );
					}
					break;
				case 6:
					System.out.println("Program got closed successfully!!!");
					next = false;
					break;
				default:
					System.out.println("Invalid Option!!!");
				}
			}
			
			
			
		
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
		
	}

}

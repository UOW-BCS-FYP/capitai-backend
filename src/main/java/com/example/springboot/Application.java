package com.example.springboot;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.swing.text.html.parser.Entity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.springboot.dao.BudgetCtgyRepository;
import com.example.springboot.dao.RefreshableCRUDRepositoryImpl;
import com.example.springboot.dao.RoleRepository;
import com.example.springboot.dao.SpendingRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.SpendingInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.UserRole;
import com.example.springboot.dao.ExpIncRepository;
import com.example.springboot.storage.StorageService;

@EnableJpaRepositories(repositoryBaseClass = RefreshableCRUDRepositoryImpl.class)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// 	return args -> {
	// 		System.out.println("Let's inspect the beans provided by Spring Boot:");

	// 		String[] beanNames = ctx.getBeanDefinitionNames();
	// 		Arrays.sort(beanNames);
	// 		for (String beanName : beanNames) {
	// 			System.out.println(beanName);
	// 		}
	// 	};
	// }

	@Bean
    public CommandLineRunner demoData(RoleRepository roleRepo, UserRepository userRepo, BudgetCtgyRepository budgetRepo, ExpIncRepository expIncRepo, SpendingRepository spendingRepo) {
        return args -> {
			UserRole adminRole = new UserRole(1, "Admin");
			UserRole userRole = new UserRole(2, "User");
            roleRepo.saveAll(Arrays.asList(
				adminRole,
				userRole
			));

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String password = encoder.encode("123456");

			// bio for user1
			String bio1 = "Hi there! I am Joanna Wellick. I am a travel enthusiast and I love to explore new places. I am a travel blogger and I love to share my travel experiences with the world. I am also a travel guide and I love to help people explore new places.";
			String bio2 = "Hi there! I am Elliot Alderson. I am a travel enthusiast and I love to explore new places. I am a travel blogger and I love to share my travel experiences with the world. I am also a travel guide and I love to help people explore new places.";
			// bio for user2
			UserInfo adminUser = new UserInfo(1, "Admin", password, new HashSet<>(Arrays.asList(adminRole)), "admin@wanderwise.com", "I am Admin", "Administrator", "admin", "admin", null, null, null, null, null);
			UserInfo defaultUser = new UserInfo(2, "Demo User1", password, new HashSet<>(Arrays.asList(userRole)), "Demo User1", bio1, "Joanna Wellick", "Wellick", "Joanna", "/src/assets/post-author-icon.png", null, null, null, null);
			UserInfo defaultUser2 = new UserInfo( 3, "Demo User2", password, new HashSet<>(Arrays.asList(userRole)), "Demo User2", bio2, "Elliot Alderson", "Alderson", "Elliot", "/src/assets/post-author-icon.png", null, null, null, null);
			userRepo.saveAll(Arrays.asList(
				adminUser,
				defaultUser,
				defaultUser2
			));

			Date now = Date.from(Instant.now());
			
			BudgetCtgyInfo cat1 = new BudgetCtgyInfo(1, "Furniture repairment", 350, false, 0, now, "active", defaultUser);
			BudgetCtgyInfo cat2 = new BudgetCtgyInfo(2, "Rent", 10000, true, 1, now, "active", defaultUser2);
			BudgetCtgyInfo cat3 = new BudgetCtgyInfo(3, "Transportation", 200, true, 1, now, "active", defaultUser);
			BudgetCtgyInfo cat4 = new BudgetCtgyInfo(4, "Tuition fee", 10000, false, 0, now, "active", defaultUser2);
			BudgetCtgyInfo cat5 = new BudgetCtgyInfo(5, "Food", 3000, true, 1, now, "active", defaultUser);
			BudgetCtgyInfo cat6 = new BudgetCtgyInfo(6, "Water bill", 300, true, 3, now, "inactive", defaultUser2);
			BudgetCtgyInfo cat7 = new BudgetCtgyInfo(7, "Other", 500, false, 0, now, "active", defaultUser);
			BudgetCtgyInfo cat8 = new BudgetCtgyInfo(8, "Other", 600, false, 0, now, "active", defaultUser2);
			budgetRepo.saveAll(Arrays.asList(
				cat1,
				cat2,
				cat3,
				cat4,
				cat5,
				cat6,
				cat7,
				cat8
			));
			
			ExpIncInfo exp1 = new ExpIncInfo(1, "Wage", 10000, true, 1, now, "active", defaultUser);
			ExpIncInfo exp2 = new ExpIncInfo(2, "Stocks", 1000, true, 1, now, "active", defaultUser);
			ExpIncInfo exp3 = new ExpIncInfo(3, "Red envelop money", 2500, false, 0, now, "active", defaultUser);
			ExpIncInfo exp4 = new ExpIncInfo(4, "Business", 25000, true, 3, now, "inactive", defaultUser2);
			ExpIncInfo exp5 = new ExpIncInfo(5, "Property leasing", 15000, true, 1, now, "active", defaultUser2);
			ExpIncInfo exp6 = new ExpIncInfo(6, "Bitcoin cash out", 5500, false, 0, now, "active", defaultUser2);
			expIncRepo.saveAll(Arrays.asList(
				exp1,
				exp2,
				exp3,
				exp4,
				exp5,
				exp6
			));
			
			SpendingInfo spending1 = new SpendingInfo(1, "Refridgerator repairment", 350, now, "Technician", cat1, defaultUser);
			SpendingInfo spending2 = new SpendingInfo(2, "Rent", 10000, now, "Landlord", cat2, defaultUser2);
			SpendingInfo spending3 = new SpendingInfo(3, "Taxi", 100, now, "Driver", cat3, defaultUser);
			SpendingInfo spending4 = new SpendingInfo(4, "Semester A tuition fee", 10000, now, "University", cat4, defaultUser2);
			SpendingInfo spending5 = new SpendingInfo(5, "Lunch", 60, now, "Restaurant", cat5, defaultUser);
			SpendingInfo spending6 = new SpendingInfo(6, "Water bill", 300, now, "Water department", cat6, defaultUser2);
			SpendingInfo spending7 = new SpendingInfo(7, "Gift", 350, now, "Giftshop", cat7, defaultUser);
			SpendingInfo spending8 = new SpendingInfo(8, "Doctor visit", 350, now, "Clinic", cat8, defaultUser2);
			spendingRepo.saveAll(Arrays.asList(
				spending1,
				spending2,
				spending3,
				spending4,
				spending5,
				spending6,
				spending7,
				spending8
			));
        };
    }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("*")
					.allowedHeaders("*")
					.exposedHeaders("Authorization") // for JWT
					.allowCredentials(true); // for JWT
			}
		};
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			// storageService.deleteAll();
			storageService.init();
		};
	}

}

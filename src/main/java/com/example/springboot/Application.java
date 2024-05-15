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

import com.example.springboot.dao.BookingRepository;
import com.example.springboot.dao.RefreshableCRUDRepositoryImpl;
import com.example.springboot.dao.RoleRepository;
import com.example.springboot.dao.TourRepository;
import com.example.springboot.dao.TourReviewRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.model.BookingInfo;
import com.example.springboot.model.TourInfo;
import com.example.springboot.model.TourReview;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.UserRole;
import com.example.springboot.model.Dispute;
import com.example.springboot.model.DisputeType;
import com.example.springboot.dao.DisputeRepository;
import com.example.springboot.dao.DisputeTypeRepository;
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
    public CommandLineRunner demoData(RoleRepository roleRepo, UserRepository userRepo, TourRepository tourRepo, TourReviewRepository tourReviewRepo, BookingRepository bookingRepo, DisputeTypeRepository disputeTypeRepo, DisputeRepository disputeRepo) {
        return args -> {
			UserRole adminRole = new UserRole(1, "Admin");
			UserRole userRole = new UserRole(2, "User");
			// var touristRole = new UserRole(3, "Tourist");
			// var guideRole = new UserRole(4, "Guide");
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
			UserInfo adminUser = new UserInfo(1, "admin@wanderwise.com", password, new HashSet<>(Arrays.asList(adminRole)), "admin@wanderwise.com", "I am Admin", "Administrator", "admin", "admin", null, null, null, null, null);
			UserInfo defaultUser = new UserInfo(2, "user@wanderwise.com", password, new HashSet<>(Arrays.asList(userRole)), "user@wanderwise.com", bio1, "Joanna Wellick", "Wellick", "Joanna", "/src/assets/post-author-icon.png", null, null, null, null);
			UserInfo defaultUser2 = new UserInfo( 3, "user2@wanderwise.com", password, new HashSet<>(Arrays.asList(userRole)), "user2@wanderwise.com", bio2, "Elliot Alderson", "Alderson", "Elliot", "/src/assets/post-author-icon.png", null, null, null, null);
			userRepo.saveAll(Arrays.asList(
				adminUser,
				defaultUser,
				defaultUser2
			));

			String dummyTourDescription = "Aenean eleifend ante maecenas pulvinar montes lorem et pede dis dolor pretium donec dictum. Vici consequat justo enim. Venenatis eget adipiscing luctus lorem.";
			TourInfo tour1 = new TourInfo(1, "Integer Maecenas Eget Viverra", "Hong Kong", 100, "1 week", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-01.png", defaultUser, new HashSet<>(), null);
			TourInfo tour2 = new TourInfo(2, "Aenean eleifend ante maecenas", "Florida City", 200, "2 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-02.png", defaultUser, new HashSet<>(), null);
			TourInfo tour3 = new TourInfo(3, "Pulvinar montes lorem et pede", "USA", 300, "3 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-03.png", defaultUser2, new HashSet<>(), null);
			TourInfo tour4 = new TourInfo(4, "Dis dolor pretium donec dictum", "Canada", 400, "4 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-04.png", defaultUser, new HashSet<>(), null);
			TourInfo tour5 = new TourInfo(5, "Vici consequat justo enim", "Australia", 500, "5 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-05.png", defaultUser, new HashSet<>(), null);
			TourInfo tour6 = new TourInfo(6, "Venenatis eget adipiscing luctus lorem", "Florida City", 600, "6 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-06.png", defaultUser, new HashSet<>(), null);
			TourInfo tour7 = new TourInfo(7, "Aenean eleifend ante maecenas", "Australia", 700, "7 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-07.png", defaultUser2, new HashSet<>(), null);
			TourInfo tour8 = new TourInfo(8, "Pulvinar montes lorem et pede", "USA", 800, "8 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-08.png", defaultUser2, new HashSet<>(), null);
			TourInfo tour9 = new TourInfo(9, "Dis dolor pretium donec dictum", "China", 900, "9 weeks", dummyTourDescription, "active", Date.from(Instant.now()), "Aenean Eleifend,Aliquam", "/src/assets/post-09.png", defaultUser, new HashSet<>(), null);
			tourRepo.saveAll(Arrays.asList(
				tour1,
				tour2,
				tour3,
				tour4,
				tour5,
				tour6,
				tour7,
				tour8,
				tour9
			));

			Date now = Date.from(Instant.now());
			TourReview review1 = new TourReview(1, "Great tour!", 4, tour1, defaultUser, now);
			TourReview review2 = new TourReview(2, "Great tour!", 5, tour1, defaultUser2, now);
			TourReview review3 = new TourReview(3, "Great tour!", 4, tour2, defaultUser, now);
			tourReviewRepo.saveAll(Arrays.asList(
				review1,
				review2,
				review3
			));

			BookingInfo booking1 = new BookingInfo(1, tour9, defaultUser2, now, "booking for 1 person!", 1, 900, "PAID", "Settle", "Payment Mode", "Payment Ref #1", "Payment Remarks", now);
			BookingInfo booking2 = new BookingInfo(2, tour8, defaultUser, now, "booking for 2 people!", 2, 800, "PAID", "Settle", "Payment Mode", "Payment Ref #2", "Payment Remarks", now);
			BookingInfo booking3 = new BookingInfo(3, tour7, defaultUser2, now, "booking for 3 people!", 3, 700, "PAID", "Settle", "Payment Mode", "Payment Ref #3", "Payment Remarks", now);
			BookingInfo booking4 = new BookingInfo(4, tour6, defaultUser, now, "booking for 4 people!", 4, 600, "PAID", "Settle", "Payment Mode", "Payment Ref #4", "Payment Remarks", now);
			BookingInfo booking5 = new BookingInfo(5, tour5, defaultUser2, now, "booking for 5 people!", 5, 500, "PAID", "Settle", "Payment Mode", "Payment Ref #5", "Payment Remarks", now);
			BookingInfo booking6 = new BookingInfo(6, tour4, defaultUser, now, "booking for 6 people!", 6, 400, "PAID", "Settle", "Payment Mode", "Payment Ref #6", "Payment Remarks", now);
			BookingInfo booking7 = new BookingInfo(7, tour3, defaultUser2, now, "booking for 7 people!", 7, 300, "PAID", "Settle", "Payment Mode", "Payment Ref #7", "Payment Remarks", now);
			BookingInfo booking8 = new BookingInfo(8, tour2, defaultUser, now, "booking for 8 people!", 8, 200, "PAID", "Settle", "Payment Mode", "Payment Ref #8", "Payment Remarks", now);
			BookingInfo booking9 = new BookingInfo(9, tour1, defaultUser2, now, "booking for 9 people!", 9, 100, "PAID", "Settle", "Payment Mode", "Payment Ref #9", "Payment Remarks", now);
			BookingInfo booking10 = new BookingInfo(10, tour9, defaultUser, now, "booking for 10 people!", 10, 900, "PAID", "Settle", "Payment Mode", "Payment Ref #10", "Payment Remarks", now);
			bookingRepo.saveAll(Arrays.asList(
				booking1,
				booking2,
				booking3,
				booking4,
				booking5,
				booking6,
				booking7,
				booking8,
				booking9,
				booking10
			));

			DisputeType payment = new DisputeType(1, "Payment");
			DisputeType arrangement = new DisputeType(2, "Arrangement");
			DisputeType other = new DisputeType(3, "Other");
			disputeTypeRepo.saveAll(Arrays.asList(
				payment,
				arrangement,
				other
			));
			
			Dispute Dispute1 = new Dispute(1, defaultUser, booking1, new HashSet<>(Arrays.asList(payment)), "too expensive!", "lower the price!");
			Dispute Dispute2 = new Dispute(2, defaultUser2, booking2, new HashSet<>(Arrays.asList(arrangement)), "this tourist is uncooperative!", "ban him from the platform!");
			Dispute Dispute3 = new Dispute(3, defaultUser, booking3, new HashSet<>(Arrays.asList(other)), "this tour is offensive!", "change the description!");
			disputeRepo.saveAll(Arrays.asList(
					Dispute1,
					Dispute2,
					Dispute3
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
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
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

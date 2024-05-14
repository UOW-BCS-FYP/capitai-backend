package com.example.springboot.web;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.BookingRepository;
import com.example.springboot.dao.TourRepository;
import com.example.springboot.dao.TourReviewRepository;
import com.example.springboot.dao.UploadedFileRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.TourBookRequestDTO;
import com.example.springboot.dto.TourSearchRequestDTO;
import com.example.springboot.model.BookingInfo;
import com.example.springboot.model.TourInfo;
import com.example.springboot.model.TourReview;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.TourService;
import com.example.springboot.storage.FileSystemStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/")
public class TourController {

    private final TourService tourService;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UploadedFileRepository uploadedFileRepository;

    @Autowired
    public TourController(
        TourService tourService,
        TourRepository tourRepository,
        UserRepository userRepository,
        BookingRepository bookingRepository,
        UploadedFileRepository uploadedFileRepository
    ) {
        this.tourService = tourService;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @GetMapping("/tours")
    public List<TourInfo> getAllTours() {
        var tours = tourRepository.findAll();
        return Streamable.of(tours).toList();
    }

    @GetMapping("/tours/{tour_id}")
    public TourInfo getTourById(@PathVariable("tour_id") Long id) {
        return tourRepository.findFirstById(id);
    }
    
    @PostMapping("/tours")
    public TourInfo createTour(@RequestBody TourInfo tour) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        tour.setUserInfo(user);
        return tourRepository.save(tour);
    }

    @PatchMapping("/tours/{tour_id}")
    public TourInfo updateTour(@PathVariable("tour_id") Long id, @RequestBody TourInfo tour) {
        
        TourInfo existingTour = tourRepository.findFirstById(id);

        if (existingTour == null) {
            throw new RuntimeException("Tour not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(tour, existingTour);

        existingTour.setId(id);
        // existingTour.setTitle(tour.getTitle());
        // existingTour.setLocation(tour.getLocation());
        // existingTour.setDescription(tour.getDescription());
        // existingTour.setPrice(tour.getPrice());
        // existingTour.setStatus(tour.getStatus());
        // existingTour.setTags(tour.getTags());
        // if (tour.getPicture() != null) {
        //     existingTour.setPicture(tour.getPicture());
        // }

        tourRepository.save(existingTour);
        tourRepository.refresh(existingTour);
        return existingTour;
    }

    // booking a tour
    @PostMapping("/tours/{tour_id}/book")
    public BookingInfo bookTour(@PathVariable("tour_id") Long id, @RequestBody TourBookRequestDTO tourBookRequest) {
        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        
        // get tour
        TourInfo existingTour = tourRepository.findFirstById(id);
        
        // create booking
        BookingInfo booking = new BookingInfo();
        booking.setUserInfo(user);
        booking.setTourInfo(existingTour);
        booking.setBookingDate(tourBookRequest.getBookingDate());
        booking.setNoOfPersons(tourBookRequest.getNoOfPersons());
        booking.setRemarks(tourBookRequest.getRemarks());
        booking.setCreatedDate(Date.from(new Date().toInstant()));
        booking.setTotalAmount(existingTour.getPrice() * tourBookRequest.getNoOfPersons());
        booking.setPaymentMode("ONLINE");
        booking.setPaymentRefId("PAYMENT-REF-" + booking.getId());
        booking.setPaymentRemarks("Payment received successfully");
        booking.setPaymentStatus("SUCCESS");
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        return booking;
    }

    @GetMapping("/me/tours")
    public ResponseEntity<TourInfo[]> getTours() {
        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);

        return ResponseEntity.ok().body(tourRepository.findAllByUserInfo(user));
    }

    @PostMapping("/tours/search")
    public ResponseEntity<TourInfo[]> searchTours(@RequestBody TourSearchRequestDTO search) {
        try {
            return ResponseEntity.ok().body(tourService.searchTour(search));
        } catch (Exception e) {
            // log.error("Error while searching tours", e);
            System.out.println("Error while searching tours: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

}

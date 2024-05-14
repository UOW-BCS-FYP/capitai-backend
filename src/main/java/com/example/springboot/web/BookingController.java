package com.example.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.BookingRepository;
import com.example.springboot.dao.TourRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.model.BookingInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/")
public class BookingController {
    private final UserServiceImpl userService;
    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;

    @Autowired
    public BookingController(UserServiceImpl userServiceImpl, BookingRepository bookingRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
        this.userService = userServiceImpl;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/bookings")
    public List<BookingInfo> getAllBookings() {
        var bookings = bookingRepository.findAll();
        return Streamable.of(bookings).toList();
    }

    @GetMapping("/me/bookings")
    public List<BookingInfo> getMyBookings() {
        UserInfo user = userService.getCurrentUserInfo();
        var bookings = bookingRepository.findAllByUserInfo(user);
        return Streamable.of(bookings).toList();
    }
    
    @DeleteMapping("/me/bookings/{booking_id}")
    public BookingInfo deleteBooking(@PathVariable("booking_id") Long id) {
        var booking = bookingRepository.findByUserInfoAndId(userService.getCurrentUserInfo(), id);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        bookingRepository.refresh(booking);
        return booking;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/bookings/{booking_id}")
    public BookingInfo deleteBookingByAdmin(@PathVariable("booking_id") Long id) {
        var booking = bookingRepository.findFirstById(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        bookingRepository.refresh(booking);
        return booking;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/bookings/{booking_id}/refund")
    public ResponseEntity<BookingInfo> refundBooking(@PathVariable("booking_id") Long id) {
        // get booking
        BookingInfo booking = bookingRepository.findFirstById(id);
        
        // check if booking exists
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        // refund booking
        booking.setPaymentStatus("REFUNDED");
        booking.setStatus("REFUNDED");
        bookingRepository.save(booking);

        return ResponseEntity.ok().body(booking);
    }
}

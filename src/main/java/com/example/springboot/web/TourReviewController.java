package com.example.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.TourRepository;
import com.example.springboot.dao.TourReviewRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.model.TourInfo;
import com.example.springboot.model.TourReview;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class TourReviewController {
    
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final TourReviewRepository tourReviewRepository;

    @Autowired
    public TourReviewController(TourRepository tourRepository, UserRepository userRepository, TourReviewRepository tourReviewRepository) {
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.tourReviewRepository = tourReviewRepository;
    }
    
    @PostMapping("/tours/{tour_id}/reviews")
    public TourInfo addReview(@PathVariable("tour_id") Long id, @RequestBody TourReview review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        // get current user
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        TourInfo existingTour = tourRepository.findFirstById(id);
        review.setTourInfo(tourRepository.findFirstById(id));
        review.setUserInfo(user);
        System.out.println("review: " + review.getComment());
        tourReviewRepository.save(review);
        // tourRepository.refresh(existingTour);
        return existingTour;
    }

    @GetMapping("/tours/{tour_id}/reviews")
    public List<TourReview> getReviews(@PathVariable("tour_id") Long id) {
        TourInfo tour = tourRepository.findFirstById(id);
        return Streamable.of(tourReviewRepository.findAllByTourInfo(tour)).toList();
    }
}

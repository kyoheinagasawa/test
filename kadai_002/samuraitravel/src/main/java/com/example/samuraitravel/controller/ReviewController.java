package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("houses/{id}/reviews")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final HouseRepository houseRepository;
	private final ReviewService reviewService;

	public ReviewController(ReviewRepository reviewRepository, HouseRepository houseRepository,
			ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.houseRepository = houseRepository;
		this.reviewService = reviewService;
	}

	@GetMapping
	public String index(@PathVariable(name = "id") Integer id, Model model,
			@PageableDefault(page = 0, size = 8, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Review> reviews = reviewRepository.findByHouseId(id, pageable);
		House house = houseRepository.getReferenceById(id);

		model.addAttribute("Reviews", reviews);
		model.addAttribute("house", house);

		return "reviews/index";
	}

	@GetMapping("/register")
	public String register(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		House house = houseRepository.getReferenceById(id);
		model.addAttribute("house", house);
		
		ReviewRegisterForm reviewRegisterForm = new ReviewRegisterForm();
		reviewRegisterForm.setHouseId(id); // houseId を設定
		reviewRegisterForm.setUserId(id);
		
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		return "reviews/register";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "reviews/register";
		}
		
		reviewService.create(reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを登録しました。");
		
		return "redirect:/reviews/index";
	}

}
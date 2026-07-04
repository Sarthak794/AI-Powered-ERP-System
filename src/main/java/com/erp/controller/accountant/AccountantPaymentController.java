package com.erp.controller.accountant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.erp.service.AccountantPaymentService;

@Controller
@RequestMapping("/accountant/payments")
public class AccountantPaymentController {

    private final AccountantPaymentService paymentService;

    public AccountantPaymentController(AccountantPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Display all payments
    @GetMapping
    public String payments(Model model) {

        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("activePage", "payments");
        model.addAttribute("content", "accountant/payments");

        return "layout/accountant-layout";
    }


    // Collect a new payment
    @PostMapping("/collect")
    public String collectPayment(
            @RequestParam Long studentId,
            @RequestParam int amount,
            RedirectAttributes ra
    ) {
        try {
            paymentService.collectPayment(studentId, amount);
            ra.addFlashAttribute("success", "Payment collected successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accountant/payments";
    }
}

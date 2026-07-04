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
@RequestMapping("/accountant/fees")
public class AccountantFeeController {

    private final AccountantPaymentService paymentService;

    public AccountantFeeController(AccountantPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public String fees(Model model) {

        model.addAttribute("fees", paymentService.getAllFees());
        model.addAttribute("activePage", "fees");
        model.addAttribute("content", "accountant/fees");

        return "layout/accountant-layout";
    }

    @PostMapping("/collect")
    public String collect(
            @RequestParam Long studentId,
            @RequestParam int amount,
            RedirectAttributes ra
    ) {
        try {
            paymentService.collectPayment(studentId, amount);
            ra.addFlashAttribute("success", "Payment collected successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accountant/fees";
    }
}

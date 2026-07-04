package com.erp.controller.student;

import com.erp.entity.Fee;
import com.erp.entity.Student;
import com.erp.entity.StudentOnlinePayment;
import com.erp.repository.FeeRepository;
import com.erp.repository.StudentOnlinePaymentRepository;
import com.erp.repository.StudentRepository;
import com.erp.service.StudentPaymentService;
import com.razorpay.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student/payment")
public class StudentPaymentController {

    private final StudentPaymentService paymentService;
    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;
    private final StudentOnlinePaymentRepository onlinePaymentRepository;

    public StudentPaymentController(
            StudentPaymentService paymentService,
            FeeRepository feeRepository,
            StudentRepository studentRepository,
            StudentOnlinePaymentRepository onlinePaymentRepository
    ) {
        this.paymentService = paymentService;
        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
        this.onlinePaymentRepository = onlinePaymentRepository;
    }

    @GetMapping
    public String paymentPage(Model model) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow();

        Fee fee = feeRepository
                .findByStudent(student)
                .orElseThrow();

        List<StudentOnlinePayment> payments =
                onlinePaymentRepository.findByStudent(student);

        model.addAttribute("totalFee", fee.getTotalAmount());
        model.addAttribute("paidFee", fee.getPaidAmount());
        model.addAttribute("pendingFee", fee.getPendingAmount());

        model.addAttribute("payments", payments);

        model.addAttribute("activePage", "payment");
        model.addAttribute("content", "student/payments");
        return "layout/student-layout";
    }

    @PostMapping("/create-order")
    @ResponseBody
    public String createOrder(@RequestParam int amount) throws Exception {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Order order = paymentService.createOrder(username, amount);

        return order.toString();
    }

    @PostMapping("/success")
    public String paymentSuccess(
            @RequestParam int amount,
            @RequestParam String razorpay_order_id,
            @RequestParam String razorpay_payment_id
    ) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        paymentService.verifyAndSavePayment(
                username,
                amount,
                razorpay_order_id,
                razorpay_payment_id
        );

        return "redirect:/student/payment";
    }
}
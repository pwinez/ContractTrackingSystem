package com.mebitech.contract.controller;

import com.mebitech.contract.mail.ScheduledTasks;
import com.mebitech.contract.service.ContractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class MailController {
    private final ScheduledTasks scheduledTasks;

    public MailController(ScheduledTasks scheduledTasks) {
        this.scheduledTasks = scheduledTasks;
    }

    /*@PostMapping("/{contractId}")*/
    @GetMapping("/send")
    @ResponseBody
    public String sendMail() {
        try {
            scheduledTasks.sendUpcomingContractNotificationsDaily();
            return "E-posta başarıyla gönderildi.";
        } catch (Exception e) {
            return "E-posta gönderme hatası: " + e.getMessage();
        }
    }
}

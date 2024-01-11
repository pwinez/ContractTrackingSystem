package com.mebitech.contract.mail;

import com.mebitech.contract.model.Contract;
import com.mebitech.contract.service.ContractService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private final ContractService contractService;
    private final JavaMailSender javaMailSender;

    public ScheduledTasks(ContractService contractService, JavaMailSender javaMailSender) {
        this.contractService = contractService;
        this.javaMailSender = javaMailSender;
    }


    @Scheduled(cron = "0 0 12 * * ?") // Her gün saat 12:00'de çalışacak şekilde ayarlandı
    public void sendUpcomingContractNotificationsDaily() {
        List<Contract> upcomingContracts = contractService.getUpcomingContracts();

        for (Contract contract : upcomingContracts) {
            sendEmail(contract);
        }
    }

    private void sendEmail(Contract contract) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("emresron@gmail.com");
        message.setTo("emresron@gmail.com");
        message.setSubject("Bitiş Tarihi Yaklaşan Sözleşme Bildirimi");
        message.setText("Sayın kullanıcı,\n\n" +
                "Bitiş tarihi yaklaşan bir sözleşmeniz bulunmaktadır. Aşağıdaki detayları kontrol ediniz:\n\n" +
                "Şirket İsmi: " + contract.getCompanyName() + "\n" +
                "Başlangıç Tarihi: " + contract.getStartDateFormatted() + "\n" +
                "Bitiş Tarihi: " + contract.getEndDateFormatted() + "\n" +
                /*"Sözleşme Linki: " + contract.getContractLink() + "\n\n" +*/
                "Saygılarımızla,\n" +
                "Sözleşme Bildirim Sistemi");

        javaMailSender.send(message);
    }

}

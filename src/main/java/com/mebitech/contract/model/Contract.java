package com.mebitech.contract.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "contracts")
public class Contract {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    private String companyName;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createDate = LocalDateTime.now();


    public Contract(Long contractId, String companyName, LocalDate startDate, LocalDate endDate, LocalDateTime createDate) {
        this.contractId = contractId;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
    }

    public Contract() {

    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getContractId() {
        return contractId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getStartDate() {
        return startDate;


    }

    public String getStartDateFormatted() {
        // Tarih biçimi desenini belirle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // endDate'ı belirtilen desene göre biçimlendir
        return startDate.format(formatter);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getEndDateFormatted() {
        // Tarih biçimi desenini belirle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // endDate'ı belirtilen desene göre biçimlendir
        return endDate.format(formatter);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

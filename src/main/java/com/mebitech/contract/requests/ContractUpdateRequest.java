package com.mebitech.contract.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractUpdateRequest {
    String companyName;
    LocalDate startDate;
    LocalDate endDate;
}

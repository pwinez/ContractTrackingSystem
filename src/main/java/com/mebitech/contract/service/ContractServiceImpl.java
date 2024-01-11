package com.mebitech.contract.service;

import com.mebitech.contract.model.Contract;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractServiceImpl {
    List<Contract> getAllContracts();
    List<Contract> getUpcomingContracts();
    boolean addContract(Contract contract);
    Contract getContractById(Long contractId);
    void deleteOneContractById(Long contractId);
    Page<Contract> findPaginated(int pageNo, int pageSize);
}

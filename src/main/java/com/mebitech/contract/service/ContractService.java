package com.mebitech.contract.service;

import com.mebitech.contract.model.Contract;
import com.mebitech.contract.repository.ContractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService implements ContractServiceImpl {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {

        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public List<Contract> getUpcomingContracts() {
        LocalDate now = LocalDate.now();
        LocalDate twoWeeksLater = now.plusWeeks(2);



        return contractRepository.findAll()
                .stream()
                .filter(contract -> contract.getEndDate().isAfter(now) && contract.getEndDate().isBefore(twoWeeksLater))
                .collect(Collectors.toList());
    }


    public boolean validateContractDates(Contract contract) {
        LocalDate startDate = contract.getStartDate();
        LocalDate endDate = contract.getEndDate();

        // Check if the start date is after the end date
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            // You can log a warning or throw an exception here
            System.out.println("Warning: Start date is after the end date for contract " + contract.getContractId());
        }
        return false;
    }

    @Override
    public boolean addContract(Contract contract) {

        // Validate contract dates
        validateContractDates(contract);

        try {
            // If the validation passes, save the contract
            contractRepository.save(contract);

            // Eğer save işlemi başarılı ise true dön
            return true;
        } catch (Exception e) {
            // Eğer save işlemi başarısız olursa logla ve false dön
            e.printStackTrace(); // Ya da loglama çerçevesini kullanabilirsiniz
            return false;
        }
    }

    public List<Contract> getAllContractsOrderedByCreationDateDesc() {
        return contractRepository.findAllByOrderByCreateDateDesc();
    }

    @Override
    public void deleteOneContractById(Long contractId) {
      contractRepository.deleteById(contractId);
    }

    @Override
    public Page<Contract> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return this.contractRepository.findAll(pageable);
    }

    public void updateOneContractById(Long contractId, Contract contract){
        Optional<Contract> optionalContract = contractRepository.findById(contractId);
        if (optionalContract.isPresent()  && contract != null){

            Contract contractUpdate = optionalContract.get();

            contractUpdate.setCompanyName(contract.getCompanyName());
            contractUpdate.setStartDate(contract.getStartDate());
            contractUpdate.setEndDate(contract.getEndDate());

            contractRepository.save(contractUpdate);
        }
    }



@Override
    public Contract getContractById(Long contractId) {
        Optional<Contract> optionalContract = contractRepository.findById(contractId);
        return optionalContract.orElse(null);
    }

}



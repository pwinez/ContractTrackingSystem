package com.mebitech.contract.controller;

import com.mebitech.contract.model.Contract;
import com.mebitech.contract.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {
    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

/*    @GetMapping
    public String getAllContracts(Model model) {


        model.addAttribute("contracts", contractService.getAllContracts());
        return "contracts";
    }*/

    @GetMapping
    public String getContracts(Model model) {
        List<Contract> contracts = contractService.getAllContractsOrderedByCreationDateDesc();
        model.addAttribute("contracts", contracts);
        return findPaginated(1, model);
    }


    @GetMapping("/upcoming-contracts")
    public String getUpcomingContracts(Model model) {
        model.addAttribute("upcomingContracts", contractService.getUpcomingContracts());
        return "upcoming-contracts";
    }

    @GetMapping("/add-contract")
    public String showAddContractForm(Model model) {
        model.addAttribute("contract", new Contract());
        return "add-contract";
    }

    @GetMapping("/delete/{contractId}")
    public String deleteContract(@PathVariable("contractId") Long contractId, Model model) {
        Contract contract = contractService.getContractById(contractId);

        if (contract != null) {
            contractService.deleteOneContractById(contractId);
            return "redirect:/contracts";
        } else {
            model.addAttribute("error", "Sözleşme bulunamadı");
            return "redirect:/contracts";
        }
    }


    @PostMapping("/update/{contractId}")
    public String updateContract(@PathVariable Long contractId, @ModelAttribute Contract contract) {
        System.out.println("Güncellenen Sözleşme ID: " + contractId);
        // Burada contract nesnesini kullanarak güncelleme işlemini gerçekleştirin
        // Örneğin, bir service üzerinden güncelleme işlemini yapabilirsiniz
        contractService.updateOneContractById(contractId, contract);
        return "redirect:/contracts"; // Başarılı güncelleme sonrası yönlendirme
    }


    @PostMapping("/add-contract")
    public String addContract(@ModelAttribute Contract contract, Model model) {

        boolean success = contractService.addContract(contract);

        // Başarı durumu kontrolü
        if (success) {
            model.addAttribute("success", "Sözleşme başarıyla kaydedildi.");
        } else {
            // Başarı durumu olmadığında logla
            System.out.println("Sözleşme kaydedilirken bir hata oluştu.");
        }

        return "redirect:/contracts";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo" ) int pageNo, Model model){
        int pageSize = 5;
        Page<Contract> page = contractService.findPaginated(pageNo, pageSize);
        List<Contract> contractList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("contracts", contractList);
        return "contracts";
    }
}

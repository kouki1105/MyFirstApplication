package com.example.myfirstapplication.app.company;

import java.util.List;

import com.example.myfirstapplication.entity.Company;
import com.example.myfirstapplication.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@GetMapping
	public String index(Model model) {
		List<Company> companies = companyRepository.findAll();
	
		model.addAttribute("companies", companies);
		model.addAttribute("title", "Company Index");
		return "company/index";
	}

	@GetMapping("new")
    public String newCompany(Model model) {
        model.addAttribute("title", "New Company");
        return "company/new";
    }

    @PostMapping
    public String create(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
	}
	
	@GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id).orElse(null);
        model.addAttribute("company", company);
        model.addAttribute("title", "Edit Company");
        return "company/edit";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute Company company) {
        company.setId(id);
        companyRepository.save(company);
        return "redirect:/companies";
	}
	
	@GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id).orElse(null);
        model.addAttribute("company", company);
        model.addAttribute("title", "Show Company");
        return "company/show";
	}
	
	@DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
        companyRepository.deleteById(id);
		return "redirect:/companies";
	}
}
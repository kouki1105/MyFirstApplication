package com.example.myfirstapplication.app.staff;

import java.util.List;

import com.example.myfirstapplication.entity.Company;
import com.example.myfirstapplication.entity.Staff;
import com.example.myfirstapplication.repository.CompanyRepository;
import com.example.myfirstapplication.repository.StaffRepository;

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
@RequestMapping("/companies/{company_id}/staffs")
public class StaffController {
	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping
	public String index(@PathVariable Long company_id, Model model) {
		List<Staff> staffs = staffRepository.findByCompanyId(company_id);
		Company company = companyRepository.findById(company_id).orElse(null);
		model.addAttribute("staffs", staffs);
		model.addAttribute("company", company);
		model.addAttribute("title", "Staff Index");
		return "staff/index";
	}

    @GetMapping("new")
    public String newStaff(@PathVariable Long company_id, Model model) {
		Company company = companyRepository.findById(company_id).orElse(null);
		model.addAttribute("company", company);
        model.addAttribute("title", "New Staff");
        return "staff/new";
	}

    @PostMapping
    public String create(@PathVariable Long company_id, @ModelAttribute Staff staff) {
		Company company = companyRepository.findById(company_id).orElse(null);
		staff.setCompany(company);
        staffRepository.save(staff);
        return "redirect:/companies/{company_id}/staffs";
	}

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long company_id,@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(company_id).orElse(null);
		Staff staff = staffRepository.findById(id).orElse(null);
		model.addAttribute("staff", staff);
        model.addAttribute("company", company);
        model.addAttribute("title", "Edit Staff");
        return "staff/edit";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long company_id,@PathVariable Long id, @ModelAttribute Staff staff) {
		Company company = companyRepository.findById(company_id).orElse(null);
		staff.setCompany(company);
        staff.setId(id);
        companyRepository.save(company);
        return "redirect:/companies/{company_id}/staffs";
	}

    @GetMapping("{id}")
    public String show(@PathVariable Long company_id,@PathVariable Long id, Model model) {
		Company company = companyRepository.findById(company_id).orElse(null);
		Staff staff = staffRepository.findById(id).orElse(null);
		model.addAttribute("staff", staff);
        model.addAttribute("company", company);
        model.addAttribute("title", "Show Staff");
        return "staff/show";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "redirect:/companies/{company_id}/staffs";
    }
}
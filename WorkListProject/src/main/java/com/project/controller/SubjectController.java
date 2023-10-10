package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.Repository.SubjectDetailRepository;
import com.project.Repository.SubjectRepository;
import com.project.Service.WorkService;
import com.project.table.Subject;
import com.project.table.SubjectDetail;

@Controller
public class SubjectController {
	@Autowired
	private SubjectDetailRepository subjectDetailRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	
	private WorkService workService;
	@Autowired
	  public void setWorkService(WorkService w) {
		  this.workService=w;
	 }
	
	@RequestMapping("/list_subject")
    public String getSubjectList(Model model) {
        System.out.println("Test in subjectList");
        List<Subject> subjectList = workService.getSubjectAll();
        // List<Category> categoryList = inventoryService.getCategoryById(null);
        model.addAttribute("subjectList", subjectList);
        return "subjectList";
    }
	
	@GetMapping("/delete_subject/{id}")
    public String deleteSubject(@PathVariable("id") Integer id, Model model) {
		Subject subject = workService.getSubjectById(id);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
        // id));
		workService.deleteSubject(subject);
        return "redirect:/list_subject";
    }
	
	@PostMapping("/update_subject/{id}")
	public String updateSubject(@PathVariable("id") Integer id, @Validated Subject subject,
	        BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        subject.setIdsubject(id);
	        return "update_subject";
	    }

	    // Retrieve the existing subject by ID
	    Subject existingSubject = workService.getSubjectById(id);

	    // Update properties of the existing subject
	    existingSubject.setName(subject.getName());

	    // Update properties of the associated SubjectDetail
	    SubjectDetail subjectDetail = existingSubject.getSubjectDetail();
	    subjectDetail.setDetail(subject.getDetail());
	    subjectDetail.setMidtermScore(subject.getMid());
	    subjectDetail.setFinalScore(subject.getFinal());
	    subjectDetail.setProjectScore(subject.getProject());
	    subjectDetail.setAssignmentScore(subject.getAssignment());

	    // Save the existing subject, which will cascade to the associated SubjectDetail
	    workService.saveSubject(existingSubject);

	    return "redirect:/list_subject";
	}

	
	@GetMapping("/edit_subject/{id}")
	public String showUpdateSubject(@PathVariable("id") Integer id, Model model) {
	try {
		Subject subject = workService.getSubjectById(id);
		
		model.addAttribute("subject", subject);
		
		// .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
		// id));
		} catch (Exception e) {
		System.err.printf(e.getMessage());
		
		}
		return "update_subject";
	}
	
	@PostMapping("/new_subject")
	public String addSubject(@Validated Subject subject, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "new_subject";
	    }

	    SubjectDetail detail = new SubjectDetail();
	    // Set properties of the SubjectDetail here

	    subject.setSubjectDetail(detail);
	    detail.setSubject(subject);

	    // Save the SubjectDetail first
	    subjectDetailRepository.save(detail);

	    // Then save the Subject
	    subjectRepository.save(subject);

	    return "redirect:/list_subject";
	}


    @GetMapping("/new_subject")
    public String showSubject(Model model) {
    	SubjectDetail detail = new SubjectDetail();
		Subject subject = new Subject();
		subject.setSubjectDetail(detail);
    	//customer.setAddress(address);
        model.addAttribute("subject", subject);

        return "new_subject";
    }
	
    @RequestMapping("/subject_list_json")
    @ResponseBody
    public List<Subject> getCatoryList() {
        List<Subject> subjects = workService.getSubjectAll();
        return subjects;
    }
	
	
	
	
	
	
	
	
//	@RequestMapping("/")
//	public String CustomerForm(Model model) {
//		List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
//		List<SubjectDetail> subjectdetail =(List<SubjectDetail>) subjectDetailRepository.findAll();
//		model.addAttribute("subjects", subjects);
//		model.addAttribute("subjectdetail", subjectdetail);
//		return "index";
//		
//	}
	
	
//	@GetMapping("/add")
//    public String showForm(Model model) {
//		SubjectDetail detail = new SubjectDetail();
//		Subject subject = new Subject();
//		
//		
//		subject.setSubjectDetail(detail);
//    	//customer.setAddress(address);
//        model.addAttribute("subject", subject);
//        return "add";
//	}

//	@PostMapping("/save")
//    public String save(@ModelAttribute("subject") Subject subject) {
//		SubjectDetail detail = new SubjectDetail();
//		detail.setDetail(subject.getDetail());
//		detail.setFinalScore(subject.getFinal());
//		detail.setMidtermScore(subject.getMid());
//		detail.setAssignmentScore(subject.getAssignment());
//		detail.setProjectScore(subject.getProject());
//		
//		
//		subject.setSubjectDetail(detail);
//		detail.setSubject(subject);
//		System.out.println(subject);
//		System.out.println(detail);
//    	subjectRepository.save(subject);
//	    
//        return "saveData";
//    }
	
//	@RequestMapping("/update/{id}")
//    public ModelAndView showUpdate(@PathVariable(name = "id") Integer id){
//    	System.out.print(id);
//    	Customer customer = customerRepository.findById(id).get();
//        ModelAndView mav = new ModelAndView("edit");
//        mav.addObject("customer", customer);
//        return mav;
//    }
//
//    @RequestMapping("/delete/{id}")
//    public String deleteEmployee(@PathVariable(name = "id" ) Integer id){
//    	customerRepository.deleteById(id);
//        System.out.print("Deleted");
//        return "index";
//    }

}

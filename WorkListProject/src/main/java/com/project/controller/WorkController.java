package com.project.controller;


import java.util.*;  
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

import com.project.Service.WorkService;
import com.project.table.Status;
import com.project.table.Subject;
import com.project.table.Type;
import com.project.table.Work;

import java.lang.String;
@Controller // This means that this class is a Controller
public class WorkController {
   
  private WorkService workService;
  @Autowired
  public void setInventoryService(WorkService w) {
	  this.workService=w;
  }
  
  @RequestMapping("/")
  public String root(Model model) {
   
	  
    return "index";
  }
  
 
  @RequestMapping("/list_work")
  public String workForm(Model model) {
   
	  List<Work> work = (List<Work>)workService.getWorktAll();
	  model.addAttribute("work",work);
    return "worktList";
  }
  
  @GetMapping("/delete_work/{id}")
  public String deleteWork(@PathVariable("id") Integer id, Model model) {
    //  Product product = productRepository.findById((int) id)
     //   .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
     // productRepository.delete(product);
	  Work work = workService.getWorkById(id);
	  workService.deleteWork(work);
      return "redirect:/list_work";
  }
  @PostMapping("/update_work/{id}")
  public String updateWork(@PathVariable("id") Integer id, @Validated  Work work, 
    BindingResult result, Model model) {
 //    System.out.println(id); 
	// System.out.println(product.getId()+ product.getName());
	 System.out.println(work);
	if (result.hasErrors()) {
		work.setIdwork(id);
	//	List<Category> categoryList = (List<Category>) categoryRepository.findAll();
	//      model.addAttribute("categorytList", categoryList);     
          return "update_work";
      }
	 
      workService.saveWork(work);
      return "redirect:/list_work";
  }
  @GetMapping("/edit_work/{id}")
  public String showUpdateWork(@PathVariable("id") int id, Model model) {
      Work work = workService.getWorkById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
      
	  List<Work> workList = (List<Work>)workService.getWorktAll();
      System.out.println("in get update_work");
      model.addAttribute("workList",workList);
      model.addAttribute("work", work);
      System.out.println(workList);
      return "update_work";
  }
  
  @PostMapping("/new_work")
  public String addWork(@Validated Work work, BindingResult result, Model model) {
      if (result.hasErrors()) {
    	  List<Work> workList = (List<Work>) workService.getWorktAll();
          model.addAttribute("workList", workList);
          return "new_workt";
      }
     
      workService.saveWork(work);
           return "redirect:/list_work";
  }
  @GetMapping("/new_work")
  public String showWork(Model model) {
      Work work = new Work();
      model.addAttribute("work", work);
       
    
      List<Subject> subjectList = (List<Subject>) workService.getSubjectAll();
      model.addAttribute("subjectList", subjectList);
      
      List<Type> typeList = (List<Type>) workService.getTypeAll();
      model.addAttribute("typeList", typeList);
      
      List<Status> statusList = (List<Status>) workService.getStatusAll();
      model.addAttribute("statusList", statusList);
      
      return "new_work";
  }
  @RequestMapping("/work_list_json") 
	@ResponseBody
	public List<Work> getCatoryList(){
		List<Work> works =  workService.getWorktAll();
		return works;
	}
 
}
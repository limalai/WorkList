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

import com.project.Service.WorkService;
import com.project.table.Status;

@Controller
public class StatusController {
    private WorkService workService;

    @Autowired
    public void setWorkService(WorkService w) {
        this.workService = w;
    }

    @RequestMapping("/list_status")
    public String getStatusList(Model model) {
        System.out.println("Test in cstatusList");
        List<Status> statusList = workService.getStatusAll();
        // List<Category> categoryList = inventoryService.getCategoryById(null);
        model.addAttribute("statusList", statusList);
        return "statusList";
    }

    @GetMapping("/delete_status/{id}")
    public String deleteStatus(@PathVariable("id") Integer id, Model model) {
    	Status status = workService.getStatusById(id);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
        // id));
    	workService.deleteStatus(status);
        return "redirect:/list_status";
    }

    @PostMapping("/update_status/{id}")
    public String updateWork(@PathVariable("id") Integer id, @Validated Status status,
            BindingResult result, Model model) {
        System.out.println(id);
        System.out.println(status.getID() + status.getName());
        if (result.hasErrors()) {
        	status.setID(id);
            return "update_status";
        }

        
        workService.saveStatus(status);
        return "redirect:/list_status";
    }

    @GetMapping("/edit_status/{id}")
    public String showUpdateStatus(@PathVariable("id") Integer id, Model model) {
        try {
        	Status status = workService.getStatusById(id);

            model.addAttribute("status", status);

            // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
            // id));
        } catch (Exception e) {
            System.err.printf(e.getMessage());

        }
        return "update_status";
    }

    @PostMapping("/new_status")
    public String addStatus(@Validated Status status, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "new_status";
        }

        workService.saveStatus(status);
        return "redirect:/list_status";
    }

    @GetMapping("/new_status")
    public String showStatus(Model model) {
    	Status status = new Status();
        model.addAttribute("status", status);

        return "new_status";
    }

    @RequestMapping("/status_list_json")
    @ResponseBody
    public List<Status> getCatoryList() {
        List<Status> status = workService.getStatusAll();
        return status;
    }
}


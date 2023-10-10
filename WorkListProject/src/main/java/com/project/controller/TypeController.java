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
import com.project.table.Type;

@Controller
public class TypeController {
    private WorkService workService;

    @Autowired
    public void setWorkService(WorkService w) {
        this.workService = w;
    }

    @RequestMapping("/list_type")
    public String getCategoryList(Model model) {
        System.out.println("Test in categoryList");
        List<Type> typeList = workService.getTypeAll();
        // List<Category> categoryList = inventoryService.getCategoryById(null);
        model.addAttribute("typeList", typeList);
        return "typeList";
    }

    @GetMapping("/delete_category/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
    	Type type = workService.getTypeById(id);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
        // id));
    	workService.deleteType(type);
        return "redirect:/list_type";
    }

    @PostMapping("/update_type/{id}")
    public String updateWork(@PathVariable("id") Integer id, @Validated Type type,
            BindingResult result, Model model) {
        System.out.println(id);
        System.out.println(type.getID() + type.getName());
        if (result.hasErrors()) {
            type.setID(id);
            return "update_type";
        }

        workService.saveType(type);
        return "redirect:/list_type";
    }

    @GetMapping("/edit_type/{id}")
    public String showUpdateType(@PathVariable("id") Integer id, Model model) {
        try {
            Type type = workService.getTypeById(id);

            model.addAttribute("type", type);

            // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
            // id));
        } catch (Exception e) {
            System.err.printf(e.getMessage());

        }
        return "update_type";
    }

    @PostMapping("/new_type")
    public String addType(@Validated Type type, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "new_type";
        }

        workService.saveType(type);
        return "redirect:/list_type";
    }

    @GetMapping("/new_type")
    public String showType(Model model) {
        Type type = new Type();
        model.addAttribute("type", type);

        return "new_type";
    }

    @RequestMapping("/type_list_json")
    @ResponseBody
    public List<Type> getCatoryList() {
        List<Type> types = workService.getTypeAll();
        return types;
    }
}


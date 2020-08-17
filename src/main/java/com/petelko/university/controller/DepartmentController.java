package com.petelko.university.controller;

import com.petelko.university.model.dto.DepartmentDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.FacultyService;
import com.petelko.university.service.exception.UnitNotEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    private static final String DEPARTMENT_EDIT = "department/department-edit";
    private static final String EDIT_DEPARTMENT = "Edit Department";
    private static final String ADD_NEW_DEPARTMENT = "Add new Department";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_DEPARTMENTS_GET_ALL = "redirect:/departments/getAll";
    private static final String REDIRECT_DEPARTMENTS_INFO = "redirect:/departments/getById/?id=%s";
    private static final String DEPARTMENTS = "departments";
    private static final String DEPARTMENT = "department";
    private static final String FACULTY_SELECT = "facultySelect";
    private static final String HEADER_STRING = "headerString";

    private FacultyService facultyService;
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(FacultyService facultyService,
                                DepartmentService departmentService) {
        this.facultyService = facultyService;
        this.departmentService = departmentService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<DepartmentDTO> departments = departmentService.getAllDTO();
        ModelAndView model = new ModelAndView("department/departments");
        model.addObject(DEPARTMENTS, departments);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            DepartmentDTO department = departmentService.getDTOById(id);
            model.addAttribute(DEPARTMENT, department);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Department with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Department doesn't exist or has been deleted");
        }
        return "department/department-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.delete(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Department deleted successfully");
        } catch (UnitNotEmptyException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Unable to delete Department. Department is not empty");
        }
        return REDIRECT_DEPARTMENTS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        departmentService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Department restored successfully");
        return format(REDIRECT_DEPARTMENTS_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        DepartmentDTO department = new DepartmentDTO();
        String headerString = ADD_NEW_DEPARTMENT;
        if (nonNull(id)) {
            department = departmentService.getDTOById(id);
            headerString = EDIT_DEPARTMENT;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(DEPARTMENT, department);
        model.addAttribute(FACULTY_SELECT, facultyService.getAllActiveDTO());
        return DEPARTMENT_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(DEPARTMENT) DepartmentDTO departmentDto,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = departmentDto.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_DEPARTMENT;
            if (nonNull(id)) {
                headerString = EDIT_DEPARTMENT;
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(FACULTY_SELECT, facultyService.getAllActiveDTO());
            returnQuery = DEPARTMENT_EDIT;
        } else {
            if (nonNull(id)) {
                DepartmentDTO resultDTO = departmentService.update(departmentDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Department updated successfully");
                returnQuery = format(REDIRECT_DEPARTMENTS_INFO, resultDTO.getId());
            } else {
                departmentService.create(departmentDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Department created successfully");
                returnQuery = REDIRECT_DEPARTMENTS_GET_ALL;
            }
        }
        return returnQuery;
    }
}

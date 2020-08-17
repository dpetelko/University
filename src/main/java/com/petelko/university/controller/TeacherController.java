package com.petelko.university.controller;

import com.petelko.university.model.dto.TeacherDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.SubjectService;
import com.petelko.university.service.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {

    private static final String TEACHER_EDIT = "teacher/teacher-edit";
    private static final String EDIT_TEACHER = "Edit Teacher";
    private static final String ADD_NEW_TEACHER = "Add new Teacher";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_TEACHERS_GET_ALL = "redirect:/teachers/getAll";
    private static final String REDIRECT_TEACHERS_INFO = "redirect:/teachers/getById/?id=%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
    private static final String HEADER_STRING = "headerString";
    private static final String DEPARTMENT_SELECT = "departmentSelect";
    private static final String SUBJECT_SELECT = "subjectSelect";

    private DepartmentService departmentService;
    private SubjectService subjectService;
    private TeacherService teacherService;

    @Autowired
    public TeacherController(SubjectService subjectService,
                             DepartmentService departmentService,
                             TeacherService teacherService) {
        this.subjectService = subjectService;
        this.departmentService = departmentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<TeacherDTO> teachers = teacherService.getAllDTO();
        ModelAndView model = new ModelAndView("teacher/teachers");
        model.addObject("teachers", teachers);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            TeacherDTO teacherDTO = teacherService.getDTOById(id);
            model.addAttribute("teacher", teacherDTO);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Teacher with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Teacher doesn't exist or has been deleted");
        }
        return "teacher/teacher-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        teacherService.delete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Teacher deleted successfully");
        return REDIRECT_TEACHERS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        teacherService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Teacher restored successfully");
        return format(REDIRECT_TEACHERS_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        TeacherDTO teacherDTO = new TeacherDTO();
        String headerString = ADD_NEW_TEACHER;
        if (nonNull(id)) {
            teacherDTO = teacherService.getDTOById(id);
            headerString = EDIT_TEACHER;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute("teacher", teacherDTO);
        model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
        model.addAttribute(SUBJECT_SELECT, subjectService.getAllActiveDTO());
        return TEACHER_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = teacherDTO.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_TEACHER;
            if (nonNull(id)) {
                headerString = EDIT_TEACHER;
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
            model.addAttribute(SUBJECT_SELECT, subjectService.getAllActiveDTO());
            returnQuery = TEACHER_EDIT;
        } else {
            if (nonNull(id)) {
                TeacherDTO resultDTO = teacherService.update(teacherDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Teacher updated successfully");
                returnQuery = format(REDIRECT_TEACHERS_INFO, resultDTO.getId());
            } else {
                teacherService.create(teacherDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Teacher created successfully");
                returnQuery = REDIRECT_TEACHERS_GET_ALL;
            }
        }
        return returnQuery;
    }
}

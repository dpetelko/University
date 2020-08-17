package com.petelko.university.controller;

import com.petelko.university.model.dto.StudentDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.GroupService;
import com.petelko.university.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {

    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String REDIRECT_STUDENT_INFO = "redirect:/students/getById/?id=%s";
    private static final String REDIRECT_STUDENTS_GET_ALL = "redirect:/students/getAll";
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private static final String HEADER_STRING = "headerString";
    private static final String STUDENT = "student";
    private static final String GROUP_SELECT = "groupSelect";
    private static final String ERROR_MESSAGE = "errorMessage";

    private StudentService studentService;
    private GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<StudentDTO> students = studentService.getAllDTO();
        ModelAndView model = new ModelAndView("student/students");
        model.addObject("students", students);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            StudentDTO student = studentService.getDTOById(id);
            model.addAttribute(STUDENT, student);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Student with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Student doesn't exist or has been deleted");
        }
        return "student/student-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        studentService.delete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Student deleted successfully");
        return REDIRECT_STUDENTS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        studentService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Student restored successfully");
        return format(REDIRECT_STUDENT_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        StudentDTO student = new StudentDTO();
        String headerString = "Add new Student";
        if (nonNull(id)) {
            student = studentService.getDTOById(id);
            headerString = "Edit Student";
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(STUDENT, student);
        model.addAttribute(GROUP_SELECT, groupService.getAllActiveDTO());
        return "student/student-edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(STUDENT) StudentDTO studentDto,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String redirectQuery;
        Long id = studentDto.getId();
        if (bindingResult.hasErrors()) {
            String headerString = "Add new Student";
            if (nonNull(id)) {
                headerString = "Edit Student";
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(GROUP_SELECT, groupService.getAllActiveDTO());
            redirectQuery = "student/student-edit";
        } else {
            if (nonNull(id)) {
                StudentDTO resultStudentDTO = studentService.update(studentDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Student updated successfully");
                redirectQuery = format(REDIRECT_STUDENT_INFO, resultStudentDTO.getId());
            } else {
                studentService.create(studentDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Student created successfully");
                redirectQuery = REDIRECT_STUDENTS_GET_ALL;
            }
        }
        return redirectQuery;
    }
}

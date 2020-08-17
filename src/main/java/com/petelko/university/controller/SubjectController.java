package com.petelko.university.controller;

import com.petelko.university.model.dto.SubjectDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.SubjectService;
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
@RequestMapping("/subjects")
public class SubjectController {

    private static final String SUBJECT_EDIT = "subject/subject-edit";
    private static final String EDIT_SUBJECT = "Edit Subject";
    private static final String ADD_NEW_SUBJECT = "Add new Subject";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_SUBJECTS_GET_ALL = "redirect:/subjects/getAll";
    private static final String REDIRECT_SUBJECTS_INFO = "redirect:/subjects/getById/?id=%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);
    private static final String HEADER_STRING = "headerString";
    private static final String SUBJECT = "subject";
    private static final String DEPARTMENT_SELECT = "departmentSelect";

    private DepartmentService departmentService;
    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService,
                             DepartmentService departmentService) {
        this.subjectService = subjectService;
        this.departmentService = departmentService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<SubjectDTO> subjects = subjectService.getAllDTO();
        ModelAndView model = new ModelAndView("subject/subjects");
        model.addObject("subjects", subjects);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            SubjectDTO subject = subjectService.getDTOById(id);
            model.addAttribute(SUBJECT, subject);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Subject with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Subject doesn't exist or has been deleted");
        }
        return "subject/subject-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        subjectService.delete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Subject deleted successfully");
        return REDIRECT_SUBJECTS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        subjectService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Subject restored successfully");
        return format(REDIRECT_SUBJECTS_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        SubjectDTO subject = new SubjectDTO();
        String headerString = ADD_NEW_SUBJECT;
        if (nonNull(id)) {
            subject = subjectService.getDTOById(id);
            headerString = EDIT_SUBJECT;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(SUBJECT, subject);
        model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
        return SUBJECT_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(SUBJECT) SubjectDTO subjectDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = subjectDTO.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_SUBJECT;
            if (nonNull(id)) {
                headerString = EDIT_SUBJECT;
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
            returnQuery = SUBJECT_EDIT;
        } else {
            if (nonNull(id)) {
                SubjectDTO resultDTO = subjectService.update(subjectDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Subject updated successfully");
                returnQuery = format(REDIRECT_SUBJECTS_INFO, resultDTO.getId());
            } else {
                subjectService.create(subjectDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Subject created successfully");
                returnQuery = REDIRECT_SUBJECTS_GET_ALL;
            }
        }
        return returnQuery;
    }
}

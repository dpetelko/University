package com.petelko.university.controller;

import com.petelko.university.model.dto.FacultyDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
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
@RequestMapping("/faculties")
public class FacultyController {

    private static final String FACULTY_EDIT = "faculty/faculty-edit";
    private static final String EDIT_FACULTY = "Edit Faculty";
    private static final String ADD_NEW_FACULTY = "Add new Faculty";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_FACULTIES_GET_ALL = "redirect:/faculties/getAll";
    private static final String REDIRECT_FACULTIES_INFO = "redirect:/faculties/getById/?id=%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);
    private static final String FACULTIES = "faculties";
    private static final String FACULTY = "faculty";
    private static final String HEADER_STRING = "headerString";

    private FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<FacultyDTO> faculties = facultyService.getAllDTO();
        ModelAndView model = new ModelAndView("faculty/faculties");
        model.addObject(FACULTIES, faculties);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            FacultyDTO faculty = facultyService.getDTOById(id);
            model.addAttribute(FACULTY, faculty);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Faculty with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Faculty doesn't exist or has been deleted");
        }
        return "faculty/faculty-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            facultyService.delete(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Faculty deleted successfully");
        } catch (UnitNotEmptyException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Unable to delete Faculty. Faculty is not empty");
        }
        return REDIRECT_FACULTIES_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        facultyService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Faculty restored successfully");
        return format(REDIRECT_FACULTIES_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        FacultyDTO faculty = new FacultyDTO();
        String headerString = ADD_NEW_FACULTY;
        if (nonNull(id)) {
            faculty = facultyService.getDTOById(id);
            headerString = EDIT_FACULTY;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(FACULTY, faculty);
        return FACULTY_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(FACULTY) FacultyDTO facultyDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = facultyDTO.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_FACULTY;
            if (nonNull(id)) {
                headerString = EDIT_FACULTY;
            }
            model.addAttribute(HEADER_STRING, headerString);
            returnQuery = FACULTY_EDIT;
        } else {
            if (nonNull(id)) {
                FacultyDTO resultDTO = facultyService.update(facultyDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Faculty updated successfully");
                returnQuery = format(REDIRECT_FACULTIES_INFO, resultDTO.getId());
            } else {
                facultyService.create(facultyDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Faculty created successfully");
                returnQuery = REDIRECT_FACULTIES_GET_ALL;
            }
        }
        return returnQuery;
    }
}

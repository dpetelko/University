package com.petelko.university.controller;

import com.petelko.university.model.dto.LectureDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.LectureService;
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
@RequestMapping("/lectures")
public class LectureController {

    private static final String LECTURE_EDIT = "lecture/lecture-edit";
    private static final String EDIT_LECTURE = "Edit Lecture";
    private static final String ADD_NEW_LECTURE = "Add new Lecture";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_LECTURES_GET_ALL = "redirect:/lectures/getAll";
    private static final String REDIRECT_LECTURES_INFO = "redirect:/lectures/getById/?id=%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(LectureController.class);
    public static final String HEADER_STRING = "headerString";
    public static final String LECTURE = "lecture";

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<LectureDTO> lectures = lectureService.getAllDTO();
        ModelAndView model = new ModelAndView("lecture/lectures");
        model.addObject("lectures", lectures);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            LectureDTO lecture = lectureService.getDTOById(id);
            model.addAttribute(LECTURE, lecture);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lecture with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Lecture doesn't exist or has been deleted");
        }
        return "lecture/lecture-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            lectureService.delete(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lecture deleted successfully");
        } catch (UnitNotEmptyException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Unable to delete Lecture. Lecture is not empty");
        }
        return REDIRECT_LECTURES_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        lectureService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lecture restored successfully");
        return format(REDIRECT_LECTURES_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        LectureDTO lecture = new LectureDTO();
        String headerString = ADD_NEW_LECTURE;
        if (nonNull(id)) {
            lecture = lectureService.getDTOById(id);
            headerString = EDIT_LECTURE;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(LECTURE, lecture);
        return LECTURE_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(LECTURE) LectureDTO lectureDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = lectureDTO.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_LECTURE;
            if ((nonNull(id))) {
                headerString = EDIT_LECTURE;
            }
            model.addAttribute(HEADER_STRING, headerString);
            returnQuery = LECTURE_EDIT;
        } else {
            if ((nonNull(id))) {
                LectureDTO resultDTO = lectureService.update(lectureDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lecture updated successfully");
                returnQuery = format(REDIRECT_LECTURES_INFO, resultDTO.getId());
            } else {
                lectureService.create(lectureDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lecture created successfully");
                returnQuery = REDIRECT_LECTURES_GET_ALL;
            }
        }
        return returnQuery;
    }
}

package com.petelko.university.controller;

import com.petelko.university.controller.dto.LessonFilterDTO;
import com.petelko.university.model.dto.LessonDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.AuditoryService;
import com.petelko.university.service.GroupService;
import com.petelko.university.service.LectureService;
import com.petelko.university.service.LessonService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);
    private static final String LESSON_EDIT = "lesson/lesson-edit";
    private static final String EDIT_LESSON = "Edit Lesson";
    private static final String ADD_NEW_LESSON = "Add new Lesson";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_LESSONS_GET_ALL = "redirect:/lessons/lessons";
    private static final String REDIRECT_LESSONS_INFO = "redirect:/lessons/getById/?id=%s";
    private static final String LESSON_FILTER = "lessonFilter";
    private static final String TEACHER_SELECT = "teacherSelect";
    private static final String GROUP_SELECT = "groupSelect";
    private static final String HEADER_STRING = "headerString";
    private static final String SUBJECT_SELECT = "subjectSelect";
    private static final String AUDITORY_SELECT = "auditorySelect";
    private static final String LECTURE_SELECT = "lectureSelect";


    private AuditoryService auditoryService;
    private LectureService lectureService;
    private SubjectService subjectService;
    private TeacherService teacherService;
    private LessonService lessonService;
    private GroupService groupService;

    @Autowired
    public LessonController(SubjectService subjectService,
                            AuditoryService auditoryService,
                            TeacherService teacherService,
                            LectureService lectureService,
                            LessonService lessonService,
                            GroupService groupService) {
        this.subjectService = subjectService;
        this.auditoryService = auditoryService;
        this.teacherService = teacherService;
        this.lectureService = lectureService;
        this.lessonService = lessonService;
        this.groupService = groupService;
    }

    @GetMapping("/lessons")
    public String getReport(@Valid @ModelAttribute("lessonFilter") LessonFilterDTO lessonFilter,
                            BindingResult bindingResult,
                            Model model) {
        model.addAttribute(TEACHER_SELECT, teacherService.getAllActiveDTO());
        model.addAttribute(GROUP_SELECT, groupService.getAllActiveDTO());
        if (!bindingResult.hasErrors()) {
            if (lessonFilter.isEmpty()) {
                lessonFilter = new LessonFilterDTO(LocalDate.now(), null, null, null);
                model.addAttribute(LESSON_FILTER, lessonFilter);
            }
            List<LessonDTO> report = lessonService.getLessons(lessonFilter);
            if (!report.isEmpty()) {
                model.addAttribute("lessons", report);
                model.addAttribute(SUCCESS_MESSAGE, "Report created successfully!");
            } else {
                model.addAttribute(ERROR_MESSAGE, "Lessons not found! Please, check your request!");
            }
        }
        return "lesson/lessons";
    }

    @GetMapping("/personal-lessons")
    public String getReport(@RequestParam(required = false) Long teacherId,
                             @RequestParam(required = false) Long groupId,
                             RedirectAttributes redirectAttributes) {
        LessonFilterDTO lessonFilterDTO = new LessonFilterDTO(LocalDate.now(), null, groupId, teacherId);
        redirectAttributes.addFlashAttribute(LESSON_FILTER, lessonFilterDTO);
        return REDIRECT_LESSONS_GET_ALL;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            LessonDTO lessonDTO = lessonService.getDTOById(id);
            model.addAttribute("lesson", lessonDTO);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lesson with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Lesson doesn't exist or has been deleted");
        }
        return "lesson/lesson-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        lessonService.delete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lesson deleted successfully");
        return REDIRECT_LESSONS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        lessonService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lesson restored successfully");
        return format(REDIRECT_LESSONS_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        LessonDTO lessonDTO = new LessonDTO();
        String headerString = ADD_NEW_LESSON;
        if (nonNull(id)) {
            lessonDTO = lessonService.getDTOById(id);
            headerString = EDIT_LESSON;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute("lesson", lessonDTO);
        model.addAttribute(TEACHER_SELECT, teacherService.getAllActiveDTO());
        model.addAttribute(SUBJECT_SELECT, subjectService.getAllActiveDTO());
        model.addAttribute(AUDITORY_SELECT, auditoryService.getAllActiveDTO());
        model.addAttribute(LECTURE_SELECT, lectureService.getAllActiveDTO());
        model.addAttribute(GROUP_SELECT, groupService.getAllActiveDTO());
        return LESSON_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("lesson") LessonDTO lessonDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_LESSON;
            if (nonNull(lessonDTO.getId())) {
                headerString = EDIT_LESSON;
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(TEACHER_SELECT, teacherService.getAllActiveDTO());
            model.addAttribute(SUBJECT_SELECT, subjectService.getAllActiveDTO());
            model.addAttribute(AUDITORY_SELECT, auditoryService.getAllActiveDTO());
            model.addAttribute(LECTURE_SELECT, lectureService.getAllActiveDTO());
            model.addAttribute(GROUP_SELECT, groupService.getAllActiveDTO());
            returnQuery = LESSON_EDIT;
        } else {
            if (nonNull(lessonDTO.getId())) {
                LessonDTO resultDTO = lessonService.update(lessonDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lesson updated successfully");
                returnQuery = format(REDIRECT_LESSONS_INFO, resultDTO.getId());
            } else {
                lessonService.create(lessonDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Lesson created successfully");
                returnQuery = REDIRECT_LESSONS_GET_ALL;
            }
        }
        return returnQuery;
    }
}

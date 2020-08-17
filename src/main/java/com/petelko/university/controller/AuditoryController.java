package com.petelko.university.controller;

import com.petelko.university.model.dto.AuditoryDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.AuditoryService;
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
@RequestMapping("/auditories")
public class AuditoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditoryController.class);
    private static final String AUDITORY_EDIT = "auditory/auditory-edit";
    private static final String EDIT_AUDITORY = "Edit Auditory";
    private static final String ADD_NEW_AUDITORY = "Add new Auditory";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_AUDITORIES_GET_ALL = "redirect:/auditories/getAll";
    private static final String REDIRECT_AUDITORIES_INFO = "redirect:/auditories/getById/?id=%s";
    private static final String AUDITORIES = "auditories";
    private static final String AUDITORY = "auditory";
    private static final String HEADER_STRING = "headerString";

    private AuditoryService auditoryService;

    @Autowired
    public AuditoryController(AuditoryService auditoryService) {
        this.auditoryService = auditoryService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<AuditoryDTO> auditories = auditoryService.getAllDTO();
        ModelAndView model = new ModelAndView("auditory/auditories");
        model.addObject(AUDITORIES, auditories);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            AuditoryDTO auditory = auditoryService.getDTOById(id);
            model.addAttribute(AUDITORY, auditory);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Auditory with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Auditory doesn't exist or has been deleted");
        }
        return "auditory/auditory-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            auditoryService.delete(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Auditory deleted successfully");
        } catch (UnitNotEmptyException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Unable to delete Auditory. Auditory is not empty");
        }
        return REDIRECT_AUDITORIES_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        auditoryService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Auditory restored successfully");
        return format(REDIRECT_AUDITORIES_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        AuditoryDTO auditory = new AuditoryDTO();
        String headerString = ADD_NEW_AUDITORY;
        if (nonNull(id)) {
            auditory = auditoryService.getDTOById(id);
            headerString = EDIT_AUDITORY;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(AUDITORY, auditory);
        return AUDITORY_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(AUDITORY) AuditoryDTO auditoryDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = auditoryDTO.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_AUDITORY;
            if (nonNull(id)) {
                headerString = EDIT_AUDITORY;
            }
            model.addAttribute(HEADER_STRING, headerString);
            returnQuery = AUDITORY_EDIT;
        } else {
            if (nonNull(id)) {
                AuditoryDTO resultDTO = auditoryService.update(auditoryDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Auditory updated successfully");
                returnQuery = format(REDIRECT_AUDITORIES_INFO, resultDTO.getId());
            } else {
                auditoryService.create(auditoryDTO);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Auditory created successfully");
                returnQuery = REDIRECT_AUDITORIES_GET_ALL;
            }
        }
        return returnQuery;
    }
}

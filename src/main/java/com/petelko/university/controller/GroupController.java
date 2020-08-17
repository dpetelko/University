package com.petelko.university.controller;

import com.petelko.university.model.dto.GroupDTO;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.service.DepartmentService;
import com.petelko.university.service.GroupService;
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
@RequestMapping("/groups")
public class GroupController {

    private static final String GROUP_EDIT = "group/group-edit";
    private static final String EDIT_GROUP = "Edit Group";
    private static final String ADD_NEW_GROUP = "Add new Group";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REDIRECT_GROUPS_GET_ALL = "redirect:/groups/getAll";
    private static final String REDIRECT_GROUP_INFO = "redirect:/groups/getById/?id=%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
    private static final String HEADER_STRING = "headerString";
    private static final String DEPARTMENT_SELECT = "departmentSelect";
    private static final String GROUP = "group";
    private static final String GROUPS = "groups";

    private GroupService groupService;
    private DepartmentService departmentService;

    @Autowired
    public GroupController(GroupService groupService,
                           DepartmentService departmentService) {
        this.groupService = groupService;
        this.departmentService = departmentService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll() {
        List<GroupDTO> groups = groupService.getAllDTO();
        ModelAndView model = new ModelAndView("group/groups");
        model.addObject(GROUPS, groups);
        return model;
    }

    @GetMapping("/getById")
    public String getById(Long id, Model model) {
        try {
            GroupDTO group = groupService.getDTOById(id);
            model.addAttribute(GROUP, group);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Group with ID '{}' not found", id);
            model.addAttribute(ERROR_MESSAGE, "Sorry, Group doesn't exist or has been deleted");
        }
        return "group/group-info";
    }

    @GetMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            groupService.delete(id);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Group deleted successfully");
        } catch (UnitNotEmptyException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Unable to delete Group. Group is not empty");
        }
        return REDIRECT_GROUPS_GET_ALL;
    }

    @GetMapping("/undelete")
    public String undelete(Long id, RedirectAttributes redirectAttributes) {
        groupService.undelete(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Group restored successfully");
        return format(REDIRECT_GROUP_INFO, id);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        GroupDTO group = new GroupDTO();
        String headerString = ADD_NEW_GROUP;
        if ((nonNull(id))) {
            group = groupService.getDTOById(id);
            headerString = EDIT_GROUP;
        }
        model.addAttribute(HEADER_STRING, headerString);
        model.addAttribute(GROUP, group);
        model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
        return GROUP_EDIT;
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(GROUP) GroupDTO groupDto,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        String returnQuery;
        Long id = groupDto.getId();
        if (bindingResult.hasErrors()) {
            String headerString = ADD_NEW_GROUP;
            if ((nonNull(id))) {
                headerString = EDIT_GROUP;
            }
            model.addAttribute(HEADER_STRING, headerString);
            model.addAttribute(DEPARTMENT_SELECT, departmentService.getAllActiveDTO());
            returnQuery = GROUP_EDIT;
        } else {
            if ((nonNull(id))) {
                GroupDTO resultGroupDTO = groupService.update(groupDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Group updated successfully");
                returnQuery = format(REDIRECT_GROUP_INFO, resultGroupDTO.getId());
            } else {
                groupService.create(groupDto);
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Group created successfully");
                returnQuery = REDIRECT_GROUPS_GET_ALL;
            }
        }
        return returnQuery;
    }
}

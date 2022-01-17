package ru.balkonsky.springbootmvc.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.balkonsky.springbootmvc.model.Role;
import ru.balkonsky.springbootmvc.model.User;
import ru.balkonsky.springbootmvc.service.RoleService;
import ru.balkonsky.springbootmvc.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", user);
        return "/index";
    }

    @PostMapping()
    public String create (@ModelAttribute("user") User user,
                          @RequestParam(required = false, value = "listRoleNames") List<String> listRoleNames) {
        Set<Role> roles = new HashSet<>();
        if (listRoleNames != null) {
            roles = roleService.getSetOfListRoles(listRoleNames);
        }else {
            roles.add(roleService.getRoleByRoleName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @PatchMapping("/user_{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id,
                         @RequestParam(required = false, value = "listRoleNames") List<String> listRoleNames) {

        Set<Role> roles = new HashSet<>();
        if (listRoleNames != null) {
            roles = roleService.getSetOfListRoles(listRoleNames);
        }else {
            roles.add(roleService.getRoleByRoleName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.updateUser(user, id);

        return "redirect:/admin";
    }

    @DeleteMapping("/user_{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

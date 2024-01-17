package org.sayres.springmvc.controllers;

import org.sayres.springmvc.dao.ParticipationDAO;
import org.sayres.springmvc.models.Participation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aliaksei Karabelnikau
 */
@Controller
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationDAO participationDAO;

    public ParticipationController(ParticipationDAO participationDAO) {
        this.participationDAO = participationDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("participation", participationDAO.index());
        return "participation/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("participation", participationDAO.show(id));
        return "participation/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("participation") Participation participation, @PathVariable("id") int id) {
        participationDAO.update(id, participation);
        return "redirect:/participation";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("participation") Participation participation) {
        return "participation/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("participation") Participation participation) {
        participationDAO.save(participation);
        return "redirect:/participation";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        participationDAO.delete(id);
        return "redirect:/participation";
    }
}

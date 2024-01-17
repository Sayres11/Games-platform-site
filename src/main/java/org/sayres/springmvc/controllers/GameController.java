package org.sayres.springmvc.controllers;

import org.sayres.springmvc.dao.GameDAO;
import org.sayres.springmvc.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aliaksei Karabelnikau
 */
@Controller
@RequestMapping("/games")
public class GameController {

    private final GameDAO gameDAO;

    @Autowired
    public GameController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("game", gameDAO.index());
        return "GameControl/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("game") Game game) {
        return "GameControl/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("game") Game game) {
        gameDAO.save(game);
        return "redirect:/games";
    }


    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("game", gameDAO.show(id));
        return "GameControl/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("game") Game game, @PathVariable("id") int id) {
        gameDAO.update(id, game);
        return "redirect:/games";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        gameDAO.delete(id);
        return "redirect:/games";
    }


}

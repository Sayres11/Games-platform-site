package org.sayres.springmvc.controllers;


import org.sayres.springmvc.dao.GameDAO;
import org.sayres.springmvc.dao.ParticipationDAO;
import org.sayres.springmvc.dao.UserDAO;
import org.sayres.springmvc.models.Game;
import org.sayres.springmvc.models.Participation;
import org.sayres.springmvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei Karabelnikau
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private final UserDAO userDAO;
    private final ParticipationDAO participationDAO;

    private final GameDAO gameDAO;

    @Autowired
    public HomeController(UserDAO userDAO, ParticipationDAO participationDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.participationDAO = participationDAO;
        this.gameDAO = gameDAO;
    }

    @GetMapping()
    public String index(Model model) {
        List<User> users = new ArrayList<>();
        List<Game> games = new ArrayList<>();
        List<Participation> participations = participationDAO.bestPlayersInGames();
        for (Participation participation : participations) {
            User user = userDAO.show(participation.getPlayer_id());
            Game game = gameDAO.show(participation.getGame_id());
            if (user != null && game != null) {
                users.add(user);
                games.add(game);
            }
        }
        model.addAttribute("user", users);
        model.addAttribute("game", games);
        model.addAttribute("part", participations);
        return "index";
    }



    @GetMapping("/cs")
    public String cs(Model model) {
        List<User> users = new ArrayList<>();
        List<Participation> participations = participationDAO.findTop5ByGameId(1);
        for (Participation participation : participations) {
            User user = userDAO.show(participation.getPlayer_id());
            if (user != null) {
                users.add(user);
            }
        }
        model.addAttribute("player", users);
        return "cs/maincs";
    }

    @GetMapping("/dota")
    public String dota(Model model) {
        model.addAttribute("user", userDAO.index());
        return "dota/main";
    }

    @GetMapping("/valorant")
    public String valorant(Model model) {
        List<User> users = new ArrayList<>();
        List<Participation> participations = participationDAO.findTop5ByGameId(3);
        for (Participation participation : participations) {
            User user = userDAO.show(participation.getPlayer_id());
            if (user != null) {
                users.add(user);
            }
        }
        model.addAttribute("player", users);
        return "valorant/main";
    }

    @GetMapping("/lol")
    public String lol(Model model) {
        List<User> users = new ArrayList<>();
        List<Participation> participations = participationDAO.findTop5ByGameId(4);
        for (Participation participation : participations) {
            User user = userDAO.show(participation.getPlayer_id());
            if (user != null) {
                users.add(user);
            }
        }
        model.addAttribute("player", users);
        return "lol/main";
    }

    @GetMapping("/servers")
    public String servers(Model model) {
        model.addAttribute("user", userDAO.index());
        return "servers/main";
    }

    @GetMapping("/support")
    public String support(Model model) {
        model.addAttribute("user", userDAO.index());
        return "support/main";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("user", userDAO.index());
        return "stats/main";
    }
}

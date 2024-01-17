package org.sayres.springmvc.controllers;

import org.sayres.springmvc.dao.GameDAO;
import org.sayres.springmvc.dao.LinkedAccountDAO;
import org.sayres.springmvc.dao.ParticipationDAO;
import org.sayres.springmvc.dao.UserDAO;
import org.sayres.springmvc.models.Game;
import org.sayres.springmvc.models.Participation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aliaksei Karabelnikau
 */
@Controller
@RequestMapping("/detailed_info")
public class DetailedInfoController {
    private final GameDAO gameDAO;
    private final ParticipationDAO participationDAO;
    private final UserDAO userDAO;

    private final LinkedAccountDAO linkedAccountDAO;

    public DetailedInfoController(GameDAO gameDAO, ParticipationDAO participationDAO, UserDAO userDAO, LinkedAccountDAO linkedAccountDAO) {
        this.gameDAO = gameDAO;
        this.participationDAO = participationDAO;
        this.userDAO = userDAO;
        this.linkedAccountDAO = linkedAccountDAO;
    }


/*    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("participation", participationDAO.show(id));
        int playerId = participationDAO.show(id).getPlayer_id();
        model.addAttribute("user", userDAO.show(playerId));
        model.addAttribute("games", gameDAO.findAllByPlayerId(playerId));
        return "detailed_info/index";
    }*/

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        Participation participation = participationDAO.show(id);
        model.addAttribute("participation", participation);
        int playerId = participation.getPlayer_id();
        model.addAttribute("user", userDAO.show(playerId));
        List<Game> games = gameDAO.findAllByPlayerId(playerId);
        model.addAttribute("games", games);
        Map<Integer, Game> gameMap = new HashMap<>();
        for (Game game : games) {
            gameMap.put(game.getGame_id(), game);
        }
        model.addAttribute("gameMap", gameMap);
        model.addAttribute("participations", participationDAO.findAllByPlayerId(playerId));
        model.addAttribute("linkedAccount", linkedAccountDAO.show(playerId));
        return "detailed_info/index";
    }
}

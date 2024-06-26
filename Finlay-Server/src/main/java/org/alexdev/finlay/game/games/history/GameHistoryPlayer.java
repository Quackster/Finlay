package org.alexdev.finlay.game.games.history;

import org.alexdev.finlay.dao.mysql.GameDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;

public class GameHistoryPlayer {
    private int score;
    private int teamId;
    private int userId;
    private String username;

    public GameHistoryPlayer(int score, int teamId, int userId) {
        this.score = score;
        this.teamId = teamId;
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        if (this.username == null) {
            this.username = PlayerDao.getName(this.userId);
        }

        return username;
    }
}

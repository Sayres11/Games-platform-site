package org.sayres.springmvc.models;

/**
 * @author Aliaksei Karabelnikau
 */
public class LinkedAccount {
    private int id;
    private int player_id;
    private String account_details;

    public LinkedAccount() {
    }

    public LinkedAccount(int id, int player_id, String account_details) {
        this.id = id;
        this.player_id = player_id;
        this.account_details = account_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getAccount_details() {
        return account_details;
    }

    public void setAccount_details(String account_details) {
        this.account_details = account_details;
    }
}

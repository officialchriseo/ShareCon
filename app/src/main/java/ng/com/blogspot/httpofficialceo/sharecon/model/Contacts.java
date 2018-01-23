package ng.com.blogspot.httpofficialceo.sharecon.model;

/**
 * Created by official on 12/26/17.
 */

public class Contacts {

    private int id;
    private String name;
    private String phone;
    private String picture;
    private int color = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

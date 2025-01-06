package data.database;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private long salary;
    private long price;
    private String imgSource;
    private boolean isInTransferList;

    public  Player(String name, String country, int age, double height, String position, String club, Integer number, long weeklySalary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.number = number;
        this.salary = weeklySalary;
    }
    public Player(String[] data) {
        this.name = data[0];
        this.country = data[1];
        this.age = Integer.parseInt(data[2]);
        this.height = Double.parseDouble(data[3]);
        this.club = data[4];
        this.position = data[5];
        this.number = (data[6].isEmpty() || data[6].equals("null"))? null : Integer.parseInt(data[6]);
        this.salary = Integer.parseInt(data[7]);
    }

    public Player() {

        imgSource = "/images/player/unknown.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setImgSource("/images/logo/unknown.png");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        try {
            new Image(getClass().getResourceAsStream(imgSource));
            this.imgSource = imgSource;
        } catch (Exception e) {
            this.imgSource = "/images/player/unknown.png";
        }
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isInTransferList() {
        return isInTransferList;
    }

    public void setInTransferList(boolean inTransferList) {
        isInTransferList = inTransferList;
    }

    @Override
    public String toString(){
        return name + "," + country + "," + age + "," + height + "," + club + "," + position + "," + number + "," + salary;
    }
}

package edu.xcu.easykeep.bean;

public class BillBean {
    private int id;//账单id
    private String name;//账单类型，饮食、旅游...该类型会有专门的实体类。
    private String note;//备注
    private float money;//价格
    private String time;//时钟和分钟字符串
    private int year, month, day;
    private int kind;//类型，收入1、支出-1、不计入收支0

    public BillBean(int id, String name, String note, float money, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}

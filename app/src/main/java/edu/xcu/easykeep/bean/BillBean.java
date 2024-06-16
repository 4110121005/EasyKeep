package edu.xcu.easykeep.bean;

/**
 * 账单数据模型，用于存储账单相关信息（不包含用户 uid）。
 */
public class BillBean {

    /**
     * 账单 ID。
     */
    private int id;

    /**
     * 账单类型，例如饮食、旅游等。
     */
    private String name;

    /**
     * 账单备注信息。
     */
    private String note;

    /**
     * 账单金额。
     */
    private float money;

    /**
     * 账单时间，格式为 "HH:mm" 的字符串。
     */
    private String time;

    /**
     * 账单年份。
     */
    private int year;

    /**
     * 账单月份。
     */
    private int month;

    /**
     * 账单日期。
     */
    private int day;

    /**
     * 账单类型，1 表示收入，-1 表示支出。
     */
    private int kind;

    /**
     * 无参构造函数。
     */
    public BillBean(){

    }

    /**
     * 构造函数，用于创建 BillBean 对象。
     *
     * @param id    账单 ID
     * @param name  账单类型
     * @param note  账单备注
     * @param money 账单金额
     * @param time  账单时间
     * @param year  账单年份
     * @param month 账单月份
     * @param day   账单日期
     * @param kind  账单类型
     */
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

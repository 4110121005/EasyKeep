package edu.xcu.easykeep.bean;

/**
 * 账单类型数据模型，用于存储账单类型相关信息。
 */
public class TypeBean {

    /**
     * 类型编号。
     */
    private int id;

    /**
     * 类型名称，例如 "餐饮"、"购物" 等。
     */
    private String name;

    /**
     * 该类型对应的默认图片资源 ID。
     */
    private int imageId;

    /**
     * 选中状态下显示的图片资源 ID。
     */
    private int selected;

    /**
     * 类型所属的账单类型，1 表示收入，-1 表示支出。
     */
    private int kind;

    /**
     * 构造函数，用于创建 TypeBean 对象。
     *
     * @param id       类型编号
     * @param name     类型名称
     * @param imageId  默认图片资源 ID
     * @param selected 选中状态下显示的图片资源 ID
     * @param kind     类型所属的账单类型
     */
    public TypeBean(int id, String name, int imageId, int selected, int kind) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.selected = selected;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}

package edu.xcu.easykeep.bean;

public class TypeBean {
    private int id;//类型编号
    private String name;//类型名称
    private int imageId;//该类型对应的图片 id，该 id 就是图片的资源 id
    private int selected;//选中后，图片的 id
    private int kind;//表示该类型属于收入还是支出，或是不计入收支

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

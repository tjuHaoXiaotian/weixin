package cn.edu.tju.scs.menu;

/**
 * 按钮
 * Created by jack on 2016/3/2.
 */
public class Button {
    private String type;
    private String name;
    private Button [] sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}

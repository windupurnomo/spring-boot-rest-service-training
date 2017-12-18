package id.co.angkasapura2.enums;

public enum RoleEnum {

    ADMIN(0), AIRLINE(1), CUSTOMER(2);

    private int value;

    RoleEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RoleEnum parse(int id) {
        RoleEnum status = null;
        for (RoleEnum item : RoleEnum.values()) {
            if (item.getValue() == id) {
                status = item;
                break;
            }
        }
        return status;
    }
}
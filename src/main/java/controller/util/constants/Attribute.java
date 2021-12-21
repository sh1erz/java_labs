package controller.util.constants;

public enum Attribute {
    USER_ADMIN("userAdmin"),
    USER_DOCTOR("userDoctor"),
    DOCTORS("doctors"),
    PATIENTS("patients"),
    DOC_SPECIALISATION("specialisations");


    private final String attribute;

    Attribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
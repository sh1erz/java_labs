package controller.util.constants;

public enum Page {
    INDEX("/index.jsp"),
    INVALID_LOGIN("/invalid_login"),
    INVALID_PERSON("/invalid_person"),
    DOCTOR_MAIN("/doctor/doctor_main"),
    ADMIN_MAIN("/admin/admin_main"),
    PATIENT("/doctor/patient");


    private final String page;

    Page(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
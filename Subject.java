public enum Subject {

    PROGRAMMING("Programming"),
    DATA_STRUCTURES("Data Structures"),
    DISCRETE_MATH("Discrete Math"),
    SOFTWARE_ENGINEERING("Software Engineering"),
    COMPUTER_SCIENCE("Computer Science"),
    MISCELLANEOUS("Miscellaneous");

    private String displayName;

    Subject(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
package be.calc.io;

public enum ArgumentType {
    CALC_EXPRESSION(),
    LOG_LEVEL("-l");

    private String argName;

    ArgumentType() {
        this.argName = null;
    }

    ArgumentType(String argName) {
        this.argName = argName;
    }

    public String getArgName(){
        return argName;
    }
}

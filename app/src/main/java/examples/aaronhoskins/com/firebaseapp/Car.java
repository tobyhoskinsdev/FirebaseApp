package examples.aaronhoskins.com.firebaseapp;

public class Car {
    private String wheels;
    private String engine;
    private String transmission;
    private String make;
    private String modle;

    public Car() {
    }

    public Car(String wheels, String engine, String transmission, String make, String modle) {
        this.wheels = wheels;
        this.engine = engine;
        this.transmission = transmission;
        this.make = make;
        this.modle = modle;
    }

    public String getWheels() {
        return wheels;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModle() {
        return modle;
    }

    public void setModle(String modle) {
        this.modle = modle;
    }
}

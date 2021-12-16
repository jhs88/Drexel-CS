public class Radio {
    private String currentRadioStation;
    protected int volume;
    protected boolean isRadioOn;

    public Radio(String crs) {
        this.currentRadioStation = crs;
    }

    public void setRadioStation(String rs) {
        currentRadioStation = rs;
    }

    public String getRadioStation() {
        return currentRadioStation;
    }

    public void setVolume(int v) {
        volume = v;
    }

    public int getVolume() {
        return volume;
    }

    public boolean isRadioOn() {
        return isRadioOn;
    }

    public void turnRadioOn() {
        System.out.println("Radio was Turned On");
        isRadioOn = true;
    }

    public void turnRadioOff() {
        System.out.println("Radio was Turned Off");
        isRadioOn = false;
    }
}

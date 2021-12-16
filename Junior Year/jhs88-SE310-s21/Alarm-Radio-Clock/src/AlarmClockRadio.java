public class AlarmClockRadio{
    private final Radio radio;
    private final AlarmClock alarmClock;

    public AlarmClockRadio(int sh, int sm, int ss, String s12, int ah, int am, int as, String a12, String radio) {
        this.alarmClock = new AlarmClock(sh, sm, ss, s12, ah, am, as, a12);
        this.radio = new Radio(radio);
    }

    public void setRadioStation(String s) {
        radio.setRadioStation(s);
    }

    public String getRadioStation() {
        return radio.getRadioStation();
    }

    public boolean isRadioOn() {
        return radio.isRadioOn();
    }

    public void setVolume(int v) {
        radio.setVolume(v);
    }

    public int getVolume() {
        return radio.getVolume();
    }

    public void turnRadioOn() {
        radio.turnRadioOn();
    }

    public void turnRadioOff() {
        radio.turnRadioOff();
    }

    public String getCurrentTime() {
        return alarmClock.getCurrentTime();
    }

    public void setCurrentTime(int h, int m, int s) {
        alarmClock.setCurrentTime(h, m, s);
    }

    public void tick() {
        alarmClock.tick();
    }

    public String getAlarm() {
        return alarmClock.getAlarm();
    }

    public void setAlarm(int h, int m, int s) {
        alarmClock.setAlarmTime(h, m, s);
    }

    public void turnAlarmOn() {
        alarmClock.turnAlarmOn();
    }

    public void turnAlarmOff() {
        alarmClock.turnAlarmOff();
    }

    public void snooze() {
        alarmClock.snooze();
    }

    public void checkAlarm() {
        alarmClock.checkAlarm();
//        System.out.print(radio.getRadioStation());
//        Here I want to print radio station,
//        but for some reason it prints the
//        value of currentStation thousands of times.
//        I'm submitting this late because I've been
//        trying to make this work. It does not make
//        any sense that it should print this many times:
//        Snooze Was Pressed
//        8:6 AM
//        1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM8:7 AM
//        1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM8:8 AM
//        1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM8:9 AM
//        1060 AM1060 AM1060 AM1060 AM1060 AM1060 AM1060 ...
    }

}

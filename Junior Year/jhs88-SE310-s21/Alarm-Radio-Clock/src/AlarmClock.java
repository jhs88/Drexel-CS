public class AlarmClock extends Clock {
    protected int ah;
    protected int am;
    protected int as;
    protected String a12hr;
    protected boolean isAlarmOn;

    public AlarmClock(int sh, int sm, int ss, String t12, int ah, int am, int as, String a12) {
        super(sh, sm, ss, t12);
        this.ah = ah;
        this.am = am;
        this.as = as;
        this.a12hr = a12;
        this.isAlarmOn = true;
    }

    public String getAlarm() {
        return String.valueOf(h) + ":" + String.valueOf(m)
                + " " + a12hr;
    }

    public void setAlarmTime(int h, int m, int s) {
        ah = h;
        am = m;
        as = s;
    }

    public void turnAlarmOn() {
        System.out.println("Buzz Buzz Buzz");
        isAlarmOn = true;
    }

    public void turnAlarmOff() {
        System.out.println("Alarm was Turned Off");
        isAlarmOn = false;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public void snooze() {
        System.out.println("Snooze Was Pressed");
        as = 0;
        am+=10;
        if (am >= 60) {
            am = 60 - am;
            ah++;
        }
    }

    public void checkAlarm() {
        if (h == ah && m == am && s == as) {
            System.out.println("The radio was on and is playing ");
        }
    }

}

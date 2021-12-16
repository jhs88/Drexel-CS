public class Clock {
    protected int h;
    protected int m;
    protected int s;
    protected String t12hr;

    public Clock(int h, int m, int s, String t12) {
        this.h = h;
        this.m = m;
        this.s = s;
        this.t12hr = t12;
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }

    public String getCurrentTime() {
        return String.valueOf(h) + ":" + String.valueOf(m) + ""
                + " " + t12hr;
    }

    public void setCurrentTime(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public void tick() {
        s++;
        if (s >= 60) {
            s = 60 - s;
            m++;
            if (m >= 60) {
                m = 60 - s;
                h++;
            }
        }
    }
}

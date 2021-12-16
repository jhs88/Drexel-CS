public class Main {
    public static void main(String[] args) {
        int i;
        int seconds;
        AlarmClockRadio myClock = new AlarmClockRadio(8, 0, 0, "AM", 8, 5, 0,"AM", "1060 AM");

        System.out.println("Initial Time: " + myClock.getCurrentTime()
                + "\nThe radio was turned on and is playing " + myClock.getRadioStation());
        for (i = 0; i <= 5; i++) {
            System.out.println(myClock.getCurrentTime());
            for (seconds = 0; seconds < 60; seconds++) {
                myClock.checkAlarm();
                myClock.tick();
            }
        }
        myClock.snooze();
        for (i = 0; i < 10; i++) {
            System.out.println(myClock.getCurrentTime());
            for (seconds = 0; seconds < 60; seconds++) {
                myClock.checkAlarm();
                myClock.tick();
            }
        }
        myClock.turnAlarmOff();
    }
}

package nesne.proje;

import nesne.proje.pojo.User;

public class AgArayuz implements IAgArayuz {

    private ISmartDevice mSmartDevice;
    private volatile int sicaklik = -1;
    private User mUser;

    public AgArayuz(ISmartDevice smartDevice, User user) {
        mSmartDevice = smartDevice;
        smartDevice.addObserver((SmartDevice.StateObserver)this);
        smartDevice.addObserver((SmartDevice.SicaklikObserver)this);
        mUser = user;
    }

    @Override
    public void sougtucuAc() {
        mSmartDevice.sougtucuAc();
    }

    @Override
    public void sougtucuKapat() {
        mSmartDevice.sougtucuKapat();
    }

    @Override
    public int sicaklikGonder() {
        return sicaklik;
    }

    @Override
    public void changePassword(String oldPassword) {

    }

    @Override
    public void onStateChange(SmartDevice.State oldState, SmartDevice.State newState) {
        if (newState == SmartDevice.State.ACIK || newState == SmartDevice.State.KAPALI)
            System.out.println("Sogutucunun Durumu: " + newState);
    }

    @Override
    public void onSicaklikChange(int newValue) {
        sicaklik = newValue;
    }
}

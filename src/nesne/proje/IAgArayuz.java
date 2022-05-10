package nesne.proje;

public interface IAgArayuz extends SmartDevice.StateObserver, SmartDevice.SicaklikObserver {
    void sougtucuAc();

    void sougtucuKapat();

    int sicaklikGonder();

    void changePassword(String oldPassword);
}

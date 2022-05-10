package nesne.proje;

public interface SicaklikPublisher {
    void addObserver(SmartDevice.SicaklikObserver observer);

    void removeObserver(SmartDevice.SicaklikObserver observer);

    void notify(int newValue);

}

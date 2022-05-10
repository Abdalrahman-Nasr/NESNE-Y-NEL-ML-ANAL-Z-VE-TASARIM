package nesne.proje;

public interface StatePublisher{

    void addObserver(SmartDevice.StateObserver observer);

    void removeObserver(SmartDevice.StateObserver observer);

    void notify(SmartDevice.State data);

}

package nesne.proje;

public interface ISmartDevice extends IEyleyici,
        StatePublisher, SicaklikPublisher {
    void startSicaklikAlgilayici();
    void stopSicaklikAlgilayici();
    void sicaklikOku();
}

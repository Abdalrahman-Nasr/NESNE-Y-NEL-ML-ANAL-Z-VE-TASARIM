package nesne.proje;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SmartDevice implements ISmartDevice{
    private ScheduledExecutorService clock;
    private IEyleyici eyleyici;
    private ISicaklikAlgilayici sicaklikAlgilayici;
    private volatile State state;
    private final List<StateObserver> stateObservers = Collections.synchronizedList(new ArrayList<>());
    private final List<SicaklikObserver> sicaklikObservers = Collections.synchronizedList(new ArrayList<>());

    private SmartDevice(IEyleyici eyleyici, ISicaklikAlgilayici sicaklikAlgilayici) {
        this.eyleyici = eyleyici;
        this.sicaklikAlgilayici = sicaklikAlgilayici;
    }

    @Override
    public void sougtucuAc() {
        if (state == State.ISLEM_YAPILIYOR)
            return;
        setState(State.ISLEM_YAPILIYOR);
        eyleyici.sougtucuAc();
        startSicaklikAlgilayici();
        setState(State.ACIK);
    }

    @Override
    public void sougtucuKapat() {
        if (state == State.ISLEM_YAPILIYOR)
            return;
        setState(State.ISLEM_YAPILIYOR);
        eyleyici.sougtucuKapat();
        stopSicaklikAlgilayici();
        setState(State.KAPALI);
    }

    @Override
    public void sicaklikOku() {
        if (state == State.ISLEM_YAPILIYOR)
            return;
        int result = sicaklikAlgilayici.sicaklikOku();
        notify(result);
        if (clock != null && !clock.isShutdown())
            clock.schedule(this::sicaklikOku, 200, TimeUnit.MILLISECONDS);
    }

    private synchronized void setState(State state) {
        notify(state);
        this.state = state;
    }

    @Override
    public void startSicaklikAlgilayici() {
        clock = Executors.newSingleThreadScheduledExecutor();
        clock.schedule(this::sicaklikOku, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stopSicaklikAlgilayici() {
        if (clock != null)
            clock.shutdown();
    }

    @Override
    public void addObserver(SicaklikObserver observer) {
        sicaklikObservers.add(observer);
    }

    @Override
    public void removeObserver(SicaklikObserver observer) {
        sicaklikObservers.remove(observer);
    }

    @Override
    public void notify(int newValue) {
        for (SicaklikObserver ob : sicaklikObservers) {
            ob.onSicaklikChange(newValue);
        }
    }

    @Override
    public void addObserver(StateObserver observer) {
        stateObservers.add(observer);

    }

    @Override
    public void removeObserver(StateObserver observer) {
        stateObservers.remove(observer);

    }

    @Override
    public void notify(State data) {
        for (StateObserver ob : stateObservers) {
            ob.onStateChange(state, data);
        }
    }

    public static class Builder {
        private IEyleyici mEyleyici;
        private ISicaklikAlgilayici mSicaklikAlgilayici;
        public Builder addEyleyici(IEyleyici eyleyici) {
            mEyleyici = eyleyici;
            return this;
        }

        public Builder addSicaklikAlgilayici(ISicaklikAlgilayici sicaklikAlgilayici) {
            mSicaklikAlgilayici = sicaklikAlgilayici;
            return this;
        }

        public SmartDevice build() {
            if (mEyleyici == null || mSicaklikAlgilayici == null)
                throw new IllegalStateException("Smart device needs both the actuator and the sensor");
            return new SmartDevice(mEyleyici, mSicaklikAlgilayici);
        }
    }

    public  enum State {
        KAPALI,
        ACIK,
        BEKLIYOR,
        ALGILIYOR,
        SERVIS_DISI,
        ISLEM_YAPILIYOR
    }

    public interface StateObserver {
        void onStateChange(State oldState, State newState);
    }

    public interface SicaklikObserver{
        void onSicaklikChange(int newValue);
    }
}

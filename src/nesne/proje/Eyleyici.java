package nesne.proje;

public class Eyleyici implements IEyleyici{

    @Override
    public void sougtucuAc() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sougtucuKapat() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

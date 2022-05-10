package nesne.proje;

import java.util.Random;

public class SicaklikAlgilayici implements ISicaklikAlgilayici{
    private Random rand = new Random(System.currentTimeMillis());
    @Override
    public int sicaklikOku() {
        return rand.nextInt(100);
    }
}

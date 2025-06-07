package cielsachen.ccprog3.model.truck;

public class SpecialTruck extends Truck {
    private float extraEspressoShotPrice = 0;
    private float syrupPrice = 0;

    public SpecialTruck(int id, String location) {
        super(id, location);
    }

    public float getExtraEspressoShotPrice() {
        return this.extraEspressoShotPrice;
    }

    public void setExtraEspressoShotPrice(float newExtraEspressoShotPrice) {
        this.extraEspressoShotPrice = newExtraEspressoShotPrice;
    }

    public float getSyrupPrice() {
        return this.syrupPrice;
    }

    public void setSyrupPrice(float newSyrupPrice) {
        this.syrupPrice = newSyrupPrice;
    }
}

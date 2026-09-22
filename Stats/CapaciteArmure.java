package Stats;

public class CapaciteArmure {
    private int initialArmor;
    private int currentArmor;

    public CapaciteArmure(int initialArmor) {
        this.initialArmor = initialArmor;
        this.currentArmor = initialArmor;
    }

    public int getInitialArmor() {
        return initialArmor;
    }

    public int getCurrentArmor() {
        return currentArmor;
    }

    public void setCurrentArmor(int currentArmor) {
        this.currentArmor = currentArmor;
    }

    public void increaseArmor(int amount) {
        currentArmor += amount;
    }

    public void decreaseArmor(int amount) {
        currentArmor -= amount;
        if (currentArmor < 0) {
            currentArmor = 0;
        }
    }

    public void resetArmor() {
        currentArmor = initialArmor;
    }
}

public record Caract(
    int force,
    int dexterite,
    int constitution,
    int intelligence,
    int sagesse,
    int charisme,
    String espece
)
{
    public Caract {
        validerValeurInitiale("Force", force);
        validerValeurInitiale("Dextérité", dexterite);
        validerValeurInitiale("Constitution", constitution);
        validerValeurInitiale("Intelligence", intelligence);
        validerValeurInitiale("Sagesse", sagesse);
        validerValeurInitiale("Charisme", charisme);
    }

    private static void validerValeurInitiale(String nom, int valeur) {
        if (valeur < 3 || valeur > 18) {
            throw new IllegalArgumentException(
                nom + " doit être comprise entre 3 et 18 à la création du héros (reçue: " + valeur + ")."
            );
        }
    }

    public Caract appliquerModificateurs(String espece) {
        int[] modificateurs = Espece.modificateursMap.get(espece);
        if (modificateurs == null) {
            throw new IllegalArgumentException("Espèce inconnue: " + espece);
        }

        return new Caract(
            Math.clamp(force + modificateurs[0], 3, 18),
            Math.clamp(dexterite + modificateurs[1], 3, 18),
            Math.clamp(constitution + modificateurs[2], 3, 18),
            Math.clamp(intelligence + modificateurs[3], 3, 18),
            Math.clamp(sagesse + modificateurs[4], 3, 18),
            Math.clamp(charisme + modificateurs[5], 3, 18),
            espece
        );
    }
}

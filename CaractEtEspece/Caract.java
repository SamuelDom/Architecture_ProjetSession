public record Caract(
    int forceInitiale,
    int dexteriteInitiale,
    int constitutionInitiale,
    int intelligenceInitiale,
    int sagesseInitiale,
    int charismeInitiale,
    String espece,
    int forceFinale,
    int dexteriteFinale,
    int constitutionFinale,
    int intelligenceFinale,
    int sagesseFinale,
    int charismeFinale
)
{
    public Caract {
        validerValeurInitiale("Force", forceInitiale);
        validerValeurInitiale("Dextérité", dexteriteInitiale);
        validerValeurInitiale("Constitution", constitutionInitiale);
        validerValeurInitiale("Intelligence", intelligenceInitiale);
        validerValeurInitiale("Sagesse", sagesseInitiale);
        validerValeurInitiale("Charisme", charismeInitiale);
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
            forceInitiale,
            dexteriteInitiale,
            constitutionInitiale,
            intelligenceInitiale,
            sagesseInitiale,
            charismeInitiale,
            espece,
            Math.clamp(forceInitiale + ((forceInitiale - 10) / 2) + modificateurs[0], 3, 20),
            Math.clamp(dexteriteInitiale + ((dexteriteInitiale - 10) / 2) + modificateurs[1], 3, 20),
            Math.clamp(constitutionInitiale + ((constitutionInitiale - 10) / 2) + modificateurs[2], 3, 20),
            Math.clamp(intelligenceInitiale + ((intelligenceInitiale - 10) / 2) + modificateurs[3], 3, 20),
            Math.clamp(sagesseInitiale + ((sagesseInitiale - 10) / 2) + modificateurs[4], 3, 20),
            Math.clamp(charismeInitiale + ((charismeInitiale - 10) / 2) + modificateurs[5], 3, 20)
        );
    }
}

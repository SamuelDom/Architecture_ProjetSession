package rpg.progression;

import java.util.List;
import java.util.Objects;

public final class ExperienceDistributor {
    public int distribute(int totalXp, List<? extends XpReceiver> participants) {
        if (totalXp < 0) throw new IllegalArgumentException("L'expérience totale ne peut pas être négative.");
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Au moins un participant est requis.");
        }
        if (participants.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Un participant ne peut pas être null.");
        }
        int share = totalXp / participants.size();
        for (XpReceiver participant : participants) participant.gainXp(share);
        return share;
    }
}

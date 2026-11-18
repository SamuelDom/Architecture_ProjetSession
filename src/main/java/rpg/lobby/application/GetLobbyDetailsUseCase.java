package rpg.lobby.application;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyRepository;

public final class GetLobbyDetailsUseCase {

    private final LobbyRepository lobbies;

    public GetLobbyDetailsUseCase(LobbyRepository lobbies) {
        this.lobbies = Objects.requireNonNull(lobbies, "lobbies");
    }

    public Optional<LobbyDetails> execute(String lobbyId) {
        return lobbies.findById(lobbyId)
                .map(lobby -> new LobbyDetails(
                        lobby.id(),
                        lobby.hostId(),
                        lobby.state().name(),
                        lobby.members().stream()
                                .map(member -> new MemberDetails(
                                        member.playerId(),
                                        member.pseudonym(),
                                        member.selectedHero()))
                                .toList()));
    }

    public record LobbyDetails(
            String id,
            String hostId,
            String state,
            List<MemberDetails> members) {
    }

    public record MemberDetails(
            String playerId,
            String pseudonym,
            Optional<Lobby.SelectedHero> selectedHero) {
    }
}
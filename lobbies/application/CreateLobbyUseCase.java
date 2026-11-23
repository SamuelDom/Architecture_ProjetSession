package lobbies.application;

import heros.domain.HeroId;
import java.util.Objects;
import java.util.UUID;
import lobbies.domain.Lobby;
import lobbies.domain.LobbyFactory;
import lobbies.domain.LobbyRepository;

public class CreateLobbyUseCase {
    private final LobbyFactory lobbyFactory;
    private final LobbyRepository lobbyRepository;
    private final LobbyOutputMapper outputMapper;

    public CreateLobbyUseCase(LobbyFactory lobbyFactory,
                              LobbyRepository lobbyRepository,
                              LobbyOutputMapper outputMapper) {
        this.lobbyFactory = Objects.requireNonNull(lobbyFactory);
        this.lobbyRepository = Objects.requireNonNull(lobbyRepository);
        this.outputMapper = Objects.requireNonNull(outputMapper);
    }

    public LobbyOutput execute(CreateLobbyCommand command) {
        Objects.requireNonNull(command, "La commande est requise.");
        if (command.playerId() == null || command.playerId().isBlank()) {
            throw new IllegalArgumentException("L'identifiant du joueur connecté est requis.");
        }
        HeroId hostId = new HeroId(UUID.fromString(command.playerId()));
        Lobby lobby = lobbyFactory.create(hostId);
        lobbyRepository.save(lobby);
        return outputMapper.toOutput(lobby);
    }
}

package chess.domain.piece;

import chess.domain.position.Position;
import chess.domain.strategy.KingMoveStrategy;

public final class King extends Piece {
    private final Team team;
    private final String symbol;

    public King(Team team, String symbol, Position position) {
        super(position, new KingMoveStrategy(), team);
        this.team = team;
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
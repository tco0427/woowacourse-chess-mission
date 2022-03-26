package chess.domain.piece;

import chess.domain.strategy.KnightMoveStrategy;

public final class Knight extends Piece {

    private final Team team;
    private final String symbol;

    public Knight(Team team, String symbol) {
        super(new KnightMoveStrategy(), team);
        this.team = team;
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public double getScore() {
        return 2.5;
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}

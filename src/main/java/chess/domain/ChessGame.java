package chess.domain;

import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.state.Ready;
import chess.domain.state.State;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChessGame {

    private static final List<Position> positions = stream(Rank.values())
            .flatMap(rank -> stream(File.values())
                    .map(file -> Position.of(file, rank)))
            .collect(toList());

    private final ChessBoard chessBoard;
    private final String gameName;
    private State state;

    public ChessGame() {
        gameName = "";
        state = new Ready();
        chessBoard = new ChessBoard();
        Collections.sort(positions);
    }

    public ChessGame(String gameName) {
        this.gameName = gameName;
        state = new Ready();
        chessBoard = new ChessBoard();
        Collections.sort(positions);
    }

    public ChessGame(String turn, String gameName, Map<Position, Piece> cells) {
        this.gameName = gameName;
        this.state = State.getState(turn);
        this.chessBoard = new ChessBoard(cells);
    }

    public boolean isExistKing() {
        return chessBoard.isExistKing();
    }

    public String getWinTeamName() {
        Map<Team, Double> teamScores = calculateResult();
        Result winTeam = chessBoard.findWinTeam(teamScores);

        return winTeam.getTeamName();
    }

    public void progress(Command command) {
        state = state.execute(command, chessBoard);
    }

    public boolean isEnd() {
        return state.isEnd();
    }

    public String getSymbolByPosition(Position position) {
        Piece piece = chessBoard.getPieceByPosition(position);

        return piece.getSymbol();
    }

    public Map<Team, Double> calculateResult() {
        Double whiteScore = calculateScore(WHITE);
        Double blackScore = calculateScore(BLACK);

        Map<Team, Double> result = new HashMap<>();
        result.put(WHITE, whiteScore);
        result.put(BLACK, blackScore);

        return result;
    }

    private double calculateScore(Team team) {
        return chessBoard.calculateScoreByTeam(team);
    }

    public List<String> getSymbols() {
        Set<Position> piecePositions = getPiecePositions();
        return makeSymbols(piecePositions);
    }

    private Set<Position> getPiecePositions() {
        Map<Position, Piece> cells = chessBoard.getCells();

        return Collections.unmodifiableSet(cells.keySet());
    }

    private List<String> makeSymbols(Set<Position> piecePositions) {
        return positions.stream()
                .map(position -> discriminate(piecePositions, position))
                .collect(toList());
    }

    private String discriminate(Set<Position> piecePositions, Position position) {
        if (piecePositions.contains(position)) {
            return getSymbolByPosition(position);
        }

        return ".";
    }

    public List<String> getChessBoardSymbol() {
        Set<Position> piecePositions = getPiecePositions();

        return positions.stream()
                .map(position -> makeChessBoard(piecePositions, position))
                .collect(toList());
    }

    private String makeChessBoard(Set<Position> piecePositions, Position position) {
        if (piecePositions.contains(position)) {
            return getSymbolAndTeam(position);
        }

        return "";
    }

    private String getSymbolAndTeam(Position position) {
        Piece piece = chessBoard.getPieceByPosition(position);

        Team team = piece.getTeam();
        String teamName = team.getTeam();

        String symbol = piece.getSymbolByTeam().toLowerCase();

        return teamName + "-" + symbol;
    }

    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }

    public State getState() {
        return this.state;
    }

    public String getGameName() {
        return this.gameName;
    }
}

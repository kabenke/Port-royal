package controller;

import ai.AIController;
import model.PortRoyal;

/**
 * This class is the central communication interface between controller and model.
 */
public class MainController {
	/**
	 * The PortRoyal
	 */
	private PortRoyal portRoyal;

	/**
	 * The MatchController
	 */
	private MatchController matchController;

	/**
	 * The TurnController
	 */
	private TurnController turnController;

	/**
	 * The ScoreController
	 */
	private ScoreController scoreController;

	/**
	 * The PortRoyalController
	 */
	private PortRoyalController portRoyalController;

	/**
	 * The IOController
	 */
	private IOController iOController;

	/**
	 * The ParseController
	 */
	private ParseController parseController;

	/**
	 * The AIController
	 */
	private AIController aIController;

	/**
	 * The MoveController
	 */
	private MoveController moveController;

	/**
	 * The PlayerController
	 */
	private PlayerController playerController;

	/**
	 * The UndoRedoController
	 */
	private UndoRedoController undoRedoController;

	/**
	 * Constructor for MainController
	 */
	public MainController() {
		iOController = new IOController(this);
		parseController = new ParseController(this);
		matchController = new MatchController(this);
		turnController = new TurnController(this);
		portRoyalController = new PortRoyalController(this);
		moveController = new MoveController(this);
		aIController = new AIController(this);
		playerController = new PlayerController(this);
		scoreController = new ScoreController(this);
		undoRedoController = new UndoRedoController(this);
		portRoyal = new PortRoyal();

		iOController.load();
	}

	/**
	 * This method gives us access to the IOController
	 * @return IOController
	 */
	public IOController getIOController() { return iOController; }

	/**
	 * This method gives us access to the ParseController
	 * @return ParseController
	 */
	public ParseController getParseController() { return parseController; }

	/**
	 * This method gives us access to the MatchController
	 * @return MatchController
	 */
	public MatchController getMatchController() { return matchController; }

	/**
	 * This method gives us access to the TurnController
	 * @return TurnController
	 */
	public TurnController getTurnController() { return turnController; }

	/**
	 * This method gives us access to the PortRoyalController
	 * @return PortRoyalController
	 */
	public PortRoyalController getPortRoyalController() { return portRoyalController; }

	/**
	 * This method gives us access to the MoveController
	 * @return MoveController
	 */
	public MoveController getMoveController() { return moveController; }

	/**
	 * This method gives us access to the AIController
	 * @return AIController
	 */
	public AIController getAIController() { return aIController; }

	/**
	 * This method gives us access to the PlayerController
	 * @return PlayerController
	 */
	public PlayerController getPlayerController() { return playerController; }

	/**
	 * This method gives us access to the ScoreController
	 * @return ScoreController
	 */
	public ScoreController getScoreController() { return scoreController; }

	/**
	 * This method gives us access to the UndoRedoController
	 * @return UndoRedoController
	 */
	public UndoRedoController getUndoRedoController() { return undoRedoController; }

	/**
	 * This method gives us access to the PortRoyal
	 * @return PortRoyal
	 */
	public PortRoyal getPortRoyal() { return portRoyal; }

	/**
	 * This method sets the IOController
	 * @param iOController IOController
	 */
	public void setiOController(IOController iOController) { this.iOController = iOController; }

	/**
	 * This method sets the ParseController
	 * @param parseController ParseController
	 */
	public void setParseController(ParseController parseController) { this.parseController = parseController; }

	/**
	 * This method sets the MatchController
	 * @param matchController MatchController
	 */
	public void setMatchController(MatchController matchController) { this.matchController = matchController; }

	/**
	 * This method sets the TurnController
	 * @param turnController  TurnController
	 */
	public void setTurnController(TurnController turnController) { this.turnController = turnController; }

	/**
	 * This method sets the PortRoyalController
	 * @param portRoyalController PortRoyalController
	 */
	public void setPortRoyalController(PortRoyalController portRoyalController) { this.portRoyalController = portRoyalController; }

	/**
	 * This method sets the MoveController
	 * @param moveController MoveController
	 */
	public void setMoveController(MoveController moveController) { this.moveController = moveController; }

	/**
	 * This method sets the AIController
	 * @param aIController AIController
	 */
	public void setAIController(AIController aIController) { this.aIController = aIController; }

	/**
	 * This method sets the PlayerController
	 * @param playerController PlayerController
	 */
	public void setPlayerController(PlayerController playerController) { this.playerController = playerController; }

	/**
	 * This method sets the ScoreController
	 * @param scoreController ScoreController
	 */
	public void setScoreController(ScoreController scoreController) { this.scoreController = scoreController; }

	/**
	 * This method sets the UndoRedoController
	 * @param undoRedoController UndoRedoController
	 */
	public void setUndoRedoController(UndoRedoController undoRedoController) { this.undoRedoController = undoRedoController; }

	/**
	 * This method sets the PortRoyal
	 * @param portRoyal PortRoyal
	 */
	public void setPortRoyal(PortRoyal portRoyal) { this.portRoyal = portRoyal; }

}

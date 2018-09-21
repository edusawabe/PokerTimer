package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Player;
import model.ProjecaoLine;
import model.RankingLine;
import model.ResultadoRodada;
import model.Resumo;
import model.Round;
import util.Constants;
import util.DateUtil;
import util.MailResultContent;
import util.MailSender;
import util.Mp3Player;
import util.NumberMaskFormatter;
import util.Util;

public class PokerTimerFXController implements Initializable{
	@FXML
	private Label bigAtual;
	@FXML
	private Label smallAtual;
	@FXML
	private Label lbAnteAtual;
	@FXML
	private Label valorAnteAtual;
	@FXML
	private Label bigSeguinte;
	@FXML
	private Label smallSeguinte;
	@FXML
	private Label lbAnteSeguinte;
	@FXML
	private Label valorAnteSeguinte;
	@FXML
	private Label lbProximoBreak;
	@FXML
	private Label statsJogando;
	@FXML
	private Label statsRebuys;
	@FXML
	private Label statsFora;
	@FXML
	private Label statsTotalArrecadado;
	@FXML
	private Label statsPremio1;
	@FXML
	private Label statsPremio2;
	@FXML
	private Label statsPremio3;
	@FXML
	private Label statsPremio4;
	@FXML
	private Label statsPremio5;
	@FXML
	private Label statsMedia;
	@FXML
	private Label lbJogadorSelecionado;
	@FXML
	private Label lbMesa1;
	@FXML
	private Label lbMesa2;
	@FXML
	private Label lbMesa3;
	@FXML
	private ProgressBar timerBar;
	@FXML
	private Button btAnterior;
	@FXML
	private Button btPause;
	@FXML
	private Button btPlay;
	@FXML
	private Button btSeguinte;
	@FXML
	private Button btSortear;
	@FXML
	private Button btTrocarMesa;
	@FXML
	private Button btAdicionaNaoInscrito;
	@FXML
	private ListView<String> listRodadas;
	@FXML
	private ListView<String> listJogadores;
	@FXML
	private ListView<String> listRebuys;
	@FXML
	private ListView<String> listFora;
	@FXML
	private ListView<String> listMesa1;
	@FXML
	private ListView<String> listMesa2;
	@FXML
	private ListView<String> listMesa3;
	@FXML
	private Button btAdicionaJogador;
	@FXML
	private Button btExcluirJogador;
	@FXML
	private Button btAdicionaRebuy;
	@FXML
	private Button btExcluirRebuy;
	@FXML
	private Button btAdicionaFora;
	@FXML
	private Button btExcluirFora;
	@FXML
	private Button btEnviarResultados;
	@FXML
	private Button btRanking;
	@FXML
	private ComboBox<String> cbJogador;
	@FXML
	private Label lbTimer;
	@FXML
	private AnchorPane parent;
	@FXML
	private VBox painelInferiorJogadores;
	@FXML
	private HBox hbBotoesJogadores;
	@FXML
	private HBox hbInferior;
	@FXML
	private HBox hbSuperior;

	private ConfigManager configManager;

	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();
	private ObservableList<String> oListRebuys = FXCollections.observableArrayList();
	private ObservableList<String> oListFora = FXCollections.observableArrayList();
	private ObservableList<String> oListrRodadas = FXCollections.observableArrayList();
	private ObservableList<String> oListComboJogador = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa1 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa2 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa3 = FXCollections.observableArrayList();

	private ObservableList<String> oListJogadoresBk = FXCollections.observableArrayList();
	private ObservableList<String> oListRebuysBk = FXCollections.observableArrayList();
	private ObservableList<String> oListForaBk = FXCollections.observableArrayList();
	private ObservableList<String> oListrRodadasBk = FXCollections.observableArrayList();
	private ObservableList<String> oListComboJogadorBk = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa1Bk = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa2Bk = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa3Bk = FXCollections.observableArrayList();

    private boolean paused;
    private boolean play;
    private int seconds;
    private int minutes;
    private int breakMinutes;
    private int maxRound;
    private int currentRound;
    private int currentSecond;
    private LinkedList<Round> roundList;
    private RoundManager roundManager;
    private Timeline timeLine;
    private double total1l = 0;
    private double total2l = 0;
    private double total3l = 0;
    private double total4l = 0;
    private double total5l = 0;
    private LinkedList<String> llMesa1;
    private LinkedList<String> llMesa1BK;
    private LinkedList<String> llMesa2;
    private LinkedList<String> llMesa2BK;
    private LinkedList<String> llMesa3;
    private LinkedList<String> llMesa3BK;
    private Timeline updateGuitask;
    private String lastKey;
    private String inputName;
    private int currentSelection;
    private int lastInput;
    private String lastSelectedName;
    private TimerWindowController timerWindowController;
	private Stage primaryStageTimer;
	private final static Logger logger = Logger.getLogger(PokerTimerFXController.class);
	private int numMesas = 0;
	private RebuysConsolidadoController rebuysConsolidadoController;

	public PokerTimerFXController() {
		lastKey = "";
		inputName = "";
		currentSelection = 0;
		lastSelectedName = "";
	}

	@FXML
	private void tratarFocus(Event evt){
		inputName = "";
	}

	@FXML
	private void tratarEventosTeclado(KeyEvent evt){
		lastInput = 3;
		if (listJogadores.isFocused()){
			String olTmp;
			String inTmp;
			inputName = inputName + ((KeyEvent) evt).getCharacter();
			inTmp = inputName.toLowerCase();
			for (int i = currentSelection; i < oListJogadores.size(); i++) {
				olTmp = oListJogadores.get(i).toLowerCase();
				if(olTmp.startsWith(inTmp)){
					if(lastSelectedName.equals(oListJogadores.get(i))){
						currentSelection = i + 1;
						listJogadores.scrollTo(i);
						lastSelectedName = oListJogadores.get(i);
					}
					else {
						listJogadores.getSelectionModel().select(i);
						listJogadores.scrollTo(i);
						currentSelection = i;
						lastSelectedName = oListJogadores.get(i);
						break;
					}
				}
			}
		} else {
			inputName = "";
			currentSelection = 0;
		}

		switch (evt.getCharacter()) {
		case "1":
			listJogadores.requestFocus();
			break;
		case "2":
			listRebuys.requestFocus();
			break;
		case "3":
			listFora.requestFocus();
			break;
		case "e":
			if(lastKey.equals("5"))
				removeJogadorTorneio(evt);
			break;
		case "E":
			if(lastKey.equals("5"))
				removeJogadorTorneio(evt);
			break;
		case "r":
			if(lastKey.equals("5"))
				adicionaRebuy(evt);
			break;
		case "R":
			if(lastKey.equals("5"))
				adicionaRebuy(evt);
			break;
		case "c":
			if(lastKey.equals("5")){
				if(listRebuys.isFocused())
					removeRebuy(evt);
				if(listFora.isFocused())
					cancelarRemocaoJogadorTorneio(evt);
			}
		case "C":
			if(lastKey.equals("5")){
				if(listRebuys.isFocused())
					removeRebuy(evt);
				if(listFora.isFocused())
					cancelarRemocaoJogadorTorneio(evt);
			}
		default:
			break;
		}

		if ((Character.isDigit(evt.getCharacter().charAt(0)) || (Character.isAlphabetic(evt.getCharacter().charAt(0))))) {
			lastKey= evt.getCharacter();
		}
	}

	@FXML
	private void selecaoMesa1(Event evt){
		listMesa2.getSelectionModel().clearSelection();
		listJogadores.getSelectionModel().clearSelection();
		if(oListJogadoresMesa3.size() > 0){
			listMesa3.getSelectionModel().clearSelection();
		}
	}

	@FXML
	private void selecaoMesa2(Event evt){
		listMesa1.getSelectionModel().clearSelection();
		listJogadores.getSelectionModel().clearSelection();
		if(oListJogadoresMesa3.size() > 0){
			listMesa3.getSelectionModel().clearSelection();
		}
	}

	@FXML
	private void selecaoMesa3(Event evt){
		listMesa1.getSelectionModel().clearSelection();
		listMesa2.getSelectionModel().clearSelection();
		listJogadores.getSelectionModel().clearSelection();
	}

	@FXML
	private void selecaojogadores(Event evt){
		listMesa1.getSelectionModel().clearSelection();
		listMesa2.getSelectionModel().clearSelection();
		if(oListJogadoresMesa3.size() > 0){
			listMesa3.getSelectionModel().clearSelection();
		}
	}

	@FXML
	private void adicionaNaoInscrito(Event evt){
		painelInferiorJogadores.getChildren().add(0, cbJogador);
		btExcluirJogador.setDisable(true);
		painelInferiorJogadores.getChildren().add(1, hbBotoesJogadores);
		btAdicionaNaoInscrito.setDisable(true);
	}

	@FXML
	private void trocarJogadorMesa(Event evt) {
		listMesa1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listMesa2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		if(oListJogadoresMesa3.size() > 0){
			listMesa3.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			if (listMesa1.getSelectionModel().getSelectedIndex() < 0
					&& listMesa2.getSelectionModel().getSelectedIndex() < 0
					&& listMesa3.getSelectionModel().getSelectedIndex() < 0) {

				Alert alError = new Alert(AlertType.ERROR);
				alError.setTitle("Selecionar Jogador");
				alError.setContentText("Por favor selecionar o jogador da troca em uma das Mesas");
				alError.show();
				return;
			}
			tratarTroca3Mesas();
		} else {
			if (listMesa1.getSelectionModel().getSelectedIndex() < 0
					&& listMesa2.getSelectionModel().getSelectedIndex() < 0) {
				Alert alError = new Alert(AlertType.ERROR);
				alError.setTitle("Selecionar Jogador");
				alError.setContentText("Por favor selecionar o jogador da troca em uma das Mesas");
				alError.show();
				return;
			}
			if (listMesa1.getSelectionModel().getSelectedIndex() >= 0) {
				trocarMesa(oListJogadoresMesa1,oListJogadoresMesa2,1,2,llMesa1,llMesa2,listMesa1,listMesa2);
			} else {
				trocarMesa(oListJogadoresMesa2,oListJogadoresMesa1,2,1,llMesa2,llMesa1,listMesa2,listMesa1);
			}
		}
	}

	private void trocarMesa(ObservableList<String> olJogadorMesaOrigem, ObservableList<String> olJogadorMesaDestino, int mesaOrigem, int mesaDestino,
			LinkedList<String> llOrigem, LinkedList<String> llDestino, ListView<String> listOrigem, ListView<String> listDestino){
		Random gerador = new Random();
		int maxMesa;
		int origem = 0, destino = 0;
		Alert al = new Alert(AlertType.CONFIRMATION);

		origem = listOrigem.getSelectionModel().getSelectedIndex();
		maxMesa = listDestino.getItems().size();
		destino = gerador.nextInt(maxMesa);

		al.setTitle("Troca de Mesa");
		al.setContentText("Confirmar Troca:" + "\nMesa " + origem + ": " + "\n - " + olJogadorMesaOrigem.get(origem) + "\nMesa "+destino +": "
				+ "\n - " + olJogadorMesaDestino.get(destino));
		al.showAndWait();
		if (al.getResult() == ButtonType.OK) {
			String j1, j2;
			j1 = olJogadorMesaOrigem.get(origem);
			j2 = olJogadorMesaDestino.get(destino);
			llOrigem.remove(j1);
			llDestino.remove(j2);
			llDestino.remove(j1);
			llOrigem.remove(j2);
			olJogadorMesaOrigem.remove(origem);
			olJogadorMesaDestino.remove(destino);
			olJogadorMesaDestino.add(destino, j1);
			olJogadorMesaOrigem.add(origem, j2);
			listOrigem.getSelectionModel().clearSelection();
			listDestino.getSelectionModel().clearSelection();
			listOrigem.getSelectionModel().select(origem);
			listDestino.getSelectionModel().select(destino);
			listOrigem.scrollTo(origem);
			listDestino.scrollTo(destino);
		}
	}

	private void tratarTroca3Mesas(){
		int qtdeMesa1=0, qtdeMesa2=0, qtdeMesa3=0, maiorMesa=0, maiorQtde=0, difMesas=0, mesaSelecionada=0;
		Random gerador = new Random();

		qtdeMesa1 = oListJogadoresMesa1.size();
		qtdeMesa2 = oListJogadoresMesa2.size();
		qtdeMesa3 = oListJogadoresMesa3.size();

		if(listMesa1.getSelectionModel().getSelectedIndex() > 0){
			mesaSelecionada = 1;
		}

		if(listMesa2.getSelectionModel().getSelectedIndex() > 0){
			mesaSelecionada = 2;
		}

		if(listMesa2.getSelectionModel().getSelectedIndex() > 0){
			mesaSelecionada = 3;
		}

		switch (mesaSelecionada) {
		case 1:
			if (gerador.nextInt(1000) % 2 == 0)
				trocarMesa(oListJogadoresMesa1, oListJogadoresMesa2, mesaSelecionada, 2, llMesa1, llMesa2, listMesa1, listMesa2);
			else
				trocarMesa(oListJogadoresMesa1, oListJogadoresMesa3, mesaSelecionada, 3, llMesa1, llMesa3, listMesa1, listMesa3);
			break;
		case 2:
			if (gerador.nextInt(1000) % 2 == 0)
				trocarMesa(oListJogadoresMesa2, oListJogadoresMesa1, mesaSelecionada, 1, llMesa2, llMesa1, listMesa2, listMesa1);
			else
				trocarMesa(oListJogadoresMesa2, oListJogadoresMesa3, mesaSelecionada, 3, llMesa2, llMesa3, listMesa2, listMesa3);
			break;
		case 3:
			if (gerador.nextInt(1000) % 2 == 0)
				trocarMesa(oListJogadoresMesa3, oListJogadoresMesa2, mesaSelecionada, 2, llMesa3, llMesa2, listMesa3, listMesa2);
			else
				trocarMesa(oListJogadoresMesa3, oListJogadoresMesa1, mesaSelecionada, 1, llMesa3, llMesa1, listMesa3, listMesa1);
			break;
		default:
			break;
		}
	}

	@FXML
	private void sortearMesas(Event evt){

		if(oListJogadores.size() > Constants.MAX_PLAYERS_2_TABLE){
			Alert al = new Alert(AlertType.CONFIRMATION);
			al.setTitle("Selecionar Quantidade de Mesas");
			al.setContentText("Existe quantidade de jogadores para 3 mesas.\n"
					+ "Selcione OK para Sortear 3 Mesas ou Cancelar para Sortear 2 Mesas.");
			al.setHeaderText("Selecionar a Quantidae de Mesas.");
			al.showAndWait();
			if(al.getResult() == ButtonType.OK){
				numMesas = 3;
				sortear3Mesas();
			} else {
				numMesas = 2;
				sortear2Mesas();
			}
		} else{
			sortear2Mesas();
		}
	}

	private void sortear2Mesas(){
		oListJogadoresMesa1.clear();
		oListJogadoresMesa2.clear();
		oListJogadoresMesa3.clear();
		llMesa1.clear();
		llMesa2.clear();
		llMesa3.clear();

		btSortear.setDisable(true);
		Random gerador = new Random();
		Random geradorMesa = new Random();
		ObservableList<String> copyList =  FXCollections.observableArrayList(oListJogadores);

		int size = oListJogadores.size();
		int players = oListJogadores.size();
		int totalSize = oListJogadores.size();
		int maxMesa = (totalSize/2) + 1;
		int numero = gerador.nextInt(size);
		int mesa = geradorMesa.nextInt(100);

		if ((totalSize % 2) == 0)
			maxMesa = totalSize/2;
		else
			maxMesa = (totalSize/2) + 1;

		while (size > 0 ){
			if (players > Constants.MAX_PLAYERS_FINAL_TABLE){
				if (llMesa1.size() < maxMesa && llMesa2.size() < maxMesa) {
					if ((mesa % 2) == 0)
						llMesa1.add(copyList.get(numero));
					else
						llMesa2.add(copyList.get(numero));
				} else {
					if (llMesa1.size() < maxMesa) {
						llMesa1.add(copyList.get(numero));
					} else {
						llMesa2.add(copyList.get(numero));
					}
				}
			}else{
				llMesa1.add(copyList.get(numero));
			}
			copyList.remove(numero);
			mesa = geradorMesa.nextInt(100);
			size = copyList.size();
			if(size > 0)
				numero = gerador.nextInt(size);
		}
		for (int i = 0; i < llMesa1.size(); i++) {
			oListJogadoresMesa1.add(i, llMesa1.get(i));
		}

		for (int i = 0; i < llMesa2.size(); i++) {
			oListJogadoresMesa2.add(i, llMesa2.get(i));
		}
		listMesa1.setItems(oListJogadoresMesa1);
		listMesa2.setItems(oListJogadoresMesa2);
	}

	private void sortear3Mesas(){
		boolean added = false;
		btSortear.setDisable(true);
		Random gerador = new Random();
		Random geradorMesa = new Random();
		ObservableList<String> copyList =  FXCollections.observableArrayList(oListJogadores);

		int size = oListJogadores.size();
		int players = oListJogadores.size();
		int totalSize = oListJogadores.size();
		int maxMesa = 0;
		int numero = gerador.nextInt(size);
		int mesa = geradorMesa.nextInt(900);

		if ((totalSize % 3) == 0)
			maxMesa = totalSize/3;
		else
			maxMesa = (totalSize/3) + 1;

		while (size > 0 ){
			//Se Existe pessoas suficientes para mais de uma mesa
			if (players > Constants.MAX_PLAYERS_FINAL_TABLE){
				if(mesa < 301){
					if (llMesa1.size() < maxMesa){
						llMesa1.add(copyList.get(numero));
						added = true;
					} else {
						added = false;
					}
				}
				if(mesa > 300 && mesa < 601){
					if (llMesa2.size() < maxMesa){
						llMesa2.add(copyList.get(numero));
						added = true;
					} else {
						added = false;
					}
				}
				if(mesa > 600 && mesa < 901){
					if (llMesa3.size() < maxMesa){
						llMesa3.add(copyList.get(numero));
						added = true;
					} else {
						added = false;
					}
				}
			}else{
				llMesa1.add(copyList.get(numero));
			}
			if(added){
				copyList.remove(numero);
			}
			mesa = geradorMesa.nextInt(900);
			size = copyList.size();
			if(size > 0)
				numero = gerador.nextInt(size);
		}
		for (int i = 0; i < llMesa1.size(); i++) {
			oListJogadoresMesa1.add(i, llMesa1.get(i));
		}

		for (int i = 0; i < llMesa2.size(); i++) {
			oListJogadoresMesa2.add(i, llMesa2.get(i));
		}
		for (int i = 0; i < llMesa3.size(); i++) {
			oListJogadoresMesa3.add(i, llMesa3.get(i));
		}
		listMesa1.setItems(oListJogadoresMesa1);
		listMesa2.setItems(oListJogadoresMesa2);
		listMesa3.setItems(oListJogadoresMesa3);
	}

	@FXML
	private void abrirSorteio(Event evt){
		Stage primaryStage = new Stage();
		SorteioMesasController sorteioMesasController = new SorteioMesasController();
		sorteioMesasController.setoListJogadores(oListJogadores);

		//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SorteioMesas.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			// definindo a nova janela
			Scene scene = new Scene(myPane, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sorteio de Mesas");

			// obtem o controller da nova janela
			sorteioMesasController = fxmlLoader.<SorteioMesasController> getController();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void abrirProjecao(Event evt){
		configManager.getPlayers();
		LinkedList<Player> lPlayer = configManager.getListPlayer();
    	Stage primaryStage = new Stage();
    	ObservableList<ProjecaoLine> lprojecaoLine = FXCollections.observableArrayList();
    	ProjecaoController projecaoController = new ProjecaoController();

    	int totalJogadoresAno = 0;

    	for (int i = 0; i < lPlayer.size(); i++) {
    		ArrayList<ResultadoRodada> listResultados = lPlayer.get(i).getResultados();
    		for (int j = 0; j < listResultados.size(); j++) {
				if(!listResultados.get(j).getColocacao().equals("0") && !listResultados.get(j).getColocacao().equals("00"))
					totalJogadoresAno++;
    		}
		}
    	totalJogadoresAno = totalJogadoresAno + oListJogadores.size() + oListFora.size();

    	double premioTotal = (totalJogadoresAno * Constants.SUBSCRIPTION_VALUE);
    	double premio1 = premioTotal * 0.45;
    	double premio2 = premioTotal * 0.25;
    	double premio3 = premioTotal * 0.15;
    	double premio4 = premioTotal * 0.10;
    	double premio5 = premioTotal * 0.05;

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Projecao.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			// definindo a nova janela
			Scene scene = new Scene(myPane, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Proje��es");

			lprojecaoLine = configManager.projetarResultado(oListRebuys, oListFora, oListJogadores, oListFora.size() + oListJogadores.size(),0,0,"");
			//lprojecaoLine = ordenarProjecaoRodada(lprojecaoLine);

			// obtem o controller da nova janela
			projecaoController = fxmlLoader.<ProjecaoController> getController();

			// inclui as informa��es do texto a abre a janela nova
			projecaoController.gettProjecao().setItems(lprojecaoLine);

			projecaoController.setListProjecaoLines(lprojecaoLine);
			projecaoController.getoListFora().addAll(oListFora);
			projecaoController.getoListJogadores().addAll(oListJogadores);
			projecaoController.getoListRebuys().addAll(oListRebuys);
			projecaoController.setConfigManager(configManager);
			projecaoController.getoListJogando().addAll(oListJogadores);
			projecaoController.getoListJogando().addAll(oListFora);
			projecaoController.sortoListJogando();
			projecaoController.getCbJogadores().setItems(projecaoController.getoListJogando());

			projecaoController.getLabel1().setText("R$ "+ Math.round(premio1));
			projecaoController.getLabel2().setText("R$ "+Math.round(premio2));
			projecaoController.getLabel3().setText("R$ "+Math.round(premio3));
			projecaoController.getLabel4().setText("R$ "+Math.round(premio4));
			projecaoController.getLabel5().setText("R$ "+Math.round(premio5));
			projecaoController.getLabelAnual().setText("R$ "+Math.round(premioTotal));

			for (int i = 0; i < oListJogadores.size() + oListFora.size(); i++) {
				projecaoController.getListComboColocacao().add((i+1) + "�");
			}

			projecaoController.getCbColocacao().getItems().addAll(projecaoController.getListComboColocacao());

			for (int i = projecaoController.getoListJogando().size()+1; i < 16; i++) {
				switch (i) {
				case 10:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao10"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				case 11:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao11"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				case 12:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao12"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				case 13:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao13"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				case 14:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao14"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				case 15:
					for (int j = 0; j < projecaoController.gettProjecao().getColumns().size(); j++) {
						if (projecaoController.gettProjecao().getColumns().get(j).getId().equals("cProjecao15"))
							projecaoController.gettProjecao().getColumns().get(j).setVisible(false);
					}
					break;
				default:
					break;
				}
			}
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param evt
	 */
	/**
	 * @param evt
	 */
	@FXML
	private void enviarResultados(Event evt){
		configManager.updatePlayersResult(oListFora, oListRebuys, total1l, total2l, total3l, total4l, total5l);

		MailResultContent mailContent = new MailResultContent();
        mailContent.setArrecadado(statsTotalArrecadado.getText());
        mailContent.setOuts(oListFora);
        mailContent.setPlayers(oListJogadores);
        mailContent.setPremio1(statsPremio1.getText());
        mailContent.setPremio2(statsPremio2.getText());
        mailContent.setPremio3(statsPremio3.getText());
        mailContent.setPremio4(statsPremio4.getText());
        mailContent.setPremio5(statsPremio5.getText());
        mailContent.setRebuy(oListRebuys);
        mailContent.setRoundFinal(oListrRodadas.get(listRodadas.getSelectionModel().getSelectedIndex()));
        String msgHtml = mailContent.toStringCssHtml();
        String msg = mailContent.toString();
        MailSender sender = new MailSender();
        String subject = "Resultados Etapa "+ DateUtil.getDate();

		Alert alert = new Alert(AlertType.INFORMATION);
		WebView webView = new WebView();
		alert.setTitle("Resultados da Etapa");
		alert.setHeaderText("Resultados da Etapa");
		//alert.setContentText(msg);
		webView.getEngine().loadContent(msgHtml);
		webView.setPrefSize(800, 600);
		//alert.getDialogPane().setContent(webView);
		TextArea t = new TextArea();
		t.setFont(new Font("Courrier New", 12));
		//t.setText(msg + "\n\n\n" + configManager.getMailList());
		t.setText(msg);
		alert.getDialogPane().setContent(t);
		alert.setWidth(800);
		alert.setHeight(600);
		alert.setResizable(true);
		alert.show();
		/*
        try {
			sender.sendMail(subject, msgHtml, configManager.getMailList(), true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@FXML
	private void loadRanking(Event evt){
		configManager.getPlayers();
		LinkedList<Player> lPlayer = configManager.getListPlayer();
		LinkedList<Player> lOrderedPlayer = new LinkedList<Player>();
    	Stage primaryStage = new Stage();
    	ObservableList<RankingLine> lRanking = FXCollections.observableArrayList();
    	RankingController rankingController;
    	Resumo resumo = new Resumo();
    	boolean added;

    	ExcelRankingGenerator excelRankingGenerator = new ExcelRankingGenerator();
    	ExcelRankingGenerator statsExcelGenerator = new ExcelRankingGenerator();
    	excelRankingGenerator.setlPlayer(lPlayer);
    	excelRankingGenerator.generateExcel();
    	statsExcelGenerator.setlPlayer(lPlayer);
    	statsExcelGenerator.generateStatisticsExcel();
    	statsExcelGenerator.generateExcel();

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ranking.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			//prepara os dados do jogador e rodadas
			for (int i = 0; i < lPlayer.size(); i++) {
				added = false;
				Player p = lPlayer.get(i);
				p.updatePontuacaoTotal();
				if (lOrderedPlayer.isEmpty())
					lOrderedPlayer.add(p);
				else {
					for (int j = 0; j < lOrderedPlayer.size(); j++) {
						if (lOrderedPlayer.get(j).getPontuacaoTotalComDescarte() <= p.getPontuacaoTotalComDescarte()) {
							lOrderedPlayer.add(j, p);
							added = true;
							break;
						}
					}
					if (!added) {
						lOrderedPlayer.add(p);
					}
				}
			}

			int pos = 0;
			//Adiciona os jogadores ordenados na lista de Ranking
			for (int i = 0; i < lOrderedPlayer.size(); i++) {
				Player p = lOrderedPlayer.get(i);
				pos = i + 1;
				RankingLine r = new RankingLine();
				r.setJogador(util.Util.completeZeros(pos, 2) + "� - " + p.getPlayerName());
				r.setResult1(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(0).getColocacao()), 2) + "/" + p.getResultados().get(0).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(0).getPontuacaoEtapa(),3));
				r.setResult2(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(1).getColocacao()), 2) + "/" + p.getResultados().get(1).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(1).getPontuacaoEtapa(),3));
				r.setResult3(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(2).getColocacao()), 2) + "/" + p.getResultados().get(2).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(2).getPontuacaoEtapa(),3));
				r.setResult4(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(3).getColocacao()), 2) + "/" + p.getResultados().get(3).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(3).getPontuacaoEtapa(),3));
				r.setResult5(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(4).getColocacao()), 2) + "/" + p.getResultados().get(4).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(4).getPontuacaoEtapa(),3));
				r.setResult6(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(5).getColocacao()), 2) + "/" + p.getResultados().get(5).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(5).getPontuacaoEtapa(),3));
				r.setResult7(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(6).getColocacao()), 2) + "/" + p.getResultados().get(6).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(6).getPontuacaoEtapa(),3));
				r.setResult8(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(7).getColocacao()), 2) + "/" + p.getResultados().get(7).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(7).getPontuacaoEtapa(),3));
				r.setResult9(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(8).getColocacao()), 2) + "/" + p.getResultados().get(8).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(8).getPontuacaoEtapa(),3));
				r.setResult10(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(9).getColocacao()), 2) + "/" + p.getResultados().get(9).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(9).getPontuacaoEtapa(),3));
				r.setResult11(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(10).getColocacao()), 2) + "/" + p.getResultados().get(10).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(10).getPontuacaoEtapa(),3));
				r.setResult12(util.Util.completeZeros(Integer.parseInt(p.getResultados().get(11).getColocacao()), 2) + "/" + p.getResultados().get(11).getRebuys() + "/" + Util.completeZerosDouble(p.getResultados().get(11).getPontuacaoEtapa(),3));
				resumo = p.getResumo();
				r.setTotal("" + Util.completeZerosDouble(p.getPontuacaoTotal(),4));
				r.setTotalDescarte("" + Util.completeZerosDouble(p.getPontuacaoTotalComDescarte(),4));
				r.setTotalRebuys(""+resumo.getRebuys());
				r.setTotalGanho("R$ " + resumo.getTotalGanho());
				r.setTotalGasto("R$ -" + resumo.getTotalGasto());
				r.setSaldo("R$ " + resumo.getSaldo());
				lRanking.add(r);
			}

			//definindo a nova janela
			Scene scene = new Scene(myPane,900,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ranking Anual");

			//obtem o controller da nova janela
			rankingController =  fxmlLoader.<RankingController>getController();
			rankingController.gettRanking().setItems(lRanking);

			//inclui as informa��es do texto a abre a janela nova
			rankingController.setListRanking(lRanking);
			primaryStage.show();

			Stage secondaryStage = new Stage();
			GraficoAnualController graficoController;
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("GraficoAnual.fxml"));
			//carrega o loader
			Pane myPane2 = (Pane) fxmlLoader2.load();

			//definindo a nova janela
			Scene scene2 = new Scene(myPane2,900,600);
			secondaryStage.setScene(scene2);
			secondaryStage.setTitle("Ranking Anual - Gr�fico");

			//obtem o controller da nova janela
			graficoController =  fxmlLoader2.<GraficoAnualController>getController();

			secondaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void addJogador(Event evt){
		int index = 0;
		//Verifica se jogador j� existe no combo ou � novo item (index < 0)
		index = cbJogador.getItems().indexOf(cbJogador.editorProperty().get().getText());

		if (index < 0 && cbJogador.editorProperty().get().getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Jogador Inv�lido!");
			alert.setContentText("N�o � Permitido Incluir Jogador Sem Nome!");
			alert.show();
			return;
		}
		btAdicionaNaoInscrito.setDisable(false);

		if (play) {
			if(listRodadas.getSelectionModel().getSelectedIndex() > Constants.MAX_ROUND_REBUY){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Torneio em Andamento");
				alert.setContentText("Torneio J� Iniciado. N�o � mais permitido adicionar jogadores.");
				alert.show();
			} else {
				int s = painelInferiorJogadores.getChildren().size();
				for (int i = 0; i < s; i++) {
					if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
						if (painelInferiorJogadores.getChildren().get(i).getId().equals("painelJogadores")) {
							painelInferiorJogadores.getChildren().remove(i);
							break;
						}
					}
				}

				s = painelInferiorJogadores.getChildren().size();
				for (int i = 0; i < s; i++) {
					if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
						if (painelInferiorJogadores.getChildren().get(i).getId().equals("comboJogadores")) {
							painelInferiorJogadores.getChildren().remove(i);
							break;
						}
					}
				}

				Random gerador = new Random();
				int destino = gerador.nextInt(10);
				int pos = 0;
				destino = destino%2;

				if (oListJogadoresMesa1.size() == oListJogadoresMesa2.size()) {
					if (destino == 0) {
						adicionaTorneioMesa1(gerador);
					} else {
						adicionaTorneioMesa2(gerador);
					}
				} else {
					if (oListJogadoresMesa1.size() > oListJogadoresMesa2.size()) {
						adicionaTorneioMesa2(gerador);
					} else {
						adicionaTorneioMesa1(gerador);
					}
				}
			}
		} else {
			movimentaJogador();
		}
	}

	private void adicionaTorneioMesa2(Random gerador) {
		int pos;
		pos = gerador.nextInt(oListJogadoresMesa2.size()+1);
		oListJogadoresMesa2.add(pos, cbJogador.getSelectionModel().getSelectedItem());
		Util.addJogadorListaOrdenadamente(cbJogador.getSelectionModel().getSelectedItem(), oListJogadores);
		oListComboJogador.remove(cbJogador.getSelectionModel().getSelectedIndex());
		cbJogador.setItems(oListComboJogador);
		listMesa2.setItems(oListJogadoresMesa2);
	}

	private void adicionaTorneioMesa1(Random gerador) {
		int pos;
		pos = gerador.nextInt(oListJogadoresMesa1.size()+1);
		oListJogadoresMesa1.add(pos, cbJogador.getSelectionModel().getSelectedItem());
		Util.addJogadorListaOrdenadamente(cbJogador.getSelectionModel().getSelectedItem(), oListJogadores);
		oListComboJogador.remove(cbJogador.getSelectionModel().getSelectedIndex());
		cbJogador.setItems(oListComboJogador);
		listMesa1.setItems(oListJogadoresMesa1);
	}

	private void movimentaJogador() {
		int index = 0;
		//Verifica se jogador j� existe no combo ou � novo item (index < 0)
		index = cbJogador.getItems().indexOf(cbJogador.editorProperty().get().getText());

		//Adiciona jogador no arquivo caso n�o exista
		configManager.addPlayer(cbJogador.editorProperty().get().getText());

		//Se existe, remove jogador do combo e adiociona na lista de jogadores
		//Sen�o apenas adiciona na lista de jogadores
		if (index >= 0){
			Util.addJogadorListaOrdenadamente(cbJogador.getItems().get(index), oListJogadores);
			cbJogador.getItems().remove(index);
		}
		else {
			Util.addJogadorListaOrdenadamente(cbJogador.editorProperty().get().getText(), oListJogadores);
		}
		listJogadores.setItems(oListJogadores);
		cbJogador.setItems(oListComboJogador);
		cbJogador.editorProperty().get().setText("");
	}


	@FXML
	private void removerJogador(Event evt){
		String jogador = null;
		if (play) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Torneio em Andamento");
			alert.setContentText("Torneio J� Iniciado. N�o � mais permitido excluir jogadores.");
			alert.show();
			return;
		} else {
			//obtem indice do jogador e nome do jogador
			int i = listJogadores.getSelectionModel().getSelectedIndex();
			if (i < 0){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Selecionar Jogador");
				alert.setContentText("Favor Selecionar Jogador a Excluir.");
				alert.show();
				return;
			}
			jogador = listJogadores.getItems().get(i);

			//remove da lista de jogadores
			oListJogadores.remove(i);
			listJogadores.setItems(oListJogadores);

			//Adiciona na lista de jogadores do Combo ordenadamente
			Util.addJogadorListaOrdenadamente(jogador, oListComboJogador);
			cbJogador.setItems(oListComboJogador);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void play(Event evt){
		if(oListJogadoresMesa1.isEmpty() && oListJogadoresMesa2.isEmpty() && oListJogadoresMesa3.isEmpty() && oListFora.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Mesas N�o Sorteadas");
			alert.setContentText("Favor Sortear as Mesas Antes de Iniciar o Torneio.");
			alert.show();
			return;
		}
		btAdicionaNaoInscrito.setVisible(true);
		int s = painelInferiorJogadores.getChildren().size();
		for (int i = 0; i < s; i++) {
			if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
				if (painelInferiorJogadores.getChildren().get(i).getId().equals("painelJogadores")) {
					painelInferiorJogadores.getChildren().remove(i);
					break;
				}
			}
		}

		s = painelInferiorJogadores.getChildren().size();
		for (int i = 0; i < s; i++) {
			if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
				if (painelInferiorJogadores.getChildren().get(i).getId().equals("comboJogadores")) {
					painelInferiorJogadores.getChildren().remove(i);
					break;
				}
			}
		}

		setRound();
		playMario();
		if(!play){
			play = true;
			timeLine = new Timeline();
			timeLine.setCycleCount(Timeline.INDEFINITE);
			timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {
				// KeyFrame event handler
				@Override
				public void handle(Event event) {
					timerAction();
				}
			}));
			timeLine.playFromStart();
		}
		if(paused)
			paused = false;
	}

	@FXML
	private void pause(Event evt){
		if(!paused)
			paused = true;
		else
			paused = false;
	}

	@FXML
	private void volta(Event evt){
		int iRodada = currentRound;
		if(iRodada > 0)
			listRodadas.getSelectionModel().select(iRodada-1);
		else
			listRodadas.getSelectionModel().select(0);
		currentRound = listRodadas.getSelectionModel().getSelectedIndex();
		if (roundList.get(currentRound).isRebuyAllowed()) {
			btAdicionaRebuy.setDisable(false);
			btExcluirRebuy.setDisable(false);
			btAdicionaNaoInscrito.setDisable(false);
		} else {
			btAdicionaRebuy.setDisable(true);
			btExcluirRebuy.setDisable(true);
			btAdicionaNaoInscrito.setDisable(true);
		}
		restartTimer();
	}

	@FXML
	private void avanca(Event evt){
		int iRodada = currentRound;
		if(iRodada < Constants.MAX_ROUNDS)
			listRodadas.getSelectionModel().select(iRodada+1);
		else
			listRodadas.getSelectionModel().select(Constants.MAX_ROUNDS);
		currentRound = listRodadas.getSelectionModel().getSelectedIndex();
		if (roundList.get(currentRound).isRebuyAllowed()) {
			btAdicionaRebuy.setDisable(false);
			btExcluirRebuy.setDisable(false);
			btAdicionaNaoInscrito.setDisable(false);
		} else {
			btAdicionaRebuy.setDisable(true);
			btExcluirRebuy.setDisable(true);
			btAdicionaNaoInscrito.setDisable(true);
		}
		restartTimer();
	}

	@FXML
	private void adicionaRebuy(Event evt){

		int i = listJogadores.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador do Rebuy");
			alert.show();
		}
		else{
			oListRebuys.add(oListJogadores.get(i));
			atualizarEstatisticas();
			playRebuy();
		}
		listJogadores.requestFocus();
	}

	@FXML
	private void removeRebuy(Event evt){
		int i = listRebuys.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador a Cancelar Rebuy");
			alert.show();
		}
		else{
			oListRebuys.remove(i);
			atualizarEstatisticas();
			playCancelRebuy();
		}
		listJogadores.requestFocus();
	}

	@FXML
	private void removeJogadorTorneio(Event evt){

		//Backups
		oListJogadoresMesa1Bk.clear();
		oListJogadoresMesa1Bk.addAll(oListJogadoresMesa1);
		oListJogadoresMesa2Bk.clear();
		oListJogadoresMesa2Bk.addAll(oListJogadoresMesa2);
		oListJogadoresMesa3Bk.clear();
		oListJogadoresMesa3Bk.addAll(oListJogadoresMesa3);
		oListComboJogadorBk.clear();
		oListComboJogadorBk.addAll(oListComboJogador);
		oListForaBk.clear();
		oListForaBk.addAll(oListFora);
		oListJogadoresBk.clear();
		oListJogadoresBk.addAll(oListJogadores);
		llMesa1BK.clear();
		llMesa1BK.addAll(llMesa1);
		llMesa2BK.clear();
		llMesa2BK.addAll(llMesa2);
		llMesa3BK.clear();
		llMesa3BK.addAll(llMesa3);

		int i = listJogadores.getSelectionModel().getSelectedIndex();
		int size1, size2, size3, diferenca, posicaoTroca, posicaoEliminacao, mesa, sorteado, sortedSize, result;
		Alert al = new Alert(AlertType.CONFIRMATION);
		al.setTitle("Mudar Jogador de Mesa");
		Random gerador = new Random();
		String eliminado, jogadorReposicionado;

		//Error
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador Eliminado na Lista de Inscritos!");
			alert.show();
		}
		else{
			//Validate End of Game or Final Table
			if(oListJogadores.size() == 2)
				playLastPlayer();
			else
				if(oListJogadores.size() == 1)
					playFinishTournament();
				else
					playElimina();

			//nome do jogador eliminado
			eliminado = oListJogadores.get(i);

			//adiciona na lista de eliminados
			oListFora.add(oListJogadores.get(i));

			//verifica de qual mesa o jogador esta sendo eliminado e elimina jogador da mesa
			mesa = buscaMesaEliminado(eliminado);
			posicaoEliminacao = buscaPosicaoEliminado(eliminado, mesa);
			removeEliminadoMesa(eliminado, mesa);

			//elimina jogador da lista de inscritos
			oListJogadores.remove(i);

			//recalcula tamanho das mesas
			size1 = oListJogadoresMesa1.size();
			size2 = oListJogadoresMesa2.size();
			size3 = oListJogadoresMesa3.size();

			//Valida se existia 3 mesas e n�o precisa mais de 3 meses
			if(numMesas == 3){
				//Realiza o Sorteio Novamente pois n�o precisa mais de Duas Mesas
				if (oListJogadores.size() == Constants.MAX_PLAYERS_2_TABLE){
					sortear2Mesas();
					Alert al2Mesa = new Alert(AlertType.INFORMATION);
					al2Mesa.setTitle("Mesa Final");
					al2Mesa.setContentText("Numero de Jogadores para 2 Mesas Atingido."
							+ "\nFavor Reposicionar Jogadores Conforme o Novo Sorteio Realizado!");
					al2Mesa.setResizable(true);
					al2Mesa.setWidth(600);
					al2Mesa.setHeight(400);
					al2Mesa.show();
					return;
				}
			}

			//tratamento para sorteio da mesa final
			if (oListJogadores.size() == Constants.MAX_PLAYERS_FINAL_TABLE){
				oListJogadoresMesa1.clear();
				oListJogadoresMesa2.clear();
				oListJogadoresMesa3.clear();
				sortedSize = oListJogadoresMesa1.size();

				while(sortedSize != oListJogadores.size()){
					sorteado = gerador.nextInt(oListJogadores.size());
					if(oListJogadoresMesa1.indexOf(oListJogadores.get(sorteado)) < 0){
						oListJogadoresMesa1.add(oListJogadores.get(sorteado));
						sortedSize = oListJogadoresMesa1.size();
					}
				}
				Alert alMesaFinal = new Alert(AlertType.INFORMATION);
				alMesaFinal.setTitle("Mesa Final");
				alMesaFinal.setContentText("Numero de Jogadores da Mesa Final Atingido."
						+ "\nFavor Reposicionar Jogadores Conforme o Novo Sorteio Realizado!");
				alMesaFinal.setResizable(true);
				alMesaFinal.setWidth(600);
				alMesaFinal.setHeight(400);
				alMesaFinal.show();
				return;
			}

			// verfica se existe necessidade de balancear jogadores
			if (size1 > 0 && size2 > 0 && size3 > 0) {
				//tratamento de elimina��o por mesa
				switch (mesa) {
				// eliminacao ocorrendo na mesa 1 - Se balancear Destino � a
				// Mesa 1
				case 1:
					if (size2 > size3) {
						verificarBalanceamento(size2, size1, posicaoEliminacao, 2, 1, eliminado, oListJogadoresMesa2, oListJogadoresMesa1, listMesa1);
					} else {
						if(size3 > size2)
							verificarBalanceamento(size3, size1, posicaoEliminacao, 3, 1, eliminado, oListJogadoresMesa3, oListJogadoresMesa1, listMesa1);
						else {
							result = gerador.nextInt(1000);
							if(result%2 == 0){
								verificarBalanceamento(size2, size1, posicaoEliminacao, 2, 1, eliminado, oListJogadoresMesa2, oListJogadoresMesa1, listMesa1);
							} else {
								verificarBalanceamento(size3, size1, posicaoEliminacao, 3, 1, eliminado, oListJogadoresMesa3, oListJogadoresMesa1, listMesa1);
							}
						}
					}
					break;
				// eliminacao ocorrendo na mesa 2 - Se balancear Destino � a
				// Mesa 2
				case 2:
					if (size1 > size3) {
						verificarBalanceamento(size1, size2, posicaoEliminacao, 1, 2, eliminado, oListJogadoresMesa1, oListJogadoresMesa2, listMesa2);
					} else {
						if(size3 > size1)
							verificarBalanceamento(size3, size2, posicaoEliminacao, 3, 2, eliminado, oListJogadoresMesa3, oListJogadoresMesa2, listMesa2);
						else {
							result = gerador.nextInt(1000);
							if(result%2 == 0){
								verificarBalanceamento(size1, size2, posicaoEliminacao, 1, 2, eliminado, oListJogadoresMesa1, oListJogadoresMesa2, listMesa2);
							} else {
								verificarBalanceamento(size3, size2, posicaoEliminacao, 3, 2, eliminado, oListJogadoresMesa3, oListJogadoresMesa2, listMesa2);
							}
						}
					}
					break;
				// eliminacao ocorrendo na mesa 2 - Se balancear Destino � a
				// Mesa 3
				case 3:
					if (size1 > size2) {
						verificarBalanceamento(size1, size3, posicaoEliminacao, 1, 3, eliminado, oListJogadoresMesa1, oListJogadoresMesa3, listMesa3);
					} else {
						if(size2 > size1)
							verificarBalanceamento(size2, size3, posicaoEliminacao, 2, 3, eliminado, oListJogadoresMesa2, oListJogadoresMesa3, listMesa3);
						else {
							result = gerador.nextInt(1000);
							if(result%2 == 0){
								verificarBalanceamento(size1, size3, posicaoEliminacao, 1, 3, eliminado, oListJogadoresMesa1, oListJogadoresMesa3, listMesa3);
							} else {
								verificarBalanceamento(size2, size3, posicaoEliminacao, 2, 3, eliminado, oListJogadoresMesa2, oListJogadoresMesa3, listMesa3);
							}
						}
					}
					break;
				default:
					break;
				}
			}
			// verfica se existe necessidade de balancear jogadores
			if (size1 > 0 && size2 > 0 && size3 == 0) {
				if (size1 > size2) {
					diferenca = size1 - size2;
				} else {
					diferenca = size2 - size1;
				}
				// balanceando jogadores
				if (diferenca > 1) {
					// se jogador eliminado � da mesa 2
					if (mesa == 2) {
						balanceTables(posicaoEliminacao, 1, 2, size1, eliminado, oListJogadoresMesa1, oListJogadoresMesa2, listMesa2);
					} else {
						// se jogador eliminado � da mesa 1
						// obtem jogador da mesa 2 a ser trocado
						balanceTables(posicaoEliminacao, 2, 1, size2, eliminado, oListJogadoresMesa2, oListJogadoresMesa1, listMesa1);
					}
				}
			}
		}
		listJogadores.requestFocus();
	}

	private void verificarBalanceamento(int sizeOrigem, int sizeDestino, int posicaoEliminacao, int mesaOrigem, int mesaDestino,
			String eliminado, ObservableList<String> olMesaOrigem, ObservableList<String> olMesaDestino, ListView<String> llDestino){
		int diferenca = 0;
		if (sizeOrigem > sizeDestino) {
			diferenca = sizeOrigem - sizeDestino;
			if (diferenca > 1) {
				balanceTables(posicaoEliminacao, mesaOrigem, mesaDestino, sizeOrigem, eliminado, olMesaOrigem,
						olMesaDestino, llDestino);
			}
		}
	}

	private void balanceTables(int posicaoEliminacao, int mesaOrigem, int mesaDestino, int sizeMesaOrigem
			, String eliminado, ObservableList<String> olOrigem, ObservableList<String> olDestino
			, ListView<String> llMesaDestino) {
		Random gerador = new Random();
		Alert al = new Alert(AlertType.INFORMATION);

		// se jogador eliminado � da mesa 1
		// obtem jogador da mesa 2 a ser trocado
		int posicaoTroca = gerador.nextInt(sizeMesaOrigem);
		String jogadorReposicionado = olOrigem.get(posicaoTroca);

		al.setContentText("Trocar de Mesa!" + "\n" + " - Jogador Da Mesa " + mesaOrigem + ":\n   " + jogadorReposicionado
				+ "\nIr para Mesa "+mesaDestino+": " + "\n  - Na posi��o em que estava:\n  " + eliminado);
		olDestino.add(posicaoEliminacao, olOrigem.get(posicaoTroca));
		olOrigem.remove(posicaoTroca);
		listJogadores.getSelectionModel().clearSelection();
		llMesaDestino.getSelectionModel().clearSelection();
		llMesaDestino.getSelectionModel().select(jogadorReposicionado);
		al.setResizable(true);
		al.setWidth(600);
		al.setHeight(500);
		al.show();
	}

	private int buscaMesaEliminado(String eliminado) {
		if(oListJogadoresMesa1.indexOf(eliminado) >= 0)
			return 1;
		if(oListJogadoresMesa2.indexOf(eliminado) >= 0)
			return 2;
		if(oListJogadoresMesa3.indexOf(eliminado) >= 0)
			return 3;
		return 0;
	}

	private int buscaPosicaoEliminado(String eliminado, int mesa) {
		switch (mesa) {
			case 1:
				return oListJogadoresMesa1.indexOf(eliminado);
			case 2:
				return oListJogadoresMesa2.indexOf(eliminado);
			case 3:
				return oListJogadoresMesa3.indexOf(eliminado);
			default:
				return 0;
		}
	}

	private void removeEliminadoMesa(String eliminado, int mesa) {
		switch (mesa) {
			case 1:
				oListJogadoresMesa1.remove(eliminado);
				break;
			case 2:
				oListJogadoresMesa2.remove(eliminado);
				break;
			case 3:
				oListJogadoresMesa3.remove(eliminado);
				break;
			default:
				break;
		}
	}

	@FXML
	private void cancelarRemocaoJogadorTorneio(Event evt){
		int i = listFora.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador a Cancelar Elimina��o");
			alert.show();
		}
		else{
			playCancelElimina();
			oListJogadoresMesa1.clear();
			oListJogadoresMesa1.addAll(oListJogadoresMesa1Bk);
			oListJogadoresMesa2.clear();
			oListJogadoresMesa2.addAll(oListJogadoresMesa2Bk);
			oListJogadoresMesa3.clear();
			oListJogadoresMesa3.addAll(oListJogadoresMesa3Bk);
			oListFora.clear();
			oListFora.addAll(oListForaBk);
			oListJogadores.clear();
			oListJogadores.addAll(oListJogadoresBk);
			llMesa1.clear();
			llMesa1.addAll(llMesa1BK);
			llMesa2.clear();
			llMesa2.addAll(llMesa2BK);
			llMesa3.clear();
			llMesa3BK.addAll(llMesa3BK);
			atualizarEstatisticas();
		}
		listJogadores.requestFocus();
	}

	private void retornarJogadorMesa(String jogador, ObservableList<String> l, LinkedList<String> lOriginal) {
		boolean added = false;
		if(l.size() == 0)
			l.add(jogador);
		else{
			int posOriginal = lOriginal.indexOf(jogador);
			for (int i = 0; i < l.size(); i++) {
				if(lOriginal.indexOf(l.get(i)) > posOriginal){
					l.add(i,jogador);
					added = true;
					break;
				}
			}
			if(!added)
				l.add(jogador);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Iniciando GUI");
		//ConfigManager obtem a lista de jogadores cadastrados
		configManager = new ConfigManager();
		configManager.setConfigFileName(Constants.CONFIG_FILE_NAME);
		configManager.setPlayersFileName(Constants.STAGE_PLAYERS_FILE_NAME);
		configManager.setActualStateFileName(Constants.ACTUAL_STATE_FILE_NAME);
		configManager.readFile();
		configManager.readActualStateFile();
		configManager.getListPlayer().clear();
		configManager.readFile();
		LinkedList<Player> lp = configManager.getListPlayer();

		for (int i = 0; i < lp.size(); i++) {
			if(!lp.get(i).isPlayed())
				Util.addJogadorListaOrdenadamente(lp.get(i).getPlayerName(), oListComboJogador);
		}

		//Define a lista de rounds do Torneio
		roundList = new LinkedList<Round>();
		roundManager = new RoundManager();
		//construindo lista de rounds
		roundManager.setRoundList(roundList);
		roundManager.setRoundListValues();

		//Definindo CellFactory da Listview das Rodadas
		listRodadas.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new MyListCell();
			}
		});

		//Alimentando valores na lista de rodadas
		for (int i = 0; i < roundList.size(); i++) {
			oListrRodadas.add(roundList.get(i).getRoundName());
		}

		currentRound = 0;
		listRodadas.setItems(oListrRodadas);
		listRodadas.getSelectionModel().select(currentRound);

		//Desabilita edi��o e sele��o da lista de rodadas
		listRodadas.setEditable(false);
		//listRodadas.setMouseTransparent(true);
		//listRodadas.setFocusTraversable(false);

		//Define Demais Listas
		listJogadores.setItems(oListJogadores);
		listMesa1.setItems(oListJogadoresMesa1);
		listMesa2.setItems(oListJogadoresMesa2);
		listFora.setItems(oListFora);
		listRebuys.setItems(oListRebuys);
		llMesa1 = new LinkedList<>();
		llMesa1BK = new LinkedList<>();
		llMesa2 = new LinkedList<>();
		llMesa2BK = new LinkedList<>();
		llMesa3 = new LinkedList<>();
		llMesa3BK = new LinkedList<>();

		//Inicializa tempos
        breakMinutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).getMinutesToBreak();
        seconds = 0;
        minutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).getMinutes();
        maxRound = minutes * Constants.SECONDS_IN_MINUTE;

        cbJogador.setItems(oListComboJogador);
        cbJogador.setPromptText("Jogador");

        //new Thread(updateGuitask).start();
        //Platform.setImplicitExit(false);

		updateGuitask = new Timeline();
		updateGuitask.setCycleCount(Timeline.INDEFINITE);
		updateGuitask.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				setRound();
				atualizarEstatisticas();
			}
		}));
		btAdicionaNaoInscrito.setVisible(false);
		updateGuitask.playFromStart();
		listMesa1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listMesa2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		LinkedList<String> stagePlayersList = configManager.getRegisteredPlayers();
		if (!stagePlayersList.isEmpty()){
			for (int i = 0; i < stagePlayersList.size(); i++) {
				cbJogador.editorProperty().get().setText(stagePlayersList.get(i));
				addJogador(null);
			}
		}
		boolean found;
		for (int i = 0; i < lp.size(); i++) {
			found = false;
			for (int j = 0; j < oListJogadores.size(); j++) {
				if(oListJogadores.get(j).equals(lp.get(i).getPlayerName()))
					found = true;
			}
			if(lp.get(i).isPlayed() && !found)
				oListComboJogador.add(lp.get(i).getPlayerName());
		}

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimerWindow.fxml"));
    	primaryStageTimer  = new Stage();
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();
			// obtem o controller da nova janela
			timerWindowController = fxmlLoader.<TimerWindowController> getController();
			// definindo a nova janela
			Scene scene = new Scene(myPane, 800, 600);
			primaryStageTimer.setScene(scene);
			primaryStageTimer.setTitle("Timer");
			primaryStageTimer.show();
			} catch(Exception e){
				e.printStackTrace();
			}
	}

    private void setRound(){

    	if (roundList.get(currentRound).getBigBlind() != 0)
            bigAtual.setText("" + roundList.get(currentRound).getBigBlind());
        else
        	bigAtual.setText("");

        if (roundList.get(currentRound).getSmallBlind()!= 0)
            smallAtual.setText("" + roundList.get(currentRound).getSmallBlind());
        else
        	smallAtual.setText("");

        if (roundList.get(currentRound).getAnte()!= 0){
            lbAnteAtual.setText("Ante:");
            valorAnteAtual.setText("" + roundList.get(currentRound).getAnte());
        }
        else
            if (roundList.get(currentRound).isBreakRound()){
            	lbAnteAtual.setText("");
            	valorAnteAtual.setText("BREAK");
            	}
            else{
            	valorAnteAtual.setText("");
            	lbAnteAtual.setText("Ante:");
            }

        if (currentRound < roundList.size()){
            if (roundList.get(currentRound+1).getBigBlind() != 0)
                bigSeguinte.setText("" + roundList.get(currentRound+1).getBigBlind());
            else
            	bigSeguinte.setText("");

            if (roundList.get(currentRound+1).getSmallBlind()!= 0)
                smallSeguinte.setText("" + roundList.get(currentRound+1).getSmallBlind());
            else
                smallSeguinte.setText("");

            if (roundList.get(currentRound+1).getAnte()!= 0){
                lbAnteSeguinte.setText("Ante:");
                valorAnteSeguinte.setText("" + roundList.get(currentRound+1).getAnte());
            }
            else
                if (roundList.get(currentRound+1).isBreakRound()){
                	lbAnteSeguinte.setText("");
                	valorAnteSeguinte.setText("BREAK");
                	}
                else{
                	lbAnteSeguinte.setText("Ante:");
                	valorAnteAtual.setText("");
                	}
        }
        else{
        	bigSeguinte.setText("");
        	smallSeguinte.setText("");
        	lbAnteSeguinte.setText(" ");
        	valorAnteSeguinte.setText("BREAK");
        }
        if(!listRodadas.isFocused()){
        	listRodadas.getFocusModel().focus(currentRound);
        	listRodadas.scrollTo(currentRound);
        	listRodadas.getSelectionModel().select(currentRound);
        }
    }

    public void restartTimer() {
        seconds = 0;
        currentSecond = 0;
        timerBar.setStyle("-fx-accent: #6699ff");

		listRodadas.getSelectionModel().select(currentRound);
		timerBar.setStyle("-fx-accent: #6699ff");

		minutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).getMinutes();
		breakMinutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex())
				.getMinutesToBreak();
		seconds = 0;

		maxRound = minutes * Constants.SECONDS_IN_MINUTE;
		setCurrentRound();
		currentSecond = 0;

        timerBar.setProgress(0);
    }

	private void timerAction() {
		if (!paused && play){
			if (seconds == 17 && minutes == 0) {
				playCountdown();
			}
			if (seconds == 2 && minutes == 0) {
				playFinish();
			}
			if (seconds == 0) {
				if (minutes > 0) {
					minutes--;
					breakMinutes--;
					seconds = 59;
					currentSecond = currentSecond + 1;
				} else {
					currentRound++;
					listRodadas.getSelectionModel().select(currentRound);
					timerBar.setStyle("-fx-accent: #6699ff");

					minutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).getMinutes();
					breakMinutes = roundList.get(listRodadas.getSelectionModel().getSelectedIndex())
							.getMinutesToBreak();
					seconds = 0;

					maxRound = minutes * Constants.SECONDS_IN_MINUTE;
					setCurrentRound();
					currentSecond = 0;
				}
			} else {
				seconds--;
				currentSecond = currentSecond + 1;
				if (minutes == 0 && seconds < 31) {
					timerBar.setStyle("-fx-accent: #ff4d4d");
				}
			}
			if (minutes < 10) {
				if (seconds < 10) {
					lbTimer.setText("0" + minutes + ":0" + seconds);
				} else {
					lbTimer.setText("0" + minutes + ":" + seconds);
				}
			} else {
				if (seconds < 10) {
					lbTimer.setText(minutes + ":0" + seconds);
				} else {
					lbTimer.setText(minutes + ":" + seconds);
				}
			}
			if(roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()){
				breakMinutes = 0;
				lbProximoBreak.setText("Break");
			}
			else
				setBreakTime();
		}
		double dMax, dcurr;
		dMax = maxRound;
		dcurr = currentSecond;

		timerBar.setProgress((dMax - (dMax - dcurr))/dMax);

		atualizarEstatisticas();
	}

	private void setBreakTime() {
        // Time to Break Update
        int tmpHours = breakMinutes / 60;

        if (tmpHours > 0) {
            int tmpMinutes = breakMinutes % 60;
            if (tmpMinutes < 10) {
                if (seconds < 10)
                    lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":" + seconds);
            } else {
                if (seconds < 10)
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":" + seconds);
            }
        } else {
            if (breakMinutes < 10) {
                if (seconds < 10)
                	lbProximoBreak.setText("0" + breakMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + breakMinutes + ":" + seconds);
            } else {
                if (seconds < 10)
                	lbProximoBreak.setText(breakMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText(breakMinutes + ":" + seconds);
            }
        }
    }

	private void setCurrentRound(){
		currentRound = listRodadas.getSelectionModel().getSelectedIndex();
		int lastRound = currentRound - 1;

		if(lastRound > 0)
			if(roundList.get(lastRound).isBreakRound() && roundList.get(lastRound).isRebuyAllowed())
				if(!oListRebuys.contains("========= BREAK " + roundList.get(lastRound).getBreakRoundNumber() +" ==========="))
					oListRebuys.add("========= BREAK " + roundList.get(lastRound).getBreakRoundNumber() +" ===========");

        if (roundList.get(currentRound).getBigBlind() != 0)
            bigAtual.setText("" + roundList.get(currentRound).getBigBlind());
        else
        	bigAtual.setText("");

        if (roundList.get(currentRound).getSmallBlind()!= 0)
            smallAtual.setText("" + roundList.get(currentRound).getSmallBlind());
        else
        	smallAtual.setText("");

        if (roundList.get(currentRound).getAnte()!= 0)
            lbAnteAtual.setText("" + roundList.get(currentRound).getAnte());
        else
            if (roundList.get(currentRound).getBigBlind() != 0)
            	lbAnteAtual.setText("  ");
            else{
            	lbAnteAtual.setText(" ");
            	valorAnteAtual.setText("BREAK");
            	}

        if (currentRound < Constants.MAX_ROUNDS){
            if (roundList.get(currentRound+1).getBigBlind() != 0)
                bigSeguinte.setText("" + roundList.get(currentRound+1).getBigBlind());
            else
            	bigSeguinte.setText("");

            if (roundList.get(currentRound+1).getSmallBlind()!= 0)
                smallSeguinte.setText("" + roundList.get(currentRound+1).getSmallBlind());
            else
            	smallSeguinte.setText("");

			if (roundList.get(currentRound + 1).getAnte() != 0)
				lbAnteSeguinte.setText("" + roundList.get(currentRound + 1).getAnte());
			else if (roundList.get(currentRound + 1).getBigBlind() != 0)
				lbAnteSeguinte.setText("  ");
			else {
				lbAnteSeguinte.setText(" ");
				valorAnteSeguinte.setText("BREAK");
			}
		}        else{
            bigSeguinte.setText("");
            smallSeguinte.setText("");
            lbAnteSeguinte.setText(" ");
            valorAnteSeguinte.setText("BREAK");
        }
        listRodadas.getFocusModel().focus(currentRound);
        listRodadas.scrollTo(currentRound);
    }

	public void atualizarEstatisticas(){
        int totalJogadores = (oListJogadores.size() + oListFora.size());
        int totalRebuy = oListRebuys.size();
        int totalJogando = oListJogadores.size();
        int totalFora = oListFora.size();
        int totalMesa1 = oListJogadoresMesa1.size();
        int totalMesa2 = oListJogadoresMesa2.size();
        int totalMesa3 = oListJogadoresMesa3.size();
        double totalArrecadado = 0;
        String jogadorSelecionado;
		if (lastInput > 0)
			lastInput--;
		else {
			inputName = "";
			currentSelection = 0;
		}

        if(listJogadores.getSelectionModel().getSelectedIndex() >= 0 ){
        	jogadorSelecionado = oListJogadores.get(listJogadores.getSelectionModel().getSelectedIndex());
        	lbJogadorSelecionado.setText(jogadorSelecionado);
			if (oListJogadoresMesa1.indexOf(jogadorSelecionado) >= 0) {
				listMesa1.getSelectionModel().clearSelection();
				listMesa1.getSelectionModel().select(oListJogadoresMesa1.indexOf(jogadorSelecionado));
				listMesa1.scrollTo(oListJogadoresMesa1.indexOf(jogadorSelecionado));
				listMesa2.getSelectionModel().clearSelection();
				listMesa3.getSelectionModel().clearSelection();
			}
			if (oListJogadoresMesa2.indexOf(jogadorSelecionado) >= 0) {
				listMesa2.getSelectionModel().clearSelection();
				listMesa2.getSelectionModel().select(oListJogadoresMesa2.indexOf(jogadorSelecionado));
				listMesa2.scrollTo(oListJogadoresMesa2.indexOf(jogadorSelecionado));
				listMesa1.getSelectionModel().clearSelection();
				listMesa3.getSelectionModel().clearSelection();
			}
			if (oListJogadoresMesa3.indexOf(jogadorSelecionado) >= 0) {
				listMesa3.getSelectionModel().clearSelection();
				listMesa3.getSelectionModel().select(oListJogadoresMesa3.indexOf(jogadorSelecionado));
				listMesa3.scrollTo(oListJogadoresMesa3.indexOf(jogadorSelecionado));
				listMesa1.getSelectionModel().clearSelection();
				listMesa2.getSelectionModel().clearSelection();
			}
        }
        lbMesa1.setText("Mesa 1 ("+ totalMesa1 + "/" + totalJogando +")");
        lbMesa2.setText("Mesa 2 ("+ totalMesa2 + "/" + totalJogando +")");
        lbMesa3.setText("Mesa 3 ("+ totalMesa3 + "/" + totalJogando +")");

        for (int i = 0; i < oListRebuys.size(); i++) {
        	if (oListRebuys.get(i).contains("==="))
        		totalRebuy--;
		}

        statsJogando.setText("" + totalJogando  + "/" + totalJogadores);
        statsFora.setText("" +  totalFora +  "/" + totalJogadores);
        statsRebuys.setText("" + totalRebuy);

        totalArrecadado = ((totalJogadores * Constants.BUY_IN_VALUE)
                + (totalRebuy * Constants.REBUY_VALUE));
        statsTotalArrecadado.setText("R$ " + totalArrecadado);

        double total1 = 0;
        double total2 = 0;
        double total3 = 0;
        double total4 = 0;
        double total5 = 0;

        switch (totalJogadores){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                total1 = totalArrecadado * 0.65;
                total2 = totalArrecadado * 0.35;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                total1 = totalArrecadado * 0.55;
                total2 = totalArrecadado * 0.30;
                total3 = totalArrecadado * 0.15;
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                total1 = totalArrecadado * 0.50;
                total2 = totalArrecadado * 0.25;
                total3 = totalArrecadado * 0.15;
                total4 = totalArrecadado * 0.10;
                break;
            default:
                total1 = totalArrecadado * 0.45;
                total2 = totalArrecadado * 0.25;
                total3 = totalArrecadado * 0.15;
                total4 = totalArrecadado * 0.10;
                total5 = totalArrecadado * 0.05;
                break;
        }
        total1l = Math.round(total1);
        total2l = Math.round(total2);
        total3l = Math.round(total3);
        total4l = Math.round(total4);
        total5l = Math.round(total5);

        statsTotalArrecadado.setText("R$ " + totalArrecadado);
        statsPremio1.setText("R$ " + total1l);
        statsPremio2.setText("R$ " + total2l);
        statsPremio3.setText("R$ " + total3l);
        statsPremio4.setText("R$ " + total4l);
        statsPremio5.setText("R$ " + total5l);
        if (totalJogando > 0)
        	statsMedia.setText(NumberMaskFormatter.formatMask(((totalJogadores + totalRebuy) * Constants.STACK)/totalJogando));

        if(timerWindowController != null){
	        timerWindowController.getLbTimer().setText(lbTimer.getText());
	        timerWindowController.getPbProgress().setProgress(timerBar.getProgress());
			timerWindowController.getLbBlindsAtual().setText(
					"Atual: " + smallAtual.getText() + "/" + bigAtual.getText() + " Ante: " + valorAnteAtual.getText());
			timerWindowController.getLbBlindsProxima().setText("Pr�xima: " + smallSeguinte.getText() + "/"
					+ bigSeguinte.getText() + " Ante: " + valorAnteSeguinte.getText());
			timerWindowController.getLbProxBreak().setText(lbProximoBreak.getText());
	        if(currentRound > Constants.LAST_BREAK_ROUND)
	        	timerWindowController.getLbProxBreak().setText("Pr�ximo Break: ");
	        timerWindowController.setTextLbJogando(statsJogando.getText());
	        timerWindowController.setTextLbRebuys(statsRebuys.getText());
	        timerWindowController.setTextLbFora(statsFora.getText());
	        timerWindowController.setTextLbMediaFichas(statsMedia.getText());
	        timerWindowController.setTextLbTotalArrecadado(statsTotalArrecadado.getText());
	        timerWindowController.setTextLbPos1(statsPremio1.getText());
	        timerWindowController.setTextLbPos2(statsPremio2.getText());
	        timerWindowController.setTextLbPos3(statsPremio3.getText());
	        timerWindowController.setTextLbPos4(statsPremio4.getText());
	        timerWindowController.setTextLbPos5(statsPremio5.getText());
        }
        if(currentRound > Constants.LAST_BREAK_ROUND)
        	lbProximoBreak.setText("");
    }

	@FXML
	private void abrirTimer(Event evt){
		if(primaryStageTimer == null){
	    	createNewTimerWindow();
		}
		else{
			if(!primaryStageTimer.isShowing()){
				primaryStageTimer = null;
				createNewTimerWindow();
			}
		}
	}

	private void createNewTimerWindow() {
		//obtem Loader
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimerWindow.fxml"));
		Stage primaryStageTimer  = new Stage();
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();
			// obtem o controller da nova janela
			timerWindowController = fxmlLoader.<TimerWindowController> getController();
			// definindo a nova janela
			Scene scene = new Scene(myPane, 800, 600);
			primaryStageTimer.setScene(scene);
			primaryStageTimer.setTitle("Timer");
			primaryStageTimer.show();
			} catch(Exception e){
				e.printStackTrace();
			}
	}

	@FXML
	private void mousePainelInferior(Event evt){
/*		hidePainelInferior = 0;
		hbInferior.setPrefHeight(HBox.USE_COMPUTED_SIZE);
		lbTimer.setFont(lbTimer.getFont().font("System", FontWeight.BOLD, 130));*/
	}

	@FXML
	private void btListaRebuyConsolidadoAction(Event evt){
/*
		//obtem Loader
    	FXMLLoader fxmlLoaderLista = new FXMLLoader(getClass().getResource("RebuysConsolidado.fxml"));
    	Stage primaryStage  = new Stage();
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoaderLista.load();

			ObservableList<RebuysConsolidadoLine> olRebuysConsolidado = FXCollections.observableArrayList();

			olRebuysConsolidado.add(new RebuysConsolidadoLine());
			olRebuysConsolidado.get(0).setNome("Teste");
			olRebuysConsolidado.get(0).setRebuys("2");
			olRebuysConsolidado.get(0).setPago("false");

			olRebuysConsolidado.add(new RebuysConsolidadoLine());
			olRebuysConsolidado.get(0).setNome("Teste2");
			olRebuysConsolidado.get(0).setRebuys("3");
			olRebuysConsolidado.get(0).setPago("false");

			//obtem o controller da nova janela
			rebuysConsolidadoController =  fxmlLoaderLista.<RebuysConsolidadoController>getController();

			//inclui as informa��es do texto a abre a janela nova
			rebuysConsolidadoController.setOlRebuysConsolidado(olRebuysConsolidado);
			rebuysConsolidadoController.getTvRebuysConsolidado().setItems(olRebuysConsolidado);
			rebuysConsolidadoController.getOlRebuysConsolidado();
			//definindo a nova janela
			Scene scene = new Scene(myPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista de Rebuys Consolidada");
			primaryStage.show();


			} catch(Exception e){
				e.printStackTrace();
			}
	*/
		LinkedList<String> lRebuys = new LinkedList<String>();
		int contRebuys;
		String currentPlayer;
		String sListRebuys = "";

		for (int i = 0; i < oListRebuys.size(); i++) {
			if(lRebuys.indexOf(oListRebuys.get(i)) < 0)
				lRebuys.add(oListRebuys.get(i));
		}

		for (int k = 0; k < lRebuys.size(); k++) {
			contRebuys = 0;
			currentPlayer = lRebuys.get(k);
			for (int j = 0; j < oListRebuys.size(); j++) {
				if (currentPlayer.equals(oListRebuys.get(j)))
					contRebuys++;
			}
			sListRebuys = sListRebuys + currentPlayer + " - " + contRebuys + "\n";
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lista de Rebuys Consolidada");
		alert.setHeaderText("Rebuys Consolidados");
		TextArea t = new TextArea();
		t.setFont(new Font("Courrier New", 12));
		t.setText(sListRebuys);
		alert.getDialogPane().setContent(t);
		alert.setWidth(800);
		alert.setHeight(600);
		alert.setResizable(true);
		alert.show();
		/*
        try {
			sender.sendMail(subject, msgHtml, configManager.getMailList(), true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public ObservableList<ProjecaoLine> ordenarProjecaoRodada(ObservableList<ProjecaoLine> l) {
		LinkedList<ProjecaoLine> lOrdered = new LinkedList<ProjecaoLine>();
		Double d1 = new Double(0.0);
		Double d2 = new Double(0.0);

		// prepara os dados do jogador e rodadas
		for (int i = 0; i < l.size(); i++) {
			ProjecaoLine p = l.get(i);

			if (lOrdered.isEmpty())
				lOrdered.add(p);
			else {
				for (int j = 0; j < lOrdered.size(); j++) {
					d1 = d1.parseDouble(lOrdered.get(j).getPosRodada());
					d2 = d2.parseDouble(p.getPosRodada());
					if (d2.equals(new Double(0.0))) {
						lOrdered.add(p);
						break;
					}
					if (d2 >= d1) {
						lOrdered.add(j, p);
						break;
					} else {
						if (d1 > d2 && j == (lOrdered.size() - 1)) {
							lOrdered.add(p);
							break;
						}
					}
				}
			}
		}
		l.clear();
		for (int i = 0; i < lOrdered.size(); i++) {
			lOrdered.get(i).setPosRodada(Util.completeZeros(i+1, 2) + "�/" + lOrdered.get(i).getPosRodada());
			l.add(lOrdered.get(i));
		}
		return l;
	}

    public void playCountdown() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("gate.wav"));
        player.start();
    }

    public void playRebuy() {
        Mp3Player player;
//        player = new Mp3Player(PokerTimerFXController.class.getResource("Death.wav"));
//        player.start();
        player = new Mp3Player(PokerTimerFXController.class.getResource("PowerUp.wav"));
        player.start();
    }

    public void playLastPlayer() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("DefeatBowser.wav"));
        player.start();
    }

    public void playCancelRebuy() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("Coin.wav"));
        player.start();
    }

    public void playCancelElimina() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("1up.wav"));
        player.start();
    }

    public void playElimina() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("GameOver.wav"));
        player.start();
    }

    public void playFinishTournament() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("WinStage.wav"));
        player.start();
    }

    public void playMario() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("MarioRiff.wav"));
        player.start();
    }

    public void playFinish() {
        Mp3Player player;
         player = new Mp3Player(PokerTimerFXController.class.getResource("Warning Siren-SoundBible.com-898272278.wav"));
        player.start();
    }

	public class MyListCell extends ListCell<String> {

	     public MyListCell() {
	     }

	     @Override protected void updateItem(String item, boolean empty) {
	         // calling super here is very important - don't skip this!
	         super.updateItem(item, empty);
	         // change the text fill based on whether it is positive (green)
	         // or negative (red). If the cell is selected, the text will
	         // always be white (so that it can be read against the blue
	         // background), and if the value is zero, we'll make it black.
	         if (item != null) {
	        	 if (item.equals("BREAK"))
	        		 setTextFill(Color.RED);
	        	 else
	        		 setTextFill(Color.BLACK);
	        	 super.setText(item);
	         }
	     }
	 }

	public ObservableList<String> getoListRebuys() {
		return oListRebuys;
	}

	public ObservableList<String> getoListFora() {
		return oListFora;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public ObservableList<String> getoListJogadores() {
		return oListJogadores;
	}

}

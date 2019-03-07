package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListsManager {
	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();
	private ObservableList<String> oListRebuys = FXCollections.observableArrayList();
	private ObservableList<String> oListFora = FXCollections.observableArrayList();
	private ObservableList<String> oListrRodadas = FXCollections.observableArrayList();
	private ObservableList<String> oListComboJogador = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa1 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa2 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa3 = FXCollections.observableArrayList();

	public ObservableList<String> getoListJogadores() {
		return oListJogadores;
	}

	public void setoListJogadores(ObservableList<String> oListJogadores) {
		this.oListJogadores = oListJogadores;
	}

	public ObservableList<String> getoListRebuys() {
		return oListRebuys;
	}

	public void setoListRebuys(ObservableList<String> oListRebuys) {
		this.oListRebuys = oListRebuys;
	}

	public ObservableList<String> getoListFora() {
		return oListFora;
	}

	public void setoListFora(ObservableList<String> oListFora) {
		this.oListFora = oListFora;
	}

	public ObservableList<String> getoListrRodadas() {
		return oListrRodadas;
	}

	public void setoListrRodadas(ObservableList<String> oListrRodadas) {
		this.oListrRodadas = oListrRodadas;
	}

	public ObservableList<String> getoListComboJogador() {
		return oListComboJogador;
	}

	public void setoListComboJogador(ObservableList<String> oListComboJogador) {
		this.oListComboJogador = oListComboJogador;
	}

	public ObservableList<String> getoListJogadoresMesa1() {
		return oListJogadoresMesa1;
	}

	public void setoListJogadoresMesa1(ObservableList<String> oListJogadoresMesa1) {
		this.oListJogadoresMesa1 = oListJogadoresMesa1;
	}

	public ObservableList<String> getoListJogadoresMesa2() {
		return oListJogadoresMesa2;
	}

	public void setoListJogadoresMesa2(ObservableList<String> oListJogadoresMesa2) {
		this.oListJogadoresMesa2 = oListJogadoresMesa2;
	}

	public ObservableList<String> getoListJogadoresMesa3() {
		return oListJogadoresMesa3;
	}

	public void setoListJogadoresMesa3(ObservableList<String> oListJogadoresMesa3) {
		this.oListJogadoresMesa3 = oListJogadoresMesa3;
	}
}

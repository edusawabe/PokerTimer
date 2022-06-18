package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimerWindowController implements Initializable{
	@FXML
	private Label lbTimer;
	@FXML
	private Label lbBlindsAtual;
	@FXML
	private Label lbBlindsProxima;
	@FXML
	private ProgressBar pbProgress;
	@FXML
	private Label lbProxBreak;
	@FXML
	private Label lbProxBreakHeader;
	@FXML
	private Label lbJogando;
	@FXML
	private Label lbRebuys;
	@FXML
	private Label lbFora;
	@FXML
	private Label lbMediaFichas;
	@FXML
	private Label lbTotalArrecadado;
	@FXML
	private Label lbPos1;
	@FXML
	private Label lbPos2;
	@FXML
	private Label lbPos3;
	@FXML
	private Label lbPos4;
	@FXML
	private Label lbPos5;
	@FXML
	private VBox vbStats;
	@FXML
	private VBox vbTimer;
	@FXML
	private AnchorPane mainPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void onScrollEvent(ScrollEvent evt){
		if(evt.getDeltaY() > 0){
			lbTimer.setFont(Font.font("System", FontWeight.BOLD, lbTimer.getFont().getSize() + 1));
			lbBlindsAtual.setFont(Font.font("System", FontWeight.BOLD, lbBlindsAtual.getFont().getSize() + 1));
			lbBlindsProxima.setFont(Font.font("System", FontWeight.BOLD, lbBlindsProxima.getFont().getSize() + 1));
			lbProxBreak.setFont(Font.font("System", FontWeight.BOLD, lbProxBreak.getFont().getSize() + 1));
			lbProxBreakHeader.setFont(Font.font("System", FontWeight.BOLD, lbProxBreakHeader.getFont().getSize() + 1));
			lbFora.setFont(Font.font("System", FontWeight.BOLD, lbFora.getFont().getSize() + 1));
			lbJogando.setFont(Font.font("System", FontWeight.BOLD, lbJogando.getFont().getSize() + 1));
			lbRebuys.setFont(Font.font("System", FontWeight.BOLD, lbRebuys.getFont().getSize() + 1));
			lbFora.setFont(Font.font("System", FontWeight.BOLD, lbFora.getFont().getSize() + 1));
			lbMediaFichas.setFont(Font.font("System", FontWeight.BOLD, lbMediaFichas.getFont().getSize() + 1));
			lbTotalArrecadado.setFont(Font.font("System", FontWeight.BOLD, lbTotalArrecadado.getFont().getSize() + 1));
			lbPos1.setFont(Font.font("System", FontWeight.BOLD, lbPos1.getFont().getSize() + 1));
			lbPos2.setFont(Font.font("System", FontWeight.BOLD, lbPos2.getFont().getSize() + 1));
			lbPos3.setFont(Font.font("System", FontWeight.BOLD, lbPos3.getFont().getSize() + 1));
			lbPos4.setFont(Font.font("System", FontWeight.BOLD, lbPos4.getFont().getSize() + 1));
			lbPos5.setFont(Font.font("System", FontWeight.BOLD, lbPos5.getFont().getSize() + 1));
		}
		if(evt.getDeltaY() < 0){
			lbTimer.setFont(Font.font("System", FontWeight.BOLD, lbTimer.getFont().getSize() - 1));
			lbBlindsAtual.setFont(Font.font("System", FontWeight.BOLD, lbBlindsAtual.getFont().getSize() - 1));
			lbBlindsProxima.setFont(Font.font("System", FontWeight.BOLD, lbBlindsProxima.getFont().getSize() - 1));
			lbProxBreak.setFont(Font.font("System", FontWeight.BOLD, lbProxBreak.getFont().getSize() - 1));
			lbProxBreakHeader.setFont(Font.font("System", FontWeight.BOLD, lbProxBreakHeader.getFont().getSize() - 1));
			lbFora.setFont(Font.font("System", FontWeight.BOLD, lbFora.getFont().getSize() - 1));
			lbJogando.setFont(Font.font("System", FontWeight.BOLD, lbJogando.getFont().getSize() - 1));
			lbRebuys.setFont(Font.font("System", FontWeight.BOLD, lbRebuys.getFont().getSize() - 1));
			lbFora.setFont(Font.font("System", FontWeight.BOLD, lbFora.getFont().getSize() - 1));
			lbMediaFichas.setFont(Font.font("System", FontWeight.BOLD, lbMediaFichas.getFont().getSize() - 1));
			lbTotalArrecadado.setFont(Font.font("System", FontWeight.BOLD, lbTotalArrecadado.getFont().getSize() - 1));
			lbPos1.setFont(Font.font("System", FontWeight.BOLD, lbPos1.getFont().getSize() - 1));
			lbPos2.setFont(Font.font("System", FontWeight.BOLD, lbPos2.getFont().getSize() - 1));
			lbPos3.setFont(Font.font("System", FontWeight.BOLD, lbPos3.getFont().getSize() - 1));
			lbPos4.setFont(Font.font("System", FontWeight.BOLD, lbPos4.getFont().getSize() - 1));
			lbPos5.setFont(Font.font("System", FontWeight.BOLD, lbPos5.getFont().getSize() - 1));
		}
	}

	public Label getLbTimer() {
		return lbTimer;
	}

	public Label getLbBlindsAtual() {
		return lbBlindsAtual;
	}

	public Label getLbBlindsProxima() {
		return lbBlindsProxima;
	}

	public ProgressBar getPbProgress() {
		return pbProgress;
	}

	public Label getLbProxBreak() {
		return lbProxBreak;
	}

	public void setTextLbJogando(String jogando){
		lbJogando.setText("Jogando: " + jogando);
	}

	public void setTextLbRebuys(String rebuys){
		lbRebuys.setText("Rebuys: " + rebuys);
	}

	public void setTextLbFora(String fora){
		lbFora.setText("Fora: " + fora);
	}

	public void setTextLbMediaFichas(String mediaFichas){
		lbMediaFichas.setText("M�dia: " + mediaFichas);
	}

	public void setTextLbTotalArrecadado(String totalArrecadado){
		lbTotalArrecadado.setText("Total: " + totalArrecadado);
	}

	public void setTextLbPos1(String pos1){
		lbPos1.setText("1�: " + pos1);
	}

	public void setTextLbPos2(String pos2){
		lbPos2.setText("2�: " + pos2);
	}

	public void setTextLbPos3(String pos3){
		lbPos3.setText("3�: " + pos3);
	}

	public void setTextLbPos4(String pos4){
		lbPos4.setText("4�: " + pos4);
	}

	public void setTextLbPos5(String pos5){
		lbPos5.setText("5�: " + pos5);
	}

	public AnchorPane getMainPane() {
		return mainPane;
	}
}

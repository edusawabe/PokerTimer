package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class AppConfigController implements Initializable {
	public AppConfigController() {
		super();
		buyin = 50;
		rebuy = 50;
		stack = 60000;
		premio1 = 0.45;
		premio2 = 0.25;
		premio3 = 0.15;
		premio4 = 0.10;
		premio5 = 0.05;
		rebuyLimit = 2;
		finalTableNumOfPlayers = 9;
	}

	@FXML
	private TextField tfBuyin;
	@FXML
	private TextField tfRebuy;
	@FXML
	private TextField tfStack;
	@FXML
	private TextField tfPremio1;
	@FXML
	private TextField tfPremio2;
	@FXML
	private TextField tfPremio3;
	@FXML
	private TextField tfPremio4;
	@FXML
	private TextField tfPremio5;
	@FXML
	private TextField tfBolha;
	@FXML
	private TextField tfRebuyLimit;
	@FXML
	private TextField tfFinalTablePlayers;

	private double buyin;
	private double rebuy;
	private int stack;
	private double premio1;
	private double premio2;
	private double premio3;
	private double premio4;
	private double premio5;
	private double bolha;
	private int rebuyLimit;
	private int finalTableNumOfPlayers;

	public void setBuyin(double buyin) {
		this.buyin = buyin;
	}

	public void setRebuy(double rebuy) {
		this.rebuy = rebuy;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public void setPremio1(double premio1) {
		this.premio1 = premio1;
	}

	public void setPremio2(double premio2) {
		this.premio2 = premio2;
	}

	public void setPremio3(double premio3) {
		this.premio3 = premio3;
	}

	public void setPremio4(double premio4) {
		this.premio4 = premio4;
	}

	public void setPremio5(double premio5) {
		this.premio5 = premio5;
	}

	public void setBolha(double bolha) {
		this.bolha = bolha;
	}

	public double getBuyin() {
		return buyin;
	}

	public double getRebuy() {
		return rebuy;
	}

	public int getStack() {
		return stack;
	}

	public double getPremio1() {
		return premio1;
	}

	public double getPremio2() {
		return premio2;
	}

	public double getPremio3() {
		return premio3;
	}

	public double getPremio4() {
		return premio4;
	}

	public double getPremio5() {
		return premio5;
	}

	public double getBolha() {
		return bolha;
	}

	public int getRebuyLimit() {
		return rebuyLimit;
	}

	public int getFinalTableNumOfPlayers() {
		return finalTableNumOfPlayers;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfBuyin.setText(""+buyin);
		tfRebuy.setText(""+rebuy);
		tfStack.setText(""+stack);
		tfPremio1.setText(""+premio1);
		tfPremio2.setText(""+premio2);
		tfPremio3.setText(""+premio3);
		tfPremio4.setText(""+premio4);
		tfPremio5.setText(""+premio5);
		tfBolha.setText(""+bolha);
		tfRebuyLimit.setText(""+rebuyLimit);
		tfFinalTablePlayers.setText(""+finalTableNumOfPlayers);
	}

	@FXML
	private void cancelar(Event evt) {
		buyin = 50;
		rebuy = 50;
		stack = 60000;
		premio1 = 0.45;
		premio2 = 0.25;
		premio3 = 0.15;
		premio4 = 0.10;
		premio5 = 0.05;
		bolha = 0;
		rebuyLimit = 2;
		finalTableNumOfPlayers = 9;

		tfBuyin.setText(""+buyin);
		tfRebuy.setText(""+rebuy);
		tfStack.setText(""+stack);
		tfPremio1.setText(""+premio1);
		tfPremio2.setText(""+premio2);
		tfPremio3.setText(""+premio3);
		tfPremio4.setText(""+premio4);
		tfPremio5.setText(""+premio5);
		tfBolha.setText(""+bolha);
		tfRebuyLimit.setText(""+rebuyLimit);
		tfFinalTablePlayers.setText(""+finalTableNumOfPlayers);
	}

	@FXML
	private void salvar(Event evt) {
		buyin = Double.parseDouble(tfBuyin.getText());
		rebuy = Double.parseDouble(tfRebuy.getText());
		stack = Integer.parseInt(tfStack.getText());
		premio1 = Double.parseDouble(tfPremio1.getText());
		premio2 = Double.parseDouble(tfPremio2.getText());
		premio3 = Double.parseDouble(tfPremio3.getText());
		premio3 = Double.parseDouble(tfPremio4.getText());
		premio3 = Double.parseDouble(tfPremio5.getText());
		bolha = Double.parseDouble(tfBolha.getText());
		rebuyLimit = Integer.parseInt(tfRebuyLimit.getText());
		finalTableNumOfPlayers = Integer.parseInt(tfFinalTablePlayers.getText());
	}
}


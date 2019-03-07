package application;

import java.util.LinkedList;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControlZManager {
	private LinkedList<ListsManager> lListManager = new LinkedList<ListsManager>();
	int actualState = 0;

	public void controlZ() {
		if(actualState > 0)
			actualState--;
		if(actualState == 0){
			Alert al = new Alert(AlertType.INFORMATION);
			al.setTitle("Fim Control Z");
			al.setContentText("Fim do Control Z");
		}
	}

	public void controlY() {
		if(actualState < lListManager.size())
			actualState++;
		if(actualState == lListManager.size()){
			Alert al = new Alert(AlertType.INFORMATION);
			al.setTitle("Fim Control Y");
			al.setContentText("Fim do Control Y");
		}
	}

	public ListsManager getCurrentListManager() {
		return lListManager.get(actualState);
	}

	public LinkedList<ListsManager> getlListManager() {
		return lListManager;
	}

	public void saveState(){
		ListsManager actual = getlListManager().get(actualState);
		if(actualState == 25){
			lListManager.remove(0);
			getlListManager().add(actual);
		}
	}
}

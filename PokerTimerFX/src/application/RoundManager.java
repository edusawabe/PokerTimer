package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import model.Round;
import util.Constants;
import util.NumberMaskFormatter;

public class RoundManager {
	private LinkedList<Round> roundList;
	private File roundFile;

	public RoundManager(){
		roundFile = new File("configRodadas.csv");
	}

	public void setRoundListValues() {
		LinkedList<String[]> lRounds = new LinkedList<String[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(roundFile));
			String line = null;
			String[] split = null;
			line = reader.readLine();
			while (line != null){
				split = line.split(";");
				lRounds.add(split);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			return;
		}

		Round round;
		int lastBreak = 0;
		int roundNum = 0;
		int numBreak = 0;

		for (String[] sRound : lRounds) {
			round = new Round();
			//Linha de definição de rodada
			if(sRound.length > 2){
				round.setSmallBlind(Integer.parseInt(sRound[1]));
				round.setBigBlind(Integer.parseInt(sRound[2]));
				round.setAnte(Integer.parseInt(sRound[3]));
				round.setBreakRound(false);
				round.setMinutes(Integer.parseInt(sRound[4]));
				if (numBreak < 2){
					round.setRebuyAllowed(true);
				} else {
					round.setRebuyAllowed(false);
				}
			//Linha de Break;
			} else {
				round.setSmallBlind(0);
				round.setBigBlind(0);
				round.setAnte(0);
				round.setBreakRound(true);
				round.setMinutes(Integer.parseInt(sRound[1]));
				round.setRoundName("BREAK");
				if (numBreak < 2){
					round.setRebuyAllowed(true);
				} else {
					round.setRebuyAllowed(false);
				}
				numBreak++;
			}
			if (round.isBreakRound()) {
				round.setRoundName("BREAK");
			} else {
				roundNum++;
				round.setRoundName("Rodada " + roundNum + ": " + NumberMaskFormatter.formatMask(round.getSmallBlind())
						+ "/" + NumberMaskFormatter.formatMask(round.getBigBlind()) + " - Ante: " + round.getAnte());
			}
			roundList.add(round);

		}

		for (int i = 0; i < roundList.size(); i++){
			if(roundList.get(i).isBreakRound()){
				while (lastBreak < i) {
					for (int j = lastBreak; j < i; j++) {
						roundList.get(lastBreak).setMinutesToBreak(roundList.get(lastBreak).getMinutesToBreak() + roundList.get(j).getMinutes());
					}
					lastBreak++;
				}
				lastBreak++;
			}
		}
	}

	public LinkedList<Round> getRoundList() {
		return roundList;
	}

	public void setRoundList(LinkedList<Round> roundList) {
		this.roundList = roundList;
	}
}

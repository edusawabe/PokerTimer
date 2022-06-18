package application;

import java.util.LinkedList;

import model.Player;
import util.ExcelManager;
import util.Util;

public class ExcelRankingGenerator {
	private LinkedList<Player> lPlayer;
	private ExcelManager excelManager;

	public ExcelRankingGenerator(){
		excelManager = new ExcelManager();
	}

	public void generateExcel() {
		double pontuacao;
		excelManager.setFileName("./RankingPontuacao.xlsx");
		excelManager.getlConlumns().clear();
		excelManager.getlRowValue().clear();

		excelManager.getlConlumns().add("Jogador");
		excelManager.getlConlumns().add("Jan");
		excelManager.getlConlumns().add("Fev");
		excelManager.getlConlumns().add("Mar");
		excelManager.getlConlumns().add("Abr");
		excelManager.getlConlumns().add("Mai");
		excelManager.getlConlumns().add("Jun");
		excelManager.getlConlumns().add("Jul");
		excelManager.getlConlumns().add("Ago");
		excelManager.getlConlumns().add("Set");
		excelManager.getlConlumns().add("Out");
		excelManager.getlConlumns().add("Nov");
		excelManager.getlConlumns().add("Dez");

		for (int i = 0; i < lPlayer.size(); i++) {
			excelManager.getlRowValue().add(new LinkedList<String>());
			excelManager.getlRowValue().getLast().add(lPlayer.get(i).getPlayerName());
			pontuacao = 0;

			for (int j = 0; j < lPlayer.get(i).getResultados().size(); j++) {
				pontuacao = pontuacao + Double.parseDouble("" + lPlayer.get(i).getResultados().get(j).getPontuacaoEtapa());
				excelManager.getlRowValue().getLast().add("" + pontuacao);
			}
		}
		excelManager.generateExcelFile();
	}

	public void generateStatisticsExcel() {
		double pontuacao;
		excelManager.setFileName("./Estatisticas.xlsx");
		excelManager.getlConlumns().clear();
		excelManager.getlRowValue().clear();

		excelManager.getlConlumns().add("Jogador");
		excelManager.getlConlumns().add("Total Premio");
		excelManager.getlConlumns().add("Total Gasto");
		excelManager.getlConlumns().add("Pontuacao Total");
		excelManager.getlConlumns().add("Total Rebuys");
		excelManager.getlConlumns().add("Total AddOn");
		excelManager.getlConlumns().add("Max Rebuy no Mes");
		excelManager.getlConlumns().add("Media Rebuy");
		excelManager.getlConlumns().add("Meses com Rebuy");
		excelManager.getlConlumns().add("Vezes na Final Table");
		excelManager.getlConlumns().add("Qtde 1o");
		excelManager.getlConlumns().add("Qtde 2o");
		excelManager.getlConlumns().add("Qtde 3o");
		excelManager.getlConlumns().add("Qtde 4o");
		excelManager.getlConlumns().add("Qtde 5o");
		excelManager.getlConlumns().add("Qtde Top 3");
		excelManager.getlConlumns().add("Maior Pontuacao");
		excelManager.getlConlumns().add("Menor Pontuacao");
		excelManager.getlConlumns().add("2a Menor Pontuacao");
		excelManager.getlConlumns().add("Media Pontuacao");
		excelManager.getlConlumns().add("Qtde Falta");


		for (int i = 0; i < lPlayer.size(); i++) {
			excelManager.getlRowValue().add(new LinkedList<String>());
			excelManager.getlRowValue().getLast().add(lPlayer.get(i).getPlayerName());
			excelManager.getlRowValue().getLast().add("" + Util.arredondar(lPlayer.get(i).getStatistic().getTotalPrize()));
			excelManager.getlRowValue().getLast().add("" + Util.arredondar(lPlayer.get(i).getStatistic().getTotalCost()));
			excelManager.getlRowValue().getLast().add("" + Util.arredondar(lPlayer.get(i).getStatistic().getTotalScore()));
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getTotalRebuy());
			//excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getT());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getMaxRebuy());
			excelManager.getlRowValue().getLast().add("" +  Util.arredondar(lPlayer.get(i).getStatistic().getAvgRebuy()));
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNumMonthRebuy());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNumFinalTable());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNum1pos());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNum2pos());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNum3pos());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNum4pos());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNum5pos());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNumTop3());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getMaxScore());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getMinScore());
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getMin2Score());
			excelManager.getlRowValue().getLast().add("" + Util.arredondar(lPlayer.get(i).getStatistic().getAvgScore()));
			excelManager.getlRowValue().getLast().add("" + lPlayer.get(i).getStatistic().getNumFault());

		}
		excelManager.generateExcelFile();
	}

	public LinkedList<Player> getlPlayer() {
		return lPlayer;
	}

	public void setlPlayer(LinkedList<Player> lPlayer) {
		this.lPlayer = lPlayer;
	}
}
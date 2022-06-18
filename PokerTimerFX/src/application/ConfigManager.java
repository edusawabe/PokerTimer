/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.JogadorConfigFile;
import model.Player;
import model.ProjecaoLine;
import model.ResultadoRodada;
import util.Constants;
import util.DateUtil;
import util.Util;

/**
 *
 * @author eduardo.sawabe
 */
public class ConfigManager {
    private String configFileName;
    private String actualStateFileName;
    private String playersFileName;
    private LinkedList<Player> listPlayer;
    private LinkedList<String> registeredPlayers;
    private static Logger logger = Logger.getLogger(ConfigManager.class);

    public LinkedList<Player> getListPlayer() {
		return listPlayer;
	}

	public void setListPlayer(LinkedList<Player> listPlayer) {
		this.listPlayer = listPlayer;
	}

	//public void readFile(ListView<String> jltJogando){
	public void readFile(){
		File confgFile  = new File(configFileName);
		boolean playerAdded = false;
		if(!confgFile.exists()){
			try {
				listPlayer = new LinkedList<Player>();
				confgFile.createNewFile();
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(confgFile), "windows-1252");
				writer.write("#Jogadores\n#Jogadores");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
        BufferedReader reader;
        String[] results;
        listPlayer = new LinkedList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("#Jogadores"))
                    line = reader.readLine();
                if (line == null)
                    break;
                while (!line.contains("#Jogadores")){
                    JogadorConfigFile j = new JogadorConfigFile();
                    j.parseFileLine(line);
                    Player p = new Player();
                    p.setPlayed(false);
                    p.setPlayerName(j.getNome());
                    p.setPlayerMail(j.getEmail());
                    results = j.getResults();
                    for (int i = 0; i < results.length; i++) {
                    	p.getResultados().add(new ResultadoRodada(results[i]));
                    	if(!(p.getResultados().get(i).getColocacao().equals("00")) && !(p.getResultados().get(i).getColocacao().equals("0")))
                    			p.setPlayed(true);
					}
                    listPlayer.add(p);
                    //((ObservableList<String>) jltJogando.getItems()).add(p.getPlayerName());
                    line = reader.readLine();
                    if (line == null)
                        break;
                }
            }
            reader.close();

            if(listPlayer.size() > 0){
            	LinkedList<Player> ltmp = new LinkedList<Player>();
            	for (int i = 0; i < listPlayer.size(); i++) {
            		playerAdded = false;
					for (int j = 0; j < ltmp.size(); j++) {
						if(listPlayer.get(i).getPlayerName().compareTo(ltmp.get(j).getPlayerName()) < 0){
							ltmp.add(j,listPlayer.get(i));
							playerAdded = true;
							break;
						}
					}
					if(!playerAdded){
						ltmp.add(listPlayer.get(i));
					}
				}
            	listPlayer.clear();
            	listPlayer.addAll(ltmp);
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
    }

	public void getPlayers(){
        File confgFile  = new File(configFileName);
        BufferedReader reader;
        String[] results;
        listPlayer = new LinkedList<Player>();
        int[] lQtdeJogadoresRodada = new int[12];
        int[] lQtderebuysRodada = new int[12];
        int[] lQtdeAddOnRodada = new int[12];

        for (int i = 0; i < lQtderebuysRodada.length; i++) {
        	lQtdeJogadoresRodada[i] = 0;
        	lQtderebuysRodada[i]    = 0;
        	lQtdeAddOnRodada[i]     = 0;
		}

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("#Jogadores"))
                    line = reader.readLine();
                if (line == null)
                    break;
                while (!line.contains("#Jogadores")){
                    JogadorConfigFile j = new JogadorConfigFile();
                    j.parseFileLine(line);
                    Player p = new Player();
                    p.setPlayerName(j.getNome());
                    p.setPlayerMail(j.getEmail());
                    results = j.getResults();
                    for (int i = 0; i < results.length; i++) {
                    	p.getResultados().add(new ResultadoRodada(results[i]));
                    	if(!(p.getResultados().get(i).getColocacao().equals("0") || p.getResultados().get(i).getColocacao().equals("00")))
                    		lQtdeJogadoresRodada[i]++;
                    		lQtderebuysRodada[i] += p.getResultados().get(i).getRebuys();
                    		lQtdeAddOnRodada[i]  += p.getResultados().get(i).getAddOn();
					}
                    listPlayer.add(p);
                    line = reader.readLine();
                    if (line == null)
                        break;
                }
                reader.close();
                for (int i = 0; i < listPlayer.size(); i++) {
                	for (int j = 0; j < lQtdeJogadoresRodada.length; j++) {
                		listPlayer.get(i).getResultados().get(j).setQtdeJogadores(lQtdeJogadoresRodada[j]);
                		listPlayer.get(i).getResultados().get(j).setQtderebuysEtapa(lQtderebuysRodada[j]);
                		listPlayer.get(i).getResultados().get(j).setQtdeAddOnEtapa(lQtdeAddOnRodada[j]);
					}
                	listPlayer.get(i).updatePontuacaoTotal();
				}
                return;
            }
            reader.close();

        } catch (FileNotFoundException ex) {
        	logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
	}

	public void addPlayer(String player){
		String complemento = "; ;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;0@0@0@0.00@0.00;";
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		String[] configBackup = configFileName.split(Constants.CONFIG_FILE_NAME_SPLIT);
        BufferedReader reader;
        OutputStreamWriter writer;
        OutputStreamWriter writer2;
        JogadorConfigFile j = new JogadorConfigFile();
        boolean newPlayer = true;
        int cont = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            while (line != null) {
				backupLine += line + "\n";
				if (line.contains("#Jogadores"))
					cont++;
				else
					j.parseFileLine(line);

				if (cont == 2) {
					if(newPlayer)
						readLines = readLines + player + complemento + "\n" + line + "\n";
					cont++;
				} else {
					if(j.getNome().equals(player))
						newPlayer = false;
					readLines += line + "\n";
				}
				line = reader.readLine();
			}
            reader.close();
			if (newPlayer) {
				writer = new OutputStreamWriter(new FileOutputStream(confgFile), "windows-1252");
				writer2 = new OutputStreamWriter(new FileOutputStream(new File (configBackup[0] + dateFormat.format(date) + ".txt")), "windows-1252");
				writer.write(readLines);
				writer.flush();
				writer.close();
				writer2.write(backupLine);
				writer2.flush();
				writer2.close();
			}
        } catch (FileNotFoundException ex) {
        	logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
	}

	/*
	public void updateFile(LinkedList<Player> lPlayer){
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		String[] configBackup = configFileName.split(Constants.CONFIG_FILE_NAME_SPLIT);
        BufferedReader reader;
        BufferedWriter writer;
        BufferedWriter writer2;
        int cont = 0;
        JogadorConfigFile j = new JogadorConfigFile();
        String[] results;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            while (line != null) {
				backupLine += line + "\n";
				readLines = readLines + line + "\n";
				if(cont > 1){
					line = reader.readLine();
					if (line == null)
						break;
				}
				if (line.contains("#Jogadores")) {
					cont++;
					line = reader.readLine();
					while (line != null && (!line.contains("#Jogadores")) && cont < 2) {
						j.parseFileLine(line);
						Player p = new Player();
						p.setPlayerName(j.getNome());
						p.setPlayerMail(j.getEmail());
						results = j.getResults();
						int rebuys = 0, pos = 0;
						for (int i = 0; i < oListRebuys.size(); i++) {
							if(oListRebuys.get(i).equals(p.getPlayerName()))
								rebuys++;
						}
						for (int i = 0; i < oListFora.size(); i++) {
							if(oListFora.get(i).equals(p.getPlayerName())){
								pos = oListFora.size() - i;
								break;
							}
						}
						for (int i = 0; i < results.length; i++) {
							ResultadoRodada r = new ResultadoRodada();
							r.getResultadoFromFileLine(results[i]);
							if ((i+1) == mesEtapa){
								r.setColocacao(Util.completeZeros(pos, 1));
								r.setRebuys(rebuys);
								switch (pos) {
								case 1:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(total1l);
									break;
								case 2:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(total2l);
									break;
								case 3:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(total3l);
									break;
								case 4:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(total4l);
									break;
								case 5:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(total5l);
									break;
								default:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos,oListRebuys.size()));
									r.setPremiacao(0.00);
									break;
								}
								results[mesEtapa -1] = r.getResultLine();
							}
							else
								if ((i+1) >  mesEtapa)
									break;
						}
						readLines = readLines + j.generateFileLine() + "\n";
						line = reader.readLine();
						if(!line.contains("#Jogadores"))
							backupLine += line + "\n";
					}
				}
			}
            reader.close();

            writer = new BufferedWriter(new FileWriter(confgFile));
            writer2 = new BufferedWriter(new FileWriter(configBackup[0] + dateFormat.format(date) + ".txt"));
            writer.write(readLines);
            writer.flush();
            writer.close();
            writer2.write(backupLine);
            writer2.flush();
            writer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	*/

	public void updatePlayersResult(ObservableList<String> oListFora, ObservableList<String> oListRebuys, ObservableList<String> oListAddOn,  double total1l
			, double total2l, double total3l, double total4l, double total5l){
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		int mesEtapa = Integer.parseInt(DateUtil.getDate().substring(3, 5));
		String[] configBackup = configFileName.split(Constants.CONFIG_FILE_NAME_SPLIT);
        BufferedReader reader;
        OutputStreamWriter writer;
        OutputStreamWriter writer2;
        int cont = 0;
        LinkedList<Player> lPlayer = new LinkedList<Player>();
        JogadorConfigFile j = new JogadorConfigFile();
        String[] results;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            while (line != null) {
				backupLine += line + "\n";
				readLines = readLines + line + "\n";
				if(cont > 1){
					line = reader.readLine();
					if (line == null)
						break;
				}
				if (line.contains("#Jogadores")) {
					cont++;
					line = reader.readLine();
					while (line != null && (!line.contains("#Jogadores")) && cont < 2) {
						j.parseFileLine(line);
						Player p = new Player();
						p.setPlayerName(j.getNome());
						p.setPlayerMail(j.getEmail());
						results = j.getResults();
						int rebuys = 0, addOn = 0, pos = 0;
						for (int i = 0; i < oListRebuys.size(); i++) {
							if(oListRebuys.get(i).equals(p.getPlayerName()))
								rebuys++;
						}
						for (int i = 0; i < oListAddOn.size(); i++) {
							if(oListAddOn.get(i).equals(p.getPlayerName()))
								addOn++;
						}
						for (int i = 0; i < oListFora.size(); i++) {
							if(oListFora.get(i).equals(p.getPlayerName())){
								pos = oListFora.size() - i;
								break;
							}
						}
						for (int i = 0; i < results.length; i++) {
							ResultadoRodada r = new ResultadoRodada();
							r.getResultadoFromFileLine(results[i]);
							if ((i+1) == mesEtapa){
								r.setColocacao(Util.completeZeros(pos, 1));
								r.setRebuys(rebuys);
								r.setAddOn(addOn);
								r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), pos, oListRebuys.size(), oListAddOn.size()));
								switch (pos) {
								case 1:
									r.setPremiacao(total1l);
									break;
								case 2:
									r.setPremiacao(total2l);
									break;
								case 3:
									r.setPremiacao(total3l);
									break;
								case 4:
									r.setPremiacao(total4l);
									break;
								case 5:
									r.setPremiacao(total5l);
									break;
								default:
									r.setPremiacao(0.00);
									break;
								}
								results[mesEtapa -1] = r.getResultLine();
							}
							else
								if ((i+1) >  mesEtapa)
									break;
						}
						readLines = readLines + j.generateFileLine() + "\n";
						line = reader.readLine();
						if(!line.contains("#Jogadores"))
							backupLine += line + "\n";
					}
				}
			}
            reader.close();

			writer = new OutputStreamWriter(new FileOutputStream(confgFile), "windows-1252");
			writer2 = new OutputStreamWriter(new FileOutputStream(new File (configBackup[0] + dateFormat.format(date) + ".txt")), "windows-1252");

            writer.write(readLines);
            writer.flush();
            writer.close();
            writer2.write(backupLine);
            writer2.flush();
            writer2.close();
        } catch (FileNotFoundException ex) {
        	logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
	}


	public ObservableList<ProjecaoLine> projetarResultado(ObservableList<String> oListRebuys, ObservableList<String> oListAddOn,
			ObservableList<String> oListFora, ObservableList<String> oListJogadores, int totalJogadores, int colocaocaoPersonilizada, int rebuysMais, String jogadorRebuys) {
		ObservableList<ProjecaoLine> projecaoList = FXCollections.observableArrayList();
		ObservableList<ProjecaoLine> projecaoListOrdered = FXCollections.observableArrayList();
		int mesEtapa = Integer.parseInt(DateUtil.getDate().substring(6, 7));
		int posAtual = 0;
		boolean jogando = false;
		double soma = 0;

		// Faz a leitura dos jogadores do arquivo de configura��o
		getPlayers();
		// seta a lista com os jogadores lidos
		LinkedList<Player> lPlayer = getListPlayer();

		double projecao = 0;
		// Pega cada jogador do arquivo de configura��o
		// verificar se est� jogando
		// quantidade de rebuys dele
		// e a posi��o atual
		for (int l = 0; l < lPlayer.size(); l++) {
			Player p = lPlayer.get(l);
			ArrayList<ResultadoRodada> aResults = p.getResultados();
			jogando = false;

			posAtual = 0;
			projecao = 0;
			// Obtem a posicao atual
			for (int i = 0; i < oListFora.size(); i++) {
				if (oListFora.get(i).equals(p.getPlayerName())) {
					posAtual = totalJogadores - i;
					break;
				}
			}

			int rebuys = 0;
			// Obtem a quantidade de rebuys
			for (int i = 0; i < oListRebuys.size(); i++) {
				if (oListRebuys.get(i).equals(p.getPlayerName())) {
					rebuys++;
				}
			}

			int addOn = 0;
			// Obtem a quantidade de addOn
			for (int i = 0; i < oListAddOn.size(); i++) {
				if (oListAddOn.get(i).equals(p.getPlayerName())) {
					addOn++;
				}
			}

			// verifica jogador jogando
			for (int i = 0; i < oListFora.size(); i++) {
				if (oListFora.get(i).equals(p.getPlayerName())) {
					jogando = true;
					break;
				}
			}

			if (!jogando) {
				// Obtem a posicao atual
				for (int i = 0; i < oListJogadores.size(); i++) {
					if (oListJogadores.get(i).equals(p.getPlayerName())) {
						jogando = true;
						break;
					}
				}
			}

			if (p.getPlayerName().equals(jogadorRebuys)){
				rebuys = rebuys + addOn + rebuysMais;
			}

			ProjecaoLine pl = new ProjecaoLine();
			pl.setJogador(p.getPlayerName());
			pl.setAtual(Util.completeZerosDouble(p.getPontuacaoTotal(), 3));

			soma = 0;
			for (int k = 0; k < 15; k++) {
				if(jogando){
					projecao = Util
							.arredondar(getPontuacaoJogadorEtapa(totalJogadores, k + 1, oListRebuys.size(), oListAddOn.size())
									+ p.getPontuacaoTotal());
				}
				else{
					projecao = Util
							.arredondar(0.00
									+ p.getPontuacaoTotal());
				}
				switch (k + 1) {
				case 1:
					pl.setProjecao1(Util.completeZerosDouble(projecao, 3));
					break;
				case 2:
					pl.setProjecao2(Util.completeZerosDouble(projecao, 3));
					break;
				case 3:
					pl.setProjecao3(Util.completeZerosDouble(projecao, 3));
					break;
				case 4:
					pl.setProjecao4(Util.completeZerosDouble(projecao, 3));
					break;
				case 5:
					pl.setProjecao5(Util.completeZerosDouble(projecao, 3));
					break;
				case 6:
					pl.setProjecao6(Util.completeZerosDouble(projecao, 3));
					break;
				case 7:
					pl.setProjecao7(Util.completeZerosDouble(projecao, 3));
					break;
				case 8:
					pl.setProjecao8(Util.completeZerosDouble(projecao, 3));
					break;
				case 9:
					pl.setProjecao9(Util.completeZerosDouble(projecao, 3));
					break;
				case 10:
					pl.setProjecao10(Util.completeZerosDouble(projecao, 3));
					break;
				case 11:
					pl.setProjecao11(Util.completeZerosDouble(projecao, 3));
					break;
				case 12:
					pl.setProjecao12(Util.completeZerosDouble(projecao, 3));
					break;
				case 13:
					pl.setProjecao13(Util.completeZerosDouble(projecao, 3));
					break;
				case 14:
					pl.setProjecao14(Util.completeZerosDouble(projecao, 3));
					break;
				default:
					pl.setProjecao15(Util.completeZerosDouble(projecao, 3));
					break;
				}
			}
			if(jogando){
				pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "�" + " / "
						+ getPontuacaoJogadorEtapa(totalJogadores, posAtual, oListRebuys.size(), oListAddOn.size()));
				soma = getPontuacaoJogadorEtapa(totalJogadores, posAtual, oListRebuys.size(), oListAddOn.size());
				soma = soma + p.getPontuacaoTotal();
				pl.setPosRodada("" + Util.completeZerosDouble(Util.arredondar(soma), 3));
				projecao = Util
						.arredondar(getPontuacaoJogadorEtapa(totalJogadores, colocaocaoPersonilizada, oListRebuys.size(), oListAddOn.size())
								+ p.getPontuacaoTotal());
				pl.setProjecaoCustom(Util.completeZerosDouble(projecao, 3));
			}else{
				pl.setNestaRodada("");
				soma = 0.00;
				soma = soma + p.getPontuacaoTotal();
				pl.setPosRodada("" + Util.completeZerosDouble(Util.arredondar(soma), 3));
				projecao = 0.00;
				pl.setProjecaoCustom(Util.completeZerosDouble(projecao, 3));
			}
			projecaoList.add(pl);
		}

		Double itemOrdenado;
		Double itemLista;

		for (int l1 = 0; l1 < projecaoList.size(); l1++) {
			if (l1 == 0) {
				projecaoListOrdered.add(projecaoList.get(l1));
			} else {
				itemLista = new Double(projecaoList.get(l1).getAtual());
				for (int l2 = 0; l2 < projecaoListOrdered.size(); l2++) {
					itemOrdenado = new Double(projecaoListOrdered.get(l2).getAtual());
					if (itemOrdenado.compareTo(itemLista) <= 0) {
						projecaoListOrdered.add(l2, projecaoList.get(l1));
						break;
					} else {
						if (Double.parseDouble(projecaoListOrdered.get(l2).getAtual()) == 0
								&& Double.parseDouble(projecaoList.get(l1).getAtual()) < 0) {
							projecaoListOrdered.add(l2, projecaoList.get(l1));
							break;
						}
						if (itemLista.compareTo(new Double("0")) == 0) {
							projecaoListOrdered.add(projecaoList.get(l1));
							break;
						}
						if (itemOrdenado.compareTo(itemLista) > 0 && l2 == (projecaoListOrdered.size() - 1)) {
							projecaoListOrdered.add(projecaoList.get(l1));
							break;
						}
					}
				}
			}
		}

		for (int k = 0; k < projecaoListOrdered.size(); k++) {
			int h = k + 1;
			if (h < 10)
				projecaoListOrdered.get(k).setJogador("0" + h + "� - " + projecaoListOrdered.get(k).getJogador());
			else
				projecaoListOrdered.get(k).setJogador(h + "� - " + projecaoListOrdered.get(k).getJogador());
		}

		return projecaoListOrdered;
	}


	private void readPlayers() {
		File f = new File(playersFileName);
		f.getAbsolutePath();
		registeredPlayers = new LinkedList<String>();
		boolean playerExists;
		if(f.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(playersFileName),"Cp1252"));
	            String line = reader.readLine();
	            while(line != null){
	            	registeredPlayers.add(line);
	            	playerExists = false;
	            	if(listPlayer.isEmpty()){
	            		addPlayer(line);
	            	}
	            	for (int i = 0; i < listPlayer.size(); i++) {
	            		if(listPlayer.get(i).getPlayerName().equals(line)){
	            			playerExists = true;
	            		}
	            		if(!playerExists){
	            			addPlayer(line);
	            		}
					}
	            	line = reader.readLine();
	            }
	            reader.close();
			} catch (FileNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void readActualStateFile() {
		File f = new File(actualStateFileName);
		if(f.exists()){

		}
		else
			readPlayers();
	}

	/*
	 * Pontos = A + B + C + D
			A: Pontos pela posi��o inversa:
				( 3 * qtde de jogadores ) � ( 3 * (posi��o-1) )
			B: Pontos pelo pr�mio recebido
				( 0,6 * pr�mio recebido em dinheiro )
			C: Cada um dos 8 jogadores da mesa final ganha 20 pontos
			D: Cada Rebuy realizado vale -15 pontos
	 */
	/*
	public double getPontuacaoJogadorEtapa(int qtdJogadores, int rebuys, int addOn, int pos, double premio){
		double resultado = 0;
		if (pos > 0){
			// A:
			resultado = ((3 * qtdJogadores) - (3 * (pos - 1)));

			// B:
			resultado = resultado + (premio * 0.6);

			// C:
			if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
				resultado = resultado + 20.00;

			// D:
			resultado = resultado + ((rebuys+addOn) * (-15.00));
		}

		BigDecimal bd = new BigDecimal(resultado);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    resultado = bd.doubleValue();

		return resultado;
	}
	*/

	/*
	 */
	public double getPontuacaoJogadorEtapa(int qtdJogadores, int rebuys, int addOn, int pos, int qtdeRebuysRodada, int qtdeAddOnRodada){
		double resultado = 0;

		if (pos > 0){
			// A Pontos pela posi��o inversa:
			resultado = ((5 * qtdJogadores) - (5 * (pos - 1)));

			// C Bonus Mesa Final:
			// B Pontos pela quantidade de Rebuys / Add-ons na Rodada:
			switch (pos) {
			case 1:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 0;
				resultado = resultado + 10*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 2:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 0;
				resultado = resultado + 8*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 3:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 0;
				resultado = resultado + 6*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 4:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 16.00;
				resultado = resultado + 4*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 5:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 14.00;
				resultado = resultado + 3*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 6:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 12.00;
				resultado = resultado + 3*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 7:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 10.00;
				resultado = resultado + 3*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 8:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 8.00;
				resultado = resultado + 3*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			case 9:
				if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
					resultado = resultado + 6.00;
				resultado = resultado + 3*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			default:
				resultado = resultado + 1*((qtdeRebuysRodada + qtdeAddOnRodada) - (rebuys + addOn));
				break;
			}

			// D:
			switch (rebuys + addOn) {
			case 0:
				resultado = resultado + 30.0;
				break;
			case 1:
				resultado = resultado + 20.0;
				break;
			case 2:
				resultado = resultado + 10.0;
				break;
			default:
				resultado = resultado + 0;
				break;
			}
		}

		BigDecimal bd = new BigDecimal(resultado);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    resultado = bd.doubleValue();

		return resultado;
	}

	/*
	 * Nova regra de pontuacao 2021
	 * Pontuacao fixa por posicao mais a quantidade de pessoas na rodada
	 */
	public double getPontuacaoJogadorEtapa(int qtdeJogadores, int posicao, int qtdeRebuys, int qtdeAddOn){
		double resultado = 0;
		if (posicao == 0)
			return resultado;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("configPontuacao.csv")));
			String consideraRebuy = reader.readLine();
			String consideraAddOn = reader.readLine();
			HashMap<String,Integer> mapPontuacao = new HashMap<String,Integer>();
			String line = reader.readLine();
			String[] tmp;
			while (line != null){
				tmp = line.split(";");
				mapPontuacao.put(tmp[0], new Integer(tmp[1]));
				line = reader.readLine();
			}
			reader.close();
			if (consideraRebuy.contains("Sim")){
				qtdeJogadores = qtdeJogadores + qtdeRebuys;
			}
			if (consideraAddOn.contains("Sim")){
				qtdeJogadores = qtdeJogadores + qtdeAddOn;
			}
			return qtdeJogadores + mapPontuacao.get(""+posicao).intValue();
		} catch (IOException e) {
			logger.error(e);
		}

		return resultado;
	}

	/*
	 * Pontos = A + B + C + D
			A: Pontos pela posicao inversa:
				( 3 * qtde de jogadores ) + ( 3 * (posicao-1) )
			B: Pontos pelo premio recebido
				( 0,6 * premio recebido em dinheiro )
			C: Cada um dos 8 jogadores da mesa final ganha 20 pontos
			D: Cada Rebuy realizado vale -15 pontos
	 */
	public double getPontuacaoJogadorEtapa(ResultadoRodada r){
		int qtdJogadores;
		int rebuys;
		int addOn;
		int pos;
		int rebuysEtapa;
		int addOnEtapa;
		double premio;

		pos = Integer.parseInt(r.getColocacao());
		qtdJogadores = r.getQtdeJogadores();
		rebuys = r.getRebuys();
		addOn = r.getAddOn();
		premio = r.getPremiacao();
		rebuysEtapa = r.getQtderebuysEtapa();
		addOnEtapa  = r.getQtdeAddOnEtapa();
		//return getPontuacaoJogadorEtapa(qtdJogadores, rebuys, addOn, pos, rebuysEtapa, addOnEtapa);
		//Nova Pontuacao 2021
		return getPontuacaoJogadorEtapa(qtdJogadores, pos, rebuysEtapa, addOnEtapa);
	}

	public String getMailList(){
        String ret = new String();
        for (int i = 0; i < listPlayer.size(); i++) {
            Player get = listPlayer.get(i);
            if (!(get.getPlayerMail().isEmpty()))
            	if (!(get.getPlayerMail().equals(" ")))
            		ret = ret + get.getPlayerMail() + ";";
        }
        ret = ret.substring(0, ret.length()-1);
        return ret;
    }
    /**
     * @return the configFileName
     */
    public String getConfigFileName() {
        return configFileName;
    }

    /**
     * @param configFileName the configFileName to set
     */
    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

	public String getActualStateFileName() {
		return actualStateFileName;
	}

	public void setActualStateFileName(String actualStateFileName) {
		this.actualStateFileName = actualStateFileName;
	}

	public String getPlayersFileName() {
		return playersFileName;
	}

	public void setPlayersFileName(String playersFileName) {
		this.playersFileName = playersFileName;
	}

	public LinkedList<String> getRegisteredPlayers() {
		return registeredPlayers;
	}
}

package br.usp.rssfeedreader;

public class Categorias {
	private static int ID_DCC = 62;
	private static int ID_MAE = 37;
	private static int ID_MAT = 21;
	private static int ID_MAP = 29;

	public static int getIdByName(String dp) {
		if(dp.contains("DCC"))
			return ID_DCC;
		if(dp.contains("MAE"))
			return ID_MAE;
		if(dp.contains("MAP"))
			return ID_MAP;
		return ID_MAT;
	}
}

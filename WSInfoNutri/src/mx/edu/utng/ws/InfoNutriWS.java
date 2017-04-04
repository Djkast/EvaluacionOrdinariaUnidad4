package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InfoNutriWS {
	
	private Connection connection;
	private Statement statement;
	private ResultSet resulset;
	private PreparedStatement ps;
	
	public InfoNutriWS(){
		conectar();
	}
	
	private void conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
						"jdbc:postgresql://localhost:5432/wstest",
						"postgres","admin");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addInfoNutri(InfoNutri infoNutri){
		int result = 0;
		if(connection != null){
			try {
				ps = connection.prepareStatement("INSERT INTO info_nutri(contenido_energetico, proteinas, lipidos, "
						+ "grasa_saturada, colesterol, carbohidratos, azucares, fibra_dietetica, sodio, calcio) VALUES "
						+ "(?,?,?,?,?,?,?,?,?,?);");
				ps.setString(1, infoNutri.getContenidoEnergetico());
				ps.setInt(2, infoNutri.getProteinas());
				ps.setInt(3, infoNutri.getLipidos());
				ps.setInt(4, infoNutri.getGrasaSaturada());
				ps.setInt(5, infoNutri.getColesterol());
				ps.setInt(6, infoNutri.getCarbohidratos());
				ps.setInt(7, infoNutri.getAzucares());
				ps.setInt(8, infoNutri.getFibraDietetica());
				ps.setString(9, infoNutri.getSodio());
				ps.setString(10, infoNutri.getCalcio());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				
			}
		}
		return result;
	}
	
	public int editInfoNutri(InfoNutri infoNutri){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE info_nutri SET contenido_energetico = ?, proteinas = ?, lipidos = ?, "
						+ "grasa_saturada = ?, colesterol = ?, carbohidratos = ?, azucares = ?, "
						+ "fibra_dietetica = ?, sodio = ?, calcio = ?"
						+ "WHERE id = ?;");
				ps.setString(1, infoNutri.getContenidoEnergetico());
				ps.setInt(2, infoNutri.getProteinas());
				ps.setInt(3, infoNutri.getLipidos());
				ps.setInt(4, infoNutri.getGrasaSaturada());
				ps.setInt(5, infoNutri.getColesterol());
				ps.setInt(6, infoNutri.getCarbohidratos());
				ps.setInt(7, infoNutri.getAzucares());
				ps.setInt(8, infoNutri.getFibraDietetica());
				ps.setString(9, infoNutri.getSodio());
				ps.setString(10, infoNutri.getCalcio());
				ps.setInt(11, infoNutri.getId());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int removeInfoNutri(int id){
		int result = 0;
		if(connection != null){
			try {
				ps = connection.prepareStatement("DELETE FROM info_nutri WHERE id = ?;");
				ps.setInt(1, id);
				result = ps.executeUpdate();
			} catch (SQLException e) {
				
			}
		}
		return result;
	}
	
	public InfoNutri[] getInfoNutris(){
		InfoNutri[] result = null;
		List<InfoNutri> informaciones = new ArrayList<InfoNutri>();
		if(connection != null){
			try {
				statement = connection.createStatement();
				resulset = statement.executeQuery(
						"SELECT * FROM info_nutri;");
				while(resulset.next()){
					InfoNutri infoNutri = new InfoNutri(
							resulset.getInt("id"),
							resulset.getString("contenido_energetico"),
							resulset.getInt("proteinas"),
							resulset.getInt("lipidos"),
							resulset.getInt("grasa_saturada"),
							resulset.getInt("colesterol"),
							resulset.getInt("carbohidratos"),
							resulset.getInt("azucares"),
							resulset.getInt("fibra_dietetica"),
							resulset.getString("sodio"),
							resulset.getString("calcio"));
					informaciones.add(infoNutri);
				}
			} catch (SQLException e) {
				
			}
		}
		result = informaciones.toArray(new InfoNutri[informaciones.size()]);
		return result;
	}
	
	public InfoNutri getInfoNutriByid(int id){
		InfoNutri infoNutri = null;
		if(connection != null){
			try {
				ps = connection.prepareStatement("SELECT * FROM info_nutri WHERE id = ?;");
				ps.setInt(1, id);
				resulset = ps.executeQuery();
				if(resulset.next()){
					infoNutri = new InfoNutri(
							resulset.getInt("id"),
							resulset.getString("contenido_energetico"),
							resulset.getInt("proteinas"),
							resulset.getInt("lipidos"),
							resulset.getInt("grasa_saturada"),
							resulset.getInt("colesterol"),
							resulset.getInt("carbohidratos"),
							resulset.getInt("azucares"),
							resulset.getInt("fibra_dietetica"),
							resulset.getString("sodio"),
							resulset.getString("calcio"));
				}
			} catch (SQLException e) {
				
			}
		}
		return infoNutri;
	}
	
}
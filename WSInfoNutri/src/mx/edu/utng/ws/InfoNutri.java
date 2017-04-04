package mx.edu.utng.ws;

public class InfoNutri {
	private int id;
	private String contenidoEnergetico;
	private int proteinas;
	private int lipidos;
	private int grasaSaturada;
	private int colesterol;
	private int carbohidratos;
	private int azucares;
	private int fibraDietetica;
	private String sodio;
	private String calcio;
	public InfoNutri(int id, String contenidoEnergetico, int proteinas, int lipidos, int grasaSaturada, int colesterol,
			int carbohidratos, int azucares, int fibraDietetica, String sodio, String calcio) {
		super();
		this.id = id;
		this.contenidoEnergetico = contenidoEnergetico;
		this.proteinas = proteinas;
		this.lipidos = lipidos;
		this.grasaSaturada = grasaSaturada;
		this.colesterol = colesterol;
		this.carbohidratos = carbohidratos;
		this.azucares = azucares;
		this.fibraDietetica = fibraDietetica;
		this.sodio = sodio;
		this.calcio = calcio;
	}
	
	public InfoNutri(){
		this(0,"",0,0,0,0,0,0,0,"","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenidoEnergetico() {
		return contenidoEnergetico;
	}

	public void setContenidoEnergetico(String contenidoEnergetico) {
		this.contenidoEnergetico = contenidoEnergetico;
	}

	public int getProteinas() {
		return proteinas;
	}

	public void setProteinas(int proteinas) {
		this.proteinas = proteinas;
	}

	public int getLipidos() {
		return lipidos;
	}

	public void setLipidos(int lipidos) {
		this.lipidos = lipidos;
	}

	public int getGrasaSaturada() {
		return grasaSaturada;
	}

	public void setGrasaSaturada(int grasaSaturada) {
		this.grasaSaturada = grasaSaturada;
	}

	public int getColesterol() {
		return colesterol;
	}

	public void setColesterol(int colesterol) {
		this.colesterol = colesterol;
	}

	public int getCarbohidratos() {
		return carbohidratos;
	}

	public void setCarbohidratos(int carbohidratos) {
		this.carbohidratos = carbohidratos;
	}

	public int getAzucares() {
		return azucares;
	}

	public void setAzucares(int azucares) {
		this.azucares = azucares;
	}

	public int getFibraDietetica() {
		return fibraDietetica;
	}

	public void setFibraDietetica(int fibraDietetica) {
		this.fibraDietetica = fibraDietetica;
	}

	public String getSodio() {
		return sodio;
	}

	public void setSodio(String sodio) {
		this.sodio = sodio;
	}

	public String getCalcio() {
		return calcio;
	}

	public void setCalcio(String calcio) {
		this.calcio = calcio;
	}

	@Override
	public String toString() {
		return "InfoNutri [id=" + id + ", contenidoEnergetico=" + contenidoEnergetico + ", proteinas=" + proteinas
				+ ", lipidos=" + lipidos + ", grasaSaturada=" + grasaSaturada + ", colesterol=" + colesterol
				+ ", carbohidratos=" + carbohidratos + ", azucares=" + azucares + ", fibraDietetica=" + fibraDietetica
				+ ", sodio=" + sodio + ", calcio=" + calcio + "]";
	}
	
	
}
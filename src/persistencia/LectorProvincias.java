package persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelo.Arista;
import modelo.Provincia;

public class LectorProvincias {

	private final Gson gson;

	public LectorProvincias() {
		gson = new Gson();
	}

	public List<Provincia> cargarProvincias() throws IOException {

		InputStream input = getClass().getClassLoader().getResourceAsStream("persistencia/provincias.json");

		if (input == null) {
			throw new IOException("No se encontró el archivo");
		}

		try (Reader reader = new InputStreamReader(input)) {

			return gson.fromJson(reader, new TypeToken<List<Provincia>>() {
			}.getType());
		}
	}

	public List<Arista> cargarAristas() throws IOException {

		InputStream input = getClass().getClassLoader().getResourceAsStream("persistencia/provinciasLimitrofes.json");

		if (input == null) {
			throw new IOException("No se encontró el archivo");
		}

		try (Reader reader = new InputStreamReader(input)) {

			return gson.fromJson(reader, new TypeToken<List<Arista>>() {
			}.getType());
		}
	}
}
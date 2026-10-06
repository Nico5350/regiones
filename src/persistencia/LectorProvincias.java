package persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelo.Provincia;

public class LectorProvincias {

	public List<Provincia> cargarArchivo() throws IOException {

		InputStream input = getClass().getClassLoader().getResourceAsStream("persistencia/provincias.json");

		if (input == null) {
			throw new IOException("No se encontró persistencia/provincias.json");
		}

		try (Reader reader = new InputStreamReader(input)) {

			Gson gson = new Gson();

			return gson.fromJson(reader, new TypeToken<List<Provincia>>() {
			}.getType());
		}
	}
}
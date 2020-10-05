package integrationtest.bouchons;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
public class JsonBouchon {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> List<T> fromFileToList(T classe, String path, Class<?> context)
			throws JsonParseException, JsonMappingException, IOException {
		JavaType type = mapper.getTypeFactory().constructParametricType(List.class, (Class<List<T>>) classe);
		return (List<T>) mapper.readValue(context.getClassLoader().getResourceAsStream(path), type);
	}

	public static Object fromFileToObject(Class<?> classe, String path, Class<?> context)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(context.getClassLoader().getResourceAsStream(path), classe);
	}

}

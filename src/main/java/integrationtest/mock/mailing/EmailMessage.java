package integrationtest.mock.mailing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {

	private String body;

	private String headers;

}

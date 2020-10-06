package integrationtest.mock.mailing;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {

	private String body;

	private String headers;

}

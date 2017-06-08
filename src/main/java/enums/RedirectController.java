package enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedirectController {
	SCHEDULE ("redirect:/schedule"),
	HOME ("redirect:/home"),
	INDEX ("redirect:/index"),
	SHARECONFIG("redirect:/shareconfig");
	;

	final String redirectName;
}

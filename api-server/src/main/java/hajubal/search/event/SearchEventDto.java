package hajubal.search.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class SearchEventDto {
    private final String query;
}

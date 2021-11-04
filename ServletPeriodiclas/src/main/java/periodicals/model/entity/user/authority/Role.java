package periodicals.model.entity.user.authority;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import periodicals.exception.RoleHasNoEndpointsException;
import periodicals.model.entity.user.authority.AccessMatcher;

public enum Role {
    GUEST{
        @Override
        public List<String> getEndpoints() {
            return Stream.of(AccessMatcher.PERMIT_ALL.stream(),
                    AccessMatcher.ANONYMOUS.stream())
                    .reduce(Stream::concat)
                    .orElseThrow(RoleHasNoEndpointsException::new)
                    .collect(Collectors.toList());
        }
    },
    READER{
        @Override
        public List<String> getEndpoints() {
            return Stream.of(AccessMatcher.PERMIT_ALL.stream(),
                            AccessMatcher.FULLY_AUTHENTICATED.stream(),
                            AccessMatcher.AUTHORITY_READER.stream())
                    .reduce(Stream::concat)
                    .orElseThrow(RoleHasNoEndpointsException::new)
                    .collect(Collectors.toList());
        }
    },
    ADMINISTRATOR{
        @Override
        public List<String> getEndpoints() {
            return Stream.of(AccessMatcher.PERMIT_ALL.stream(),
                            AccessMatcher.FULLY_AUTHENTICATED.stream(),
                            AccessMatcher.AUTHORITY_ADMINISTRATOR.stream())
                    .reduce(Stream::concat)
                    .orElseThrow(RoleHasNoEndpointsException::new)
                    .collect(Collectors.toList());
        }
    };

    //private List<String> endpoints;
    public abstract List<String> getEndpoints();
}

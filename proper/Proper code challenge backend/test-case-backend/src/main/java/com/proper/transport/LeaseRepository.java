package com.proper.transport;

import com.proper.domain.Lease;
import java.util.Optional;

public interface LeaseRepository {
  //No need to implement this class, use it as you were given the implementation
  Optional<Lease> fetch(String id) throws RepositoryException;
}

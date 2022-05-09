package guru.springframework.sfgjms.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
public class HelloWorldMessage implements Serializable {

  static final long serialVersionUID = 4962955953346156186L;

  private UUID id;
  private String message;
}

package guru.springframework.sfgjms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloWorldMessage implements Serializable {

  static final long serialVersionUID = 4962955953346156186L;

  private UUID id;
  private String message;
}

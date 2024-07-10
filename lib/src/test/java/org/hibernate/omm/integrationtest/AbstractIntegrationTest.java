package org.hibernate.omm.integrationtest;

import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractIntegrationTest {

  protected TransitionWalker.ReachedState<RunningMongodProcess> running;

  @BeforeEach
  void setUp() {
    running = Mongod.instance().start(Version.Main.V4_4);
  }

  @AfterEach
  void tearDown() {
    running.close();
  }
}

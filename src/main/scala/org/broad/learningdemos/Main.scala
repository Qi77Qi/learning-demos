package org.broad.learningdemos

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    LearningdemosServer.stream[IO].compile.drain.as(ExitCode.Success)
}

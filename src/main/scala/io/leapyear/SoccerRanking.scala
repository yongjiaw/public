package io.leapyear

import java.io.{BufferedReader, FileReader, OutputStream, OutputStreamWriter}

object SoccerRanking {

  case class Team(name: String)

  case class Game(team1: Team, score1: Int, team2: Team, score2: Int)

  case class TeamPoints(team: Team, points: Int)

  def teamPoints(games: Iterable[Game]): Iterable[TeamPoints] = {
    games.flatMap {
      case Game(team1, score1, team2, score2) =>
        if (score1 > score2) {
          Seq(
            TeamPoints(team1, 3),
            TeamPoints(team2, 0)
          )
        } else if (score1 == score2) {
          Seq(
            TeamPoints(team1, 1),
            TeamPoints(team2, 1)
          )
        } else {
          Seq(
            TeamPoints(team1, 0),
            TeamPoints(team2, 3)
          )
        }
    }.groupBy(_.team).map {
      case (team, teamPoints) => TeamPoints(team, teamPoints.map(_.points).sum)
    }
  }

  // TODO
  // better error message
  def parseInput(fileName: String): Iterator[Game] = {
    import scala.collection.JavaConverters._
    // example
    // Lions 3, Snakes 3
    // be most strict about the regex to begin with
    val InputPattern = """([a-zA-Z\s]+) ([0-9]+), ([a-zA-Z\s]+) ([0-9]+)""".r
    new BufferedReader(new FileReader(fileName)).lines().iterator().asScala.map {
      case InputPattern(team1, score1, team2, score2) =>
        Game(Team(team1), score1.toInt, Team(team2), score2.toInt)
      case other => throw new Exception(s"'$other' does not match $InputPattern")
    }
  }

  def teamPointsOutput(teamPoints: Iterable[TeamPoints], output: OutputStream): Unit = {
    val outputStreamWriter = new OutputStreamWriter(output)

    teamPoints.toSeq
      .sortBy {
        case TeamPoints(team, points) => (- points, team.name)
      }.zipWithIndex.foreach {
      case (TeamPoints(Team(team), points), idx) =>
        //example
        // 1. Tarantulas, 6 pts
        outputStreamWriter.write(s"${idx + 1}. $team, $points pts\n")
    }
    outputStreamWriter.close()
  }

  // TODO
  // 1. support more scalable IO options such as GCS, S3, Bigquery, HDFS, etc.
  // 2. support more scalable computation framework with the scala IO, such as Apache Beam, Spark, etc.
  // 3. command line help
  def main(args: Array[String]): Unit = {

    teamPointsOutput(
      teamPoints(parseInput(args(0)).toIterable),
      Console.out
    )
  }
}

package io.leapyear

import io.leapyear.SoccerRanking.{Game, Team, TeamPoints, teamPoints}
import org.scalatest.flatspec.AnyFlatSpec

class SoccerRankingTest extends AnyFlatSpec {
  // focus on testing the core logic
  // ranking and formatting should be in another test if needed
  "Team points calculation" should "pass" in {
    teamPoints(
      Seq(
        Game(Team("Lions"), 3, Team("Snakes"), 3),
        Game(Team("Tarantulas"), 1, Team("FC Awesome"), 0),
        Game(Team("Lions"), 1, Team("FC Awesome"), 1),
        Game(Team("Tarantulas"), 3, Team("Snakes"), 1),
        Game(Team("Lions"), 4, Team("Grouches"), 0)
      )
    ).toSet ==
      Set(
        TeamPoints(Team("Tarantulas"), 6),
        TeamPoints(Team("Lions"), 5),
        TeamPoints(Team("FC Awesome"), 1),
        TeamPoints(Team("Snakes"), 1),
        TeamPoints(Team("Grouches"), 0)
      )
  }
}

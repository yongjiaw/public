#!/bin/bash

# need to install sbt first, refer to https://www.scala-sbt.org/1.x/docs/index.html
sbt --warn "runMain io.leapyear.SoccerRanking $1"

# Marshmallow Java Backend Test

Your task is to write a Java based web service that navigates an imaginary robotic cleaner through an oil spill in the sea.

## Context
* Sea area dimensions as [X and Y coordinates](https://en.wikipedia.org/wiki/Cartesian_coordinate_system), identifying the top right corner of the area rectangle. 
This area is divided up into a grid using these dimensions; an area that has dimensions `X: 5` and `Y: 5` has 5 columns and 5 rows, so 25 possible cleaner positions. 
The bottom left corner is the point of origin for our coordinate system, so the bottom left corner of the area is defined by `X: 0` and `Y: 0`.
* Locations of patches of oil, also defined by `X` and `Y` coordinates identifying the bottom left corner of those grid positions.
* The initial cleaner position (X and Y coordinates like patches of oil)
* Navigation instructions (as [cardinal directions](https://en.wikipedia.org/wiki/Cardinal_direction) where e.g. `N` and `E` mean "go north" and "go east" respectively)
* The tide does not impact this simulation - the patches of oil remain in the same place throughout the execution of the program.
* The area will be rectangular, has no obstacles and all locations in the area will be clean (cleaning has no effect) except for the locations of the patches of oil presented in the program input.
* Navigating the cleaner onto a patch of oil removes the oil so that patch is then clean for the remainder of the program run. 
The cleaner is always on - there is no need to enable it.
* In the program input attempts to navigate the cleaner outside of the boundary of the defined area an appropriate error should be returned.

## Goal

The goal of the service is to take the area dimensions, the locations of the oil patches, the initial location of the cleaner and the navigation instructions as input and to then output the following:

* The final cleaner position (X, Y)
* The number of patches of oil the robot cleaned up

## Input

Program input will be received in a JSON payload with the format described here.

Example:

```json
{
  "areaSize" : [5, 5],
  "startingPosition" : [1, 2],
  "oilPatches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "navigationInstructions" : "NNESEESWNWW"
}
```

## Output

Service output must be returned as JSON.

Example (matching the input above):

```json
{
  "finalPosition" : [1, 3],
  "oilPatchesCleaned" : 1
}
```
Where `finalPosition` is the final coordinates of the cleaner and `oilPatchesCleaned` is the number of cleaned patches.

## Deliverable

We've set up a basic Spring Boot application with Maven on the latest Spring Boot and Java versions for you.

⚠️ Please DO NOT change the versions as it might break the automated tests we run once you submit your work.

The service:

* is a web service
* must be written using the provided Java and Spring Boot versions
* can make use of any existing open source libraries that don't directly address the problem statement (use your best judgement).

We require:

* The full source code, including any code written which is not part of the normal program run (scripts, tests).
* Clear instructions on how to build and run the program in the README file.
* Please provide any deliverables and instructions by pushing to the master branch of this repository.

## Evaluation

We will especially consider:

* Code organisation
* Quality
* Readability
* Solving of the problem

This test is based on the following gist https://gist.github.com/alirussell/9a519e07128b7eafcb50
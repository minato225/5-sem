cmake_minimum_required(VERSION 3.17)
project(17_12)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fopenmp")

add_executable(17_12 main.cpp t.cpp)


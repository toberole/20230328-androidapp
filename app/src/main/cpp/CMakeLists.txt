cmake_minimum_required(VERSION 3.22.1)

project("app")

include_directories(${CMAKE_SOURCE_DIR}/include)

aux_source_directory(${CMAKE_CURRENT_LIST_DIR} ALL_SOURCE)

add_library(app
        SHARED
        ${ALL_SOURCE})

find_library(log-lib
        log
        )

target_link_directories(
        app
        PUBLIC
        ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI}
)

target_link_libraries(app
        ${log-lib}
        wasmedge
)


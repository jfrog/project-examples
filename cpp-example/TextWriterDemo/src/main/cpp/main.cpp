/* 
 * File:   main.cpp
 * Author: gidis
 *
 * Created on December 29, 2012, 12:22 PM
 */

#include <cstdlib>

#include "TextWriterDemo.h"

using namespace std;

/*
 * The main function creates an instance of TextWriterDemo run it.
 */
int main(int argc, char** argv) {
    TextWriterDemo* demo=new TextWriterDemo();
    demo->run();
    return 0;
}


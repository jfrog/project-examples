/* 
 * File:   TextWriterDemo.cpp
 * Author: gidis
 * 
 * Created on December 29, 2012, 12:23 PM
 */

#include "TextWriterDemo.h"

TextWriterDemo::TextWriterDemo() {
}

TextWriterDemo::TextWriterDemo(const TextWriterDemo& orig) {
}

/**
 * An example of TextWriter usage
 */
void TextWriterDemo:: run(){
    demo.writeLine("writing first line");
    demo.writeLine("writing second line");
    demo.append("appending text to second line");
    std::string path="text.writer.demo.out";
    demo.writeLine(path);
//    if(textScanner.contains("where","here")){
//        demo.writeLine("String found");
//    } else {
//        demo.writeLine("String not found");
//    }
    demo.writeToConsole();
}

TextWriterDemo::~TextWriterDemo() {
}


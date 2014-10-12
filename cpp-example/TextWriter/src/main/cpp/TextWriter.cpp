/* 
 * File:   TextWriter.cpp
 * Author: gidis
 * 
 * Created on December 29, 2012, 12:23 PM
 */

#include "TextWriter.h"
#include <string>
#include <iostream>
#include <fstream>

TextWriter::TextWriter() {
    //buffer=new std::string;
}

TextWriter::TextWriter(const TextWriter& orig) {
}

void TextWriter::writeLine(std::string text){
    if(buffer.empty()!=true){
        buffer.append("\n");
    }
    append(text);

}

void TextWriter::append(std::string text){
    buffer.append(text);
}

void TextWriter::writeToConsole(){
    std::cout<<buffer;
}

void TextWriter::writeToFile(std::string path){
    std::ofstream theStream;
    theStream.open(path.c_str());
    theStream << buffer.c_str() << std::endl;
    theStream.close(); 
}

TextWriter::~TextWriter() {
}


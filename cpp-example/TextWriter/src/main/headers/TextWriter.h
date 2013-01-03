/* 
 * File:   TextWriter.h
 * Author: gidis
 *
 * Created on December 29, 2012, 12:23 PM
 */

#ifndef TEXTWRITER_H
#define	TEXTWRITER_H
#include <string>
class TextWriter {
public:
    TextWriter();
    TextWriter(const TextWriter& orig);
    void writeLine(std::string text);
    void append(std::string text);
    void writeToConsole();
    void writeToFile(std::string path);
    virtual ~TextWriter();
private:
    std::string buffer;

};

#endif	/* TEXTWRITER_H */


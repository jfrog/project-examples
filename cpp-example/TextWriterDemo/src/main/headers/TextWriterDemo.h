/* 
 * File:   TextWriterDemo.h
 * Author: gidis
 *
 * Created on December 29, 2012, 12:23 PM
 */

#ifndef TEXTWRITERDEMO_H
#define	TEXTWRITERDEMO_H
#include "TextWriter.h"
#include "TextScanner.h"

class TextWriterDemo {
public:
    TextWriterDemo();
    TextWriterDemo(const TextWriterDemo& orig);
    void run();
    virtual ~TextWriterDemo();
private:
    TextWriter demo ;
    TextScanner textScanner;
};

#endif	/* TEXTWRITERDEMO_H */


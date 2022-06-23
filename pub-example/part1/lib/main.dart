import 'dart:developer';

import 'package:flutter/material.dart';
//import 'package:JFrogCard/card.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      title: 'JFrog Card',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: const MyHomePage(title: 'Welcome to JFrog Security application'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Column(
        children: <Widget>[
          Image.network(
            'https://speedmedia.jfrog.com/08612fe1-9391-4cf3-ac1a-6dd49c36b276/https://support.jfrog.com/resource/1547111714000/BR_JFC_Resource/img/jfrog-logo.png/mxw_1920,f_auto',
            width: 80.0,
          ),
          Expanded(
            //        <-- Use Expanded
            child: ListView(
              children: const [
                JfrogCard(heading: "New Vulnerability was found", supportingText: "Security researchers from JFrog said on Thursday that they discovered a critical JNDI-based...'", mainCardText: "JFrog researchers find JNDI vulnerability in H2 database consoles similar to Log4Shell"),
                JfrogCard(heading: "New Vulnerability was found", supportingText: " Envoy proxy decompressor memory exhaustion DoS...'", mainCardText: "A memory exhaustion issue in Envoy Proxy's decompressors can allow a remote attacker to perform denial of service"),
                JfrogCard(heading: "New Vulnerability was found", supportingText: " Very large input data may cause Apache's mod_sed filter to abort, resulting in a denial of service...'", mainCardText: " Apache httpd mod_sed DoS"),
              ],
            ),
          )
        ],
      ),
    );
  }
}


class JfrogCard extends StatelessWidget {
  const JfrogCard({
    Key? key,
    required this.heading,
    this.subheading = "",
    this.mainCardText = "Default Text",
    required this.supportingText,
  }) : super(key: key);

  final String heading;
  final String subheading;
  final String supportingText;
  final String mainCardText;

  @override
  Widget build(BuildContext context) {
    return Card(
        elevation: 4.0,
        child: Column(
          children: [
            ListTile(
              title: Text(heading),
              subtitle: Text(subheading),
              trailing: Icon(Icons.favorite_outline),
            ),
            Container(
                height: 200.0,
                child: Center(
                  child: Padding(
                    padding: EdgeInsets.all(8.0),
                    child: Center(
                      child: Text(
                        mainCardText,
                        style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 20.0,
                            color: Colors.white),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  ),
                ),
                decoration: const BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  gradient: LinearGradient(
                      colors: [Color(0xFF40be46), Color(0xFF000000)],
                      begin: Alignment.bottomCenter,
                      end: Alignment.topCenter),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey,
                      blurRadius: 12,
                      offset: Offset(0, 6),
                    ),
                  ],
                )),
            Container(
              padding: EdgeInsets.all(16.0),
              alignment: Alignment.centerLeft,
              child: Text(supportingText),
            ),
            ButtonBar(
              children: [
                TextButton(
                  child: const Text('Remove'),
                  onPressed: () {
                    log('I Was pressed remove');
                  },
                ),
                TextButton(
                  child: const Text('LEARN MORE'),
                  onPressed: () {
                    log('I Was pressed to learn');
                  },
                )
              ],
            )
          ],
        ));
  }
}


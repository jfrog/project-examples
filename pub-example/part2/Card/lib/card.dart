library JFrogCard;
import 'dart:developer';

import 'package:flutter/material.dart';

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